package com.example.happyghost.showtimeforkotlin.inject.component.videocomponent

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.videomodule.VideoListModule
import com.example.happyghost.showtimeforkotlin.ui.video.kankan.VideoListFragment
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/12/25.
 * @description
 */
@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(VideoListModule::class))
interface VideoListComponent {
    fun inject(videoListFragment: VideoListFragment)
}