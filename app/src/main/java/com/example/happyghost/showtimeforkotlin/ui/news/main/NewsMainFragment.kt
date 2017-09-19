package com.example.happyghost.showtimeforkotlin.ui.news.main


import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.RxBus.event.ChannelEvent
import com.example.happyghost.showtimeforkotlin.adapter.ViewPagerAdapter
import com.example.happyghost.showtimeforkotlin.inject.component.newscomponent.DaggerNewsMainComponent
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment

import com.example.happyghost.showtimeforkotlin.inject.module.newsmodule.NewsMainModule
import com.example.happyghost.showtimeforkotlin.loacaldao.NewsTypeInfo
import com.example.happyghost.showtimeforkotlin.ui.news.channel.ChannelActivity
import com.example.happyghost.showtimeforkotlin.ui.news.newlist.NewsListFragment
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_news_main_layout.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/8/26.
 * @description
 */
class NewsMainFragment : BaseFragment<NewsMainPresenter>(),IBaseMainNewsView{


    @Inject lateinit var mPagerAdapter : ViewPagerAdapter

    override fun loadData(checkList: List<NewsTypeInfo>) {
        val fragments = ArrayList<Fragment>()
        val titles = ArrayList<String>()
        for (bean in checkList) {
            titles.add(bean.name)
            fragments.add(NewsListFragment.newInstance(bean.typeId))
        }
        mPagerAdapter.setItems(fragments, titles)
    }


    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView(mRootView: View?) {
        val toolbar = mRootView!!.find<Toolbar>(R.id.tool_bar)
        val new_vp = mRootView.find<ViewPager>(R.id.new_vp)
        val tab_new_layout = mRootView.find<TabLayout>(R.id.tab_new_layout)
        initToolBar(toolbar,"新闻",true)
        setHasOptionsMenu(true)
        new_vp.adapter=mPagerAdapter
        tab_new_layout.setupWithViewPager(new_vp)
        mPresenter.registerRxBus(ChannelEvent::class.java, Consumer<ChannelEvent> { t -> handleChannelMessage(t) })
    }
    override fun initInject() {
        DaggerNewsMainComponent.builder()
                .applicationComponent(getAppComponent())
                .newsMainModule(NewsMainModule(this))
                .build()
                .inject(this)
    }

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_news_main_layout
    }
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_channel,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId==R.id.item_channel){
            mContext?.startActivity<ChannelActivity>()
            return  true
        }
        return false
    }
    fun handleChannelMessage(t: ChannelEvent) {
        when(t.mEventType){
            ChannelEvent.ADD_EVENT->{
                mPagerAdapter.addItems(NewsListFragment.newInstance(t.mNewsData.typeId), t.mNewsData.name)
            }
            ChannelEvent.DEL_EVENT->{
                new_vp.setCurrentItem(0)
                mPagerAdapter.delItems(t.mNewsData.name)
            }
            ChannelEvent.SWAP_EVENT->{
                mPagerAdapter.swapItems(t.mFroPos,t.mToPos)
            }
        }
    }
}