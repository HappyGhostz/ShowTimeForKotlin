package com.example.happyghost.showtimeforkotlin.news


import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.news.main.IBaseMainNewsView
import com.example.happyghost.showtimeforkotlin.news.main.NewsMainPresenter

/**
 * @author Zhao Chenping
 * @creat 2017/8/26.
 * @description
 */
class NewsMainFragment : BaseFragment<NewsMainPresenter>(),IBaseMainNewsView{
    override fun loadData() {

    }

    override fun upDataView() {

    }

    override fun initView() {

    }

    override fun initInject() {

    }

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_news_main_layout
    }

}