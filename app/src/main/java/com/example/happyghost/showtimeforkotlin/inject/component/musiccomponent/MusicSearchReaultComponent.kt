package com.example.happyghost.showtimeforkotlin.inject.component.musiccomponent

import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.musicmodule.MusicSearchReaultModule
import com.example.happyghost.showtimeforkotlin.ui.music.search.MusicSearchReaultActivity
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/12/5.
 * @description
 */
@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(MusicSearchReaultModule::class))
interface MusicSearchReaultComponent {
    fun inject(musicSearchReaultActivity: MusicSearchReaultActivity)
}