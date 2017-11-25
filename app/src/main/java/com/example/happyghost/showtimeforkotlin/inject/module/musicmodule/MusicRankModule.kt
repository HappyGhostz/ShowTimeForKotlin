package com.example.happyghost.showtimeforkotlin.inject.module.musicmodule

import com.example.happyghost.showtimeforkotlin.adapter.musicadapter.MusicRankAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.ui.music.rank.MusicRankFragment
import com.example.happyghost.showtimeforkotlin.ui.music.rank.MusicRankPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/11/25.
 * @description
 */
@Module
class MusicRankModule(musicRankFragment: MusicRankFragment) {
    var view = musicRankFragment
    @PerFragment
    @Provides
    fun providesPresenter():MusicRankPresenter= MusicRankPresenter(view)
    @PerFragment
    @Provides
    fun providesMusicRankAdapter(): MusicRankAdapter=MusicRankAdapter()
}