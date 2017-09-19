package com.example.happyghost.showtimeforkotlin.inject.component.newscomponent

import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.newsmodule.NewSpecialModule
import com.example.happyghost.showtimeforkotlin.ui.news.special.NewsSpecialActivity
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/9/11.
 * @description
 */
@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(NewSpecialModule::class))
interface NewsSpecialComponent {
    fun inject(newsSpecial:NewsSpecialActivity)
}