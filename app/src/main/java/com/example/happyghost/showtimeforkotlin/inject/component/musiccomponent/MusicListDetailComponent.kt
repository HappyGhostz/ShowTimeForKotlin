package com.example.happyghost.showtimeforkotlin.inject.component.musiccomponent

import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.musicmodule.MusicListDetailModule
import com.example.happyghost.showtimeforkotlin.ui.music.listdetail.MusicListDetialActivity
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/11/27.
 * @description
 */
@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(MusicListDetailModule::class))
interface MusicListDetailComponent {
    fun inject(musicListDetialActivity: MusicListDetialActivity)
}