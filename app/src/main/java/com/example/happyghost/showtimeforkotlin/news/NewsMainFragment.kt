package com.example.happyghost.showtimeforkotlin.news


import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.loacaldao.NewsTypeInfo
import com.example.happyghost.showtimeforkotlin.news.main.IBaseMainNewsView
import com.example.happyghost.showtimeforkotlin.news.main.NewsMainPresenter
import kotlinx.android.synthetic.main.fragment_news_main_layout.*

/**
 * @author Zhao Chenping
 * @creat 2017/8/26.
 * @description
 */
class NewsMainFragment : BaseFragment<NewsMainPresenter>(),IBaseMainNewsView{
    override fun loadData(checkList: List<NewsTypeInfo>) {

    }


    override fun upDataView() {

    }

    override fun initView() {
        initToolBar(tool_bar,"新闻",true)
        setHasOptionsMenu(true)
    }

    override fun initInject() {

    }

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_news_main_layout
    }

}