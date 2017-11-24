package com.example.happyghost.showtimeforkotlin.inject.component.musiccomponent

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.musicmodule.MusicLocalListModule
import com.example.happyghost.showtimeforkotlin.ui.music.local.MusicLocalFragment
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/11/24.
 * @description
 */
@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(MusicLocalListModule::class))
interface MusicLocalListComponent {
    fun inject(fragment:MusicLocalFragment)
}