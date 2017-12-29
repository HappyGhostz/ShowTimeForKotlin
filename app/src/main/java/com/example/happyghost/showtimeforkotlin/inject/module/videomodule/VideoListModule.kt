package com.example.happyghost.showtimeforkotlin.inject.module.videomodule

import com.example.happyghost.showtimeforkotlin.adapter.videoadapter.VideoListAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.ui.video.kankan.VideoListFragment
import com.example.happyghost.showtimeforkotlin.ui.video.kankan.VideoListPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/12/25.
 * @description
 */
@Module
class VideoListModule(videoListFragment: VideoListFragment) {
    var view= videoListFragment
    @PerFragment
    @Provides
    fun providesPresenter():VideoListPresenter= VideoListPresenter(view)
    @PerFragment
    @Provides
    fun providesAdapter():VideoListAdapter = VideoListAdapter()
    @PerFragment
    @Provides
    fun providesCategroyAdapter():VideoListCategroyIdAdapter = VideoListCategroyIdAdapter()
}