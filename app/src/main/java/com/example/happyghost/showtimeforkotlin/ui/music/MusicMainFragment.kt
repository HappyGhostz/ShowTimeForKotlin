package com.example.happyghost.showtimeforkotlin.ui.music

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.ViewPagerAdapter
import com.example.happyghost.showtimeforkotlin.downloadservice.DownloadBookService
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.ui.book.classify.BookClassifyListFragment
import com.example.happyghost.showtimeforkotlin.ui.book.community.BookCommunityListFragment
import com.example.happyghost.showtimeforkotlin.ui.book.rack.BookRackListFragment
import com.example.happyghost.showtimeforkotlin.ui.book.rank.BookRankListFragment
import com.example.happyghost.showtimeforkotlin.ui.music.list.MusicListFragment
import com.example.happyghost.showtimeforkotlin.ui.music.local.MusicLocalFragment
import com.example.happyghost.showtimeforkotlin.ui.music.rank.MusicRankFragment
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startService
import java.util.ArrayList

/**
 * @author Zhao Chenping
 * @creat 2017/8/28.
 * @description
 */
class MusicMainFragment : BaseFragment<IBasePresenter>(){
    lateinit var adapter:ViewPagerAdapter
    override fun upDataView() {
        var titles = ArrayList<String>()
        titles.add("本地音乐")
        titles.add("推荐歌单")
        titles.add("排行榜")
        var fragments = ArrayList<Fragment>()
        fragments.add(MusicLocalFragment.lunch())
        fragments.add(MusicListFragment.lunch())
        fragments.add(MusicRankFragment.lunch())
        adapter.setItems(fragments,titles)
    }

    override fun initView(mRootView: View?) {
        val toolbar = mRootView!!.find<Toolbar>(R.id.tool_bar)
        val tabLayout = mRootView!!.find<TabLayout>(R.id.tab_book_layout)
        val viewPager = mRootView.find<ViewPager>(R.id.viewpager)
        initToolBar(toolbar,"音乐爽听",true)
        setHasOptionsMenu(true)
        adapter = ViewPagerAdapter(childFragmentManager)
        viewPager.adapter=adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun initInject() {

    }

    override fun getFragmentLayout(): Int =R.layout.fragment_book_main

}