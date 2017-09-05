package com.example.happyghost.showtimeforkotlin.news.newlist

import android.os.Bundle
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.bean.NewsInfo
import com.example.happyghost.showtimeforkotlin.bean.NewsMultiItem
import com.example.happyghost.showtimeforkotlin.inject.component.DaggerNewsListComponent
import com.example.happyghost.showtimeforkotlin.inject.module.NewsListModule
import com.example.happyghost.showtimeforkotlin.news.newslist.INewsListView
import com.example.happyghost.showtimeforkotlin.news.newslist.NewsListPresenter

/**
 * @author Zhao Chenping
 * @creat 2017/8/30.
 * @description
 */
class NewsListFragment : BaseFragment<NewsListPresenter>(), INewsListView<List<NewsMultiItem>> {
    override fun loadData(data: List<NewsMultiItem>) {

    }

    override fun loadMoreData(moreData: List<NewsMultiItem>) {

    }

    override fun loadNoData() {

    }

    override fun loadAdData(newsBean: NewsInfo) {

    }

    override fun upDataView() {
         mPresenter.getData()
    }

    override fun initView() {

    }

    override fun initInject() {
        val keyID = arguments.getString(NEWS_TYPE_KEY)
         DaggerNewsListComponent.builder()
                 .applicationComponent(getAppComponent())
                 .newsListModule(NewsListModule(this,keyID))
                 .build()
                 .inject(this)
    }

    override fun getFragmentLayout(): Int {
         return R.layout.fragment_list_layout
    }


    companion object {
         val NEWS_TYPE_KEY: String = "newstypekey"
         fun newInstance(newsId: String): NewsListFragment {
             val fragment = NewsListFragment()
             val bundle = Bundle()
             bundle.putString(NEWS_TYPE_KEY, newsId)
             fragment.arguments = bundle
             return fragment
         }
     }

}