package com.example.happyghost.showtimeforkotlin.ui.music.search

import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.SearchHistoryAdapter
import com.example.happyghost.showtimeforkotlin.adapter.musicadapter.MusicSearchResultAdapter
import com.example.happyghost.showtimeforkotlin.bean.musicdate.MusicSearchList
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongDetailInfo
import com.example.happyghost.showtimeforkotlin.inject.component.musiccomponent.DaggerMusicSearchReaultComponent
import com.example.happyghost.showtimeforkotlin.inject.module.musicmodule.MusicSearchReaultModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.music.play.MusicPlayActivity
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import com.example.happyghost.showtimeforkotlin.utils.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_music_search.*
import java.util.ArrayList
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/12/5.
 * @description
 */
class MusicSearchReaultActivity: BaseActivity<MusicSearchPresenter>(),IMusicSearchView {
    lateinit var searchView:SearchView
    lateinit var menuItem:MenuItem
    @Inject lateinit var  mHisAdapter: SearchHistoryAdapter
    @Inject lateinit var  mSearchResultAdapter: MusicSearchResultAdapter
    private val mHistory = ArrayList<String>()
    override fun loadSearchMusicResult(results: List<MusicSearchList.SongListBean>) {
        initSearchResult()
        mSearchResultAdapter.replaceData(results)
    }
    override fun loadSearchMusicInfo(results: SongDetailInfo) {
        MusicPlayActivity.open(this,results,false)
    }

    override fun upDataView() {

    }

    override fun initView() {
        initActionBar(tool_bar,"",true)
        RecyclerViewHelper.initRecycleViewV(this,lvSearchHistory,mHisAdapter,true)
        initSearchHistory()
        tvClear.setOnClickListener{
            SharedPreferencesUtil.putObject("searchHistory",null)
            initSearchHistory()
        }
        mHisAdapter.setOnItemClickListener { adapter, _, position ->
            val history = adapter.getItem(position) as String
            search(history)
        }
    }

    override fun initInjector() {
        DaggerMusicSearchReaultComponent.builder()
                .applicationComponent(getAppComponent())
                .musicSearchReaultModule(MusicSearchReaultModule(this))
                .build()
                .inject(this)
    }

    override fun getContentView(): Int= R.layout.activity_music_search
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_book_search,menu)
        menuItem = menu?.findItem(R.id.action_search)!!//在菜单中找到对应控件的item
        searchView = MenuItemCompat.getActionView(menuItem) as SearchView
        searchView.isIconified = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                mPresenter.getSearchResultList(query!!)
                saveSearchHistory(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (TextUtils.isEmpty(newText)) {
                    initHistView()
                }
                return false
            }
        })
        MenuItemCompat.setOnActionExpandListener(menuItem,
                object : MenuItemCompat.OnActionExpandListener {
                    //设置打开关闭动作监听
                    override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                        searchView.isIconified = false
                        initHistView()
                        return true
                    }

                    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                        searchView.isIconified = true
                        initHistView()
                        return true
                    }
                })
        return true
    }

    private fun saveSearchHistory(query: String?) {
        mHistory.clear()
        var list = SharedPreferencesUtil.getObject("searchHistory", ArrayList<String>()::class.java)
        if (list == null) {
            mHistory.add(query!!)
        } else {
            mHistory.addAll(list)
            val iterator = mHistory.iterator()
            while (iterator.hasNext()) {
                val item = iterator.next()
                if (TextUtils.equals(query, item)) {
                    iterator.remove()
                }
            }
            mHistory.add(0, query!!)
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

    private fun initHistView() {
        visible(rlHistory)
        gone(recyclerview)
    }
    fun search(key: String?) {
        MenuItemCompat.expandActionView(menuItem)
        if (!TextUtils.isEmpty(key)) {
            initSearchResult()
            searchView.setQuery(key, true)
            saveSearchHistory(key!!)
        }
    }
    private fun initSearchResult() {
        gone(rlHistory)
        visible(recyclerview)
        RecyclerViewHelper.initRecycleViewV(this,recyclerview,mSearchResultAdapter,true)
        mSearchResultAdapter.setOnItemClickListener { adapter, view, position ->
           var song = adapter.getItem(position) as  MusicSearchList.SongListBean
            val song_id = song.song_id
            mPresenter.getMusic(song_id)
        }
    }
}