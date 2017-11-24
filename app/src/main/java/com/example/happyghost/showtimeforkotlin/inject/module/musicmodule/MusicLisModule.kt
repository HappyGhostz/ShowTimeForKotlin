package com.example.happyghost.showtimeforkotlin.inject.module.musicmodule

import com.example.happyghost.showtimeforkotlin.adapter.musicadapter.MusicListAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.ui.music.list.MusicListFragment
import com.example.happyghost.showtimeforkotlin.ui.music.list.MusicListPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/11/24.
 * @description
 */
@Module
class MusicLisModule(musicListFragment: MusicListFragment) {
    var view =musicListFragment
    @PerFragment
    @Provides
    fun providesPresenter():MusicListPresenter= MusicListPresenter(view)
    @PerFragment
    @Provides
    fun providesAdapter():MusicListAdapter=MusicListAdapter()
}