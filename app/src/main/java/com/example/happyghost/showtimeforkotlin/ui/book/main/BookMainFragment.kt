package com.example.happyghost.showtimeforkotlin.ui.book.main

import android.app.AlertDialog
import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.ViewPagerAdapter
import com.example.happyghost.showtimeforkotlin.downloadservice.DownloadBookService
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.ui.book.classify.BookClassifyListFragment
import com.example.happyghost.showtimeforkotlin.ui.book.community.BookCommunityListFragment
import com.example.happyghost.showtimeforkotlin.ui.book.rack.BookRackListFragment
import com.example.happyghost.showtimeforkotlin.ui.book.rank.BookRankListFragment
import com.example.happyghost.showtimeforkotlin.ui.book.search.BookSearchActivity
import com.example.happyghost.showtimeforkotlin.ui.book.search.ScanLocalBookActivity
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.SharedPreferencesUtil
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startService
import org.jetbrains.anko.support.v4.toast
import java.util.ArrayList

/**
 * @author Zhao Chenping
 * @creat 2017/8/28.
 * @description
 */
class BookMainFragment: BaseFragment<BookMainPresenter>() {
    lateinit var adapter:ViewPagerAdapter

    override fun upDataView() {
        initData()
    }

    private fun initData() {
        startService<DownloadBookService>()
        var titles = ArrayList<String>()
        titles.add("书架")
        titles.add("分类")
        titles.add("社区")
        titles.add("排行榜")
        var fragments = ArrayList<Fragment>()
        fragments.add(BookRackListFragment.lunch("书架"))
        fragments.add(BookClassifyListFragment.lunch("分类"))
        fragments.add(BookCommunityListFragment.lunch("社区"))
        fragments.add(BookRankListFragment.lunch("排行榜"))
        adapter.setItems(fragments,titles)
    }

    override fun initView(mRootView: View?) {
        val toolbar = mRootView!!.find<Toolbar>(R.id.tool_bar)
        val tabLayout = mRootView!!.find<TabLayout>(R.id.tab_book_layout)
        val viewPager = mRootView.find<ViewPager>(R.id.viewpager)
        initToolBar(toolbar,"阅读",true)
        setHasOptionsMenu(true)
        adapter = ViewPagerAdapter(childFragmentManager)
        viewPager.adapter=adapter
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun initInject() {

    }

    override fun getFragmentLayout(): Int = R.layout.fragment_book_main

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_book, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
         val id = item?.itemId
         when(id){
             R.id.action_seach->{
                 BookSearchActivity.open(mContext)
             }
             R.id.item_channel->{
                 AlertDialog.Builder(mContext)
                         .setTitle("阅读页翻页效果")
                         .setSingleChoiceItems(resources.getStringArray(R.array.setting_dialog_style_choice),
                                 SharedPreferencesUtil.getInt(ConsTantUtils.FLIP_STYLE, 0),
                                  {
                                      dialog, which->
                                         SharedPreferencesUtil.putInt(ConsTantUtils.FLIP_STYLE, which);
                                         dialog.dismiss()
                                     }
                                 )
                         .create()
                         .show()
             }
             R.id.action_sync_bookshelf-> ScanLocalBookActivity.open(mContext!!)
         }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        DownloadBookService.cancel()
        mContext?.stopService(Intent(mContext,DownloadBookService.javaClass))
    }

}