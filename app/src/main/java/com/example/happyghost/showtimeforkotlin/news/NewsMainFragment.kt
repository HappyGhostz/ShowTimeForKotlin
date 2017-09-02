package com.example.happyghost.showtimeforkotlin.news


import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.ViewPagerAdapter
import com.example.happyghost.showtimeforkotlin.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.inject.component.DaggerNewsMainComponent
import com.example.happyghost.showtimeforkotlin.inject.module.NewsMainModule
import com.example.happyghost.showtimeforkotlin.loacaldao.NewsTypeInfo
import com.example.happyghost.showtimeforkotlin.news.main.IBaseMainNewsView
import com.example.happyghost.showtimeforkotlin.news.main.NewsMainPresenter
import com.example.happyghost.showtimeforkotlin.news.newlist.NewsListFragment
import kotlinx.android.synthetic.main.fragment_news_main_layout.*
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

    override fun initView() {
        initToolBar(tool_bar,"新闻",true)
        setHasOptionsMenu(true)
        new_vp.adapter=mPagerAdapter
        tab_new_layout.setupWithViewPager(new_vp)
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
           Toast.makeText(AppApplication.instance?.getContext(),mPresenter.showString(),Toast.LENGTH_SHORT).show()

            return  true
        }
        return false
    }
}