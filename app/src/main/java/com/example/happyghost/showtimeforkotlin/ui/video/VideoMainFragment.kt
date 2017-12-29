package com.example.happyghost.showtimeforkotlin.ui.video

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.ViewPagerAdapter
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.ui.video.kankan.VideoListFragment
import org.jetbrains.anko.find

/**
 * @author Zhao Chenping
 * @creat 2017/8/28.
 * @description
 */
class VideoMainFragment : BaseFragment<IBasePresenter>() {
    lateinit var pagerAdapter:ViewPagerAdapter
    override fun upDataView() {
        var titles = ArrayList<String>()
        titles.add("头条")
        titles.add("娱乐")//categoryId": "4",
        titles.add("社会")//categoryId": "1",
        titles.add("搞笑")//"categoryId": "7",
        titles.add("世界")// "categoryId": "2",
        titles.add("科技")// "categoryId": "8",
        titles.add("体育")// "categoryId": "9",
        titles.add("生活")// "categoryId": "5",
        titles.add("财富")// "categoryId": "3",
        titles.add("新知")// "categoryId": "10",
        titles.add("美食")//  "categoryId": "6",
        var fragments = ArrayList<Fragment>()
        fragments.add(VideoListFragment.instance("0"))
        fragments.add(VideoListFragment.instance("4"))
        fragments.add(VideoListFragment.instance("1"))
        fragments.add(VideoListFragment.instance("7"))
        fragments.add(VideoListFragment.instance("2"))
        fragments.add(VideoListFragment.instance("8"))
        fragments.add(VideoListFragment.instance("9"))
        fragments.add(VideoListFragment.instance("5"))
        fragments.add(VideoListFragment.instance("3"))
        fragments.add(VideoListFragment.instance("10"))
        fragments.add(VideoListFragment.instance("6"))
        pagerAdapter.setItems(fragments,titles)
    }

    override fun initView(mRootView: View?) {
        val toolbar = mRootView!!.find<Toolbar>(R.id.tool_bar)
        val viewPage = mRootView.find<ViewPager>(R.id.new_vp)
        val tabLayout = mRootView.find<TabLayout>(R.id.tab_new_layout)
        initToolBar(toolbar,"视频",true)
        pagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPage.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPage)

    }

    override fun initInject() {

    }

    override fun getFragmentLayout(): Int =R.layout.fragment_news_main_layout

}