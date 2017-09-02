package com.example.happyghost.showtimeforkotlin.news


import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.ViewPagerAdapter
import com.example.happyghost.showtimeforkotlin.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.inject.module.NewsMainModule
import com.example.happyghost.showtimeforkotlin.loacaldao.NewsTypeInfo
import com.example.happyghost.showtimeforkotlin.news.main.IBaseMainNewsView
import com.example.happyghost.showtimeforkotlin.news.main.NewsMainPresenter
import kotlinx.android.synthetic.main.fragment_news_main_layout.*
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/8/26.
 * @description
 */
class NewsMainFragment : BaseFragment<NewsMainPresenter>(),IBaseMainNewsView{
    @Inject lateinit var mPagerAdapter : ViewPagerAdapter
//    @Inject lateinit var presenter :NewsMainPresenter
    override fun loadData(checkList: List<NewsTypeInfo>) {

    }


    override fun upDataView() {
//        mPresenter.getData()
    }

    override fun initView() {
        initToolBar(tool_bar,"新闻",true)
        setHasOptionsMenu(true)
//        new_vp.adapter=mPagerAdapter
//        tab_new_layout.setupWithViewPager(new_vp)
    }

    override fun initInject() {
//        DaggerNewsMainComponent.builder()
//                .applicationComponent(getAppComponent())
//                .newsMainModule(NewsMainModule(this))
//                .build()
//                .inject(this)
    }

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_news_main_layout
    }
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_channel,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId==R.id.item_channel){
            //todo
            return  true
        }
        return false
    }
}