package com.example.happyghost.showtimeforkotlin.inject.module.videomodule

import com.example.happyghost.showtimeforkotlin.adapter.videoadapter.LiveAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.ui.video.live.LiveFragment
import com.example.happyghost.showtimeforkotlin.ui.video.live.LivePresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/12/15.
 * @description
 */
@Module
class LiveFragmentModule(liveFragment: LiveFragment) {
    var view =liveFragment
    @PerFragment
    @Provides
    fun providesPresenter():LivePresenter= LivePresenter(view)
    @PerFragment
    @Provides
    fun providesAdapter():LiveAdapter=LiveAdapter();
}