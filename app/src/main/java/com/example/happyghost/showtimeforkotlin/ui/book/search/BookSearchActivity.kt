package com.example.happyghost.showtimeforkotlin.ui.book.search

import android.app.Activity
import android.content.Context
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdata.SearchDetail
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookSearchModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_book_search.*
import org.jetbrains.anko.startActivity
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.ListPopupWindow
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.*
import android.widget.AdapterView
import android.widget.PopupWindow
import android.widget.TextView
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.AutoCompleteAdapter
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.SearchHistoryAdapter
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.SearchResultAdapter
import com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent.DaggerBookSearchComponent
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import com.example.happyghost.showtimeforkotlin.utils.SharedPreferencesUtil
import com.example.happyghost.showtimeforkotlin.wegit.TagColor
import com.example.happyghost.showtimeforkotlin.wegit.TagGroup
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.util.ArrayList
import javax.inject.Inject


/**
 * @author Zhao Chenping
 * @creat 2017/11/21.
 * @description
 */
class BookSearchActivity: BaseActivity<BookSearchPresenter>(),IBookSearchBaseView {
    lateinit var searchView:SearchView
    lateinit var menuItem:MenuItem
    var mListPopupWindow: ListPopupWindow? = null
    private val mAutoList = ArrayList<String>()
    private val mHistory = ArrayList<String>()
    private val tagList = ArrayList<String>()
    private var times = 0
    lateinit var mAutoAdapter:AutoCompleteAdapter
    @Inject lateinit var  mHisAdapter: SearchHistoryAdapter
    @Inject lateinit var  mSearchResultAdapter: SearchResultAdapter
    internal var hotIndex = 0
    private var key: String? = null
    override fun showHotWordList(list: List<String>) {
        visible(tvChangeWords)
        tagList.clear()
        tagList.addAll(list)
        times = 0
        showHotWord()
    }

    /**
     * 每次显示8个热搜词
     */
    @Synchronized private fun showHotWord() {
        val tagSize = 8
        val tags =ArrayList<String>()
        var j = 0
        while (j < tagSize && j < tagList.size) {
            tags.add(tagList[hotIndex % tagList.size])
            hotIndex++
            j++
        }
        val colors = TagColor.getRandomColors(tagSize)
        tag_group.setTags(colors, *tags.toTypedArray())
    }

    override fun showAutoCompleteList(list: List<String>) {
        mAutoList.clear()
        mAutoList.addAll(list)
        if (!mListPopupWindow?.isShowing!!) {
            mListPopupWindow?.inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED
            mListPopupWindow?.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
            mListPopupWindow?.show()
        }
        mAutoAdapter.notifyDataSetChanged()
    }

    override fun showSearchResultList(list: List<SearchDetail.SearchBooks>) {
        initSearchResult()
        mSearchResultAdapter.replaceData(list)

    }

    private fun initSearchResult() {
        gone(tag_group, layoutHotWord, rlHistory)
        visible(recyclerview)
        RecyclerViewHelper.initRecycleViewV(this,recyclerview,mSearchResultAdapter,true)
        if (mListPopupWindow!!.isShowing)
            mListPopupWindow?.dismiss()
    }

    override fun upDataView() {
        mPresenter.getData()
        mPresenter.getHotWordList()
    }

    override fun initView() {
        initActionBar(tool_bar,"",true)
        initAutoList()
        initTagGroup()
        RecyclerViewHelper.initRecycleViewV(this,lvSearchHistory,mHisAdapter,true)
        initSearchHistory()
        tag_group.setOnTagClickListener(object : TagGroup.OnTagClickListener{
            override fun onTagClick(tag: String) {
                search(tag)
            }
        })

        tvChangeWords.setOnClickListener{ showHotWord() }
        tvClear.setOnClickListener{
            SharedPreferencesUtil.putObject("searchHistory",null)
            initSearchHistory()
        }
        mHisAdapter.setOnItemClickListener { _, _, position ->
            search(mHistory[position])
        }

    }

    private fun initAutoList() {
        mListPopupWindow = ListPopupWindow(this)
        mAutoAdapter = AutoCompleteAdapter(this, mAutoList)
        mListPopupWindow?.setAdapter(mAutoAdapter)
        mListPopupWindow?.width = ViewGroup.LayoutParams.MATCH_PARENT
        mListPopupWindow?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        mListPopupWindow?.anchorView = tool_bar
        mListPopupWindow?.setOnItemClickListener{ parent, view, position, id ->
            mListPopupWindow?.dismiss()
            val tv = view.find<TextView>(R.id.tvAutoCompleteItem)
            val str = tv.text.toString()
            search(str)
        }
    }

    override fun initInjector() {
        key = intent.getStringExtra(QUERY_KEY)
        DaggerBookSearchComponent.builder()
                .applicationComponent(getAppComponent())
                .bookSearchModule(BookSearchModule(this))
                .build()
                .inject(this)

    }

    override fun getContentView(): Int = R.layout.activity_book_search
    companion object {
        var QUERY_KEY = "querykey"
        fun open(mContext: Context?) {
            mContext?.startActivity<BookSearchActivity>()
            (mContext as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
        fun open(mContext: Context?,key: String){
            mContext?.startActivity<BookSearchActivity>(QUERY_KEY to key)
            (mContext as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_book_search,menu)
        menuItem = menu?.findItem(R.id.action_search)!!//在菜单中找到对应控件的item
        searchView = MenuItemCompat.getActionView(menuItem) as SearchView
        searchView.isIconified = false
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                key = query
                mPresenter.getSearchResultList(query!!)
                saveSearchHistory(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (TextUtils.isEmpty(newText)) {
                    if (mListPopupWindow?.isShowing!!)
                        mListPopupWindow?.dismiss()
                    initTagGroup()
                } else {
                    mPresenter.getAutoCompleteList(newText!!)
                }
                return false
            }
        })
        search(key) // 外部调用搜索，则打开页面立即进行搜索
        MenuItemCompat.setOnActionExpandListener(menuItem,
                object : MenuItemCompat.OnActionExpandListener {
                    //设置打开关闭动作监听
                    override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                        searchView.isIconified = false
                        initTagGroup()
                        return true
                    }

                    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                        searchView.isIconified = true
                        initTagGroup()
                        return true
                    }
                })
        return true
    }

    private fun saveSearchHistory(query: String) {
        mHistory.clear()
        var list = SharedPreferencesUtil.getObject("searchHistory", ArrayList<String>()::class.java)
        if (list == null) {
            mHistory.add(query)
        } else {
            mHistory.addAll(list)
            val iterator = mHistory.iterator()
            while (iterator.hasNext()) {
                val item = iterator.next()
                if (TextUtils.equals(query, item)) {
                    iterator.remove()
                }
            }
            mHistory.add(0, query)
        }
        val size = mHistory.size
        if (size > 20) { // 最多保存20条
            for (i in size - 1 downTo 20) {
                mHistory.removeAt(i)
            }
        }
        SharedPreferencesUtil.putObject("searchHistory",mHistory)
        initSearchHistory()
    }

    private fun initSearchHistory() {
        val list = SharedPreferencesUtil.getObject("searchHistory",ArrayList<String>()::class.java)
        if (list != null) {
            if (list.size > 0) {

                mHisAdapter.replaceData(list)
            } else {
                tvClear.isEnabled = false
            }
        }else{
            mHistory.clear()
            mHisAdapter.replaceData(mHistory)
        }
    }

    private fun initTagGroup() {
        visible(tag_group, layoutHotWord, rlHistory)
        gone(recyclerview)
        if (mListPopupWindow!=null&&mListPopupWindow!!.isShowing)
            mListPopupWindow?.dismiss()
    }
    fun search(key: String?) {
        MenuItemCompat.expandActionView(menuItem)
        if (!TextUtils.isEmpty(key)) {
            initSearchResult()
            searchView.setQuery(key, true)
            saveSearchHistory(key!!)
        }
    }
}