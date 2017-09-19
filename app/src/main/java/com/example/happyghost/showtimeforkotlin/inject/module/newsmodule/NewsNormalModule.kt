package com.example.happyghost.showtimeforkotlin.inject.module.newsmodule

import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.ui.news.normal.NewsNormalActivity
import com.example.happyghost.showtimeforkotlin.ui.news.normal.NewsNormalPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/9/12.
 * @description
 */
@Module
class NewsNormalModule(view: NewsNormalActivity, normalId: String) {
    var baseView:NewsNormalActivity
    var id :String
    init {
        baseView = view
        id = normalId
    }
    @PerActivity
    @Provides
    fun providesPresenter():NewsNormalPresenter{
        return NewsNormalPresenter(baseView,id)
    }
}