package com.example.happyghost.showtimeforkotlin.inject.module.musicmodule

import android.content.Context
import com.example.happyghost.showtimeforkotlin.adapter.musicadapter.MusicLocalListAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.ui.music.local.MusicLocalFragment
import com.example.happyghost.showtimeforkotlin.ui.music.local.MusicLocalListPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/11/24.
 * @description
 */
@Module
class MusicLocalListModule(musicLocalFragment: MusicLocalFragment) {
    var view = musicLocalFragment
    @PerFragment
    @Provides
    fun providesPresenter(context: Context):MusicLocalListPresenter= MusicLocalListPresenter(view,context)
    @PerFragment
    @Provides
    fun providesAdapter():MusicLocalListAdapter= MusicLocalListAdapter()
}