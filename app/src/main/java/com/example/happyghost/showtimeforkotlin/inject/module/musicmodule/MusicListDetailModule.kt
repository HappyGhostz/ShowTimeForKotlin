package com.example.happyghost.showtimeforkotlin.inject.module.musicmodule

import com.example.happyghost.showtimeforkotlin.adapter.musicadapter.MusicListDetialAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.ui.music.listdetail.MusicListDetialActivity
import com.example.happyghost.showtimeforkotlin.ui.music.listdetail.MusicListDetialPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/11/25.
 * @description
 */
@Module
class MusicListDetailModule(musicListDetialActivity: MusicListDetialActivity, listid: String?) {
    var view=musicListDetialActivity
    var stringid=listid

    @PerActivity
    @Provides
    fun providesPresenter():MusicListDetialPresenter=MusicListDetialPresenter(view,stringid)
    @PerActivity
    @Provides
    fun providesAdapter():MusicListDetialAdapter= MusicListDetialAdapter()

}