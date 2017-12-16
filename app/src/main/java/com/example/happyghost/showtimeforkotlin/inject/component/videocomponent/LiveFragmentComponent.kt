package com.example.happyghost.showtimeforkotlin.inject.component.videocomponent

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.videomodule.LiveFragmentModule
import com.example.happyghost.showtimeforkotlin.ui.video.live.LiveFragment
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/12/16.
 * @description
 */
@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(LiveFragmentModule::class))
interface LiveFragmentComponent {
    fun inject(liveFragment: LiveFragment)
}