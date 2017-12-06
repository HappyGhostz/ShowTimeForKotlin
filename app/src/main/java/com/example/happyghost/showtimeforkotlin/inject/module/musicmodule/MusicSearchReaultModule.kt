package com.example.happyghost.showtimeforkotlin.inject.module.musicmodule

import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.SearchHistoryAdapter
import com.example.happyghost.showtimeforkotlin.adapter.musicadapter.MusicSearchResultAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.ui.music.search.MusicSearchPresenter
import com.example.happyghost.showtimeforkotlin.ui.music.search.MusicSearchReaultActivity
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/12/5.
 * @description
 */
@Module
class MusicSearchReaultModule(musicSearchReaultActivity: MusicSearchReaultActivity) {
    var view = musicSearchReaultActivity
    @PerActivity
    @Provides
    fun providesPresenter():MusicSearchPresenter = MusicSearchPresenter(view)
    @PerActivity
    @Provides
    fun providesHistAdapter(): SearchHistoryAdapter = SearchHistoryAdapter()
    @PerActivity
    @Provides
    fun providesResultAdapter(): MusicSearchResultAdapter =MusicSearchResultAdapter()
}