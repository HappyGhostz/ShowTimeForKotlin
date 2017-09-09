package com.example.happyghost.showtimeforkotlin.inject.module

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.ui.news.newlist.NewsListFragment
import com.example.happyghost.showtimeforkotlin.ui.news.newslist.NewsListPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/9/5.
 * @description
 */
@Module
class NewsListModule() {
    lateinit var view: NewsListFragment
    lateinit var key :String
    constructor(newsListFragment: NewsListFragment, keyID: String):this(){
        this.view = newsListFragment
        this.key=keyID
    }
    @PerFragment
    @Provides
    fun provudesNewsListPresenter():NewsListPresenter{
        return NewsListPresenter(view,key)
    }
//    @PerFragment
//    @Provides
//    fun providesRecycleViewAdapter():NewsListAdapter{
//        return NewsListAdapter()
//    }
}