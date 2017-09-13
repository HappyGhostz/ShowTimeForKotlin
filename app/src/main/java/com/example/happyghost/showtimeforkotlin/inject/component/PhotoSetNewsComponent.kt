package com.example.happyghost.showtimeforkotlin.inject.component

import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.inject.module.PhotoSetNewsModule
import com.example.happyghost.showtimeforkotlin.ui.news.photonews.PhotoSetNewsActivity
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/9/13.
 * @description
 */
@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(PhotoSetNewsModule::class))
interface PhotoSetNewsComponent {
    fun inject(photoSetNewsActivity: PhotoSetNewsActivity)
}