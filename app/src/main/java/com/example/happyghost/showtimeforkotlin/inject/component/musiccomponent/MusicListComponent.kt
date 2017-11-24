package com.example.happyghost.showtimeforkotlin.inject.component.musiccomponent

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.musicmodule.MusicLisModule
import com.example.happyghost.showtimeforkotlin.ui.music.list.MusicListFragment
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/11/24.
 * @description
 */
@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(MusicLisModule::class))
interface MusicListComponent {
    fun inject(musicListFragment: MusicListFragment)
}