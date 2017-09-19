package com.example.happyghost.showtimeforkotlin.inject.component.newscomponent

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.newsmodule.NewsMainModule
import com.example.happyghost.showtimeforkotlin.ui.news.main.NewsMainFragment
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/9/2.
 * @description
 */
@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(NewsMainModule::class))
interface NewsMainComponent {
    fun inject(newsMainFragment: NewsMainFragment)
}