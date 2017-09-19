package com.example.happyghost.showtimeforkotlin.inject.component.newscomponent

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.newsmodule.NewsListModule
import com.example.happyghost.showtimeforkotlin.ui.news.newlist.NewsListFragment
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/9/5.
 * @description
 */
@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(NewsListModule::class))
interface NewsListComponent {
    fun inject(newsListFragment: NewsListFragment)
}