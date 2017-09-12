package com.example.happyghost.showtimeforkotlin.inject.component

import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.inject.module.NewsNormalModule
import com.example.happyghost.showtimeforkotlin.ui.news.normal.NewsNormalActivity
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/9/12.
 * @description
 */
@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(NewsNormalModule::class))
interface NewsNormalComponent {
    fun inject(newsNormalActivity: NewsNormalActivity)
}