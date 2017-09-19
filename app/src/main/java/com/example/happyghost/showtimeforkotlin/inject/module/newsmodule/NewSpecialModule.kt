package com.example.happyghost.showtimeforkotlin.inject.module.newsmodule

import com.example.happyghost.showtimeforkotlin.adapter.newsadapter.NewsSpecialAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.ui.news.special.NewsSpecialActivity
import com.example.happyghost.showtimeforkotlin.ui.news.special.NewsSpecialPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/9/11.
 * @description
 */
@Module
class NewSpecialModule(newsSpecialActivity: NewsSpecialActivity, specialId: String) {
    var view:NewsSpecialActivity = newsSpecialActivity
    var id:String=specialId
    @PerActivity
    @Provides
    fun providesPresenter():NewsSpecialPresenter{
        return NewsSpecialPresenter(view,id)
    }
    @PerActivity
    @Provides
    fun providesAdapter(): NewsSpecialAdapter {
        return NewsSpecialAdapter()
    }

}