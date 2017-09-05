package com.example.happyghost.showtimeforkotlin.inject.module

import com.example.happyghost.showtimeforkotlin.adapter.NewsListAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.news.newlist.NewsListFragment
import com.example.happyghost.showtimeforkotlin.news.newslist.NewsListPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/9/5.
 * @description
 */
@Module
class NewsListModule(newsListFragment: NewsListFragment, keyID: String) {
    var view = newsListFragment
    var key = keyID
    @PerFragment
    @Provides
    fun provudesNewsListPresenter():NewsListPresenter{
        return NewsListPresenter(view,key)
    }
    @PerFragment
    @Provides
    fun providesRecycleViewAdapter():NewsListAdapter{
        return NewsListAdapter()
    }
}