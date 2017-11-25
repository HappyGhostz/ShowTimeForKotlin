package com.example.happyghost.showtimeforkotlin.inject.component.musiccomponent

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.musicmodule.MusicRankModule
import com.example.happyghost.showtimeforkotlin.ui.music.rank.MusicRankFragment
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/11/25.
 * @description
 */
@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(MusicRankModule::class))
interface MusicRankComponent {
    fun inject(musicRankFragment: MusicRankFragment)
}