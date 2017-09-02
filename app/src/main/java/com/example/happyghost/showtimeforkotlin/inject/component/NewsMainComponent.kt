package com.example.happyghost.showtimeforkotlin.inject.component

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.module.NewsMainModule
import com.example.happyghost.showtimeforkotlin.news.NewsMainFragment
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/9/2.
 * @description
 */
//@PerFragment
//@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(NewsMainModule::class))
interface NewsMainComponent {
    fun inject(newsMainFragment: NewsMainFragment)
}