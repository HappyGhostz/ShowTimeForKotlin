package com.example.happyghost.showtimeforkotlin.inject.module.crossmodule

import com.example.happyghost.showtimeforkotlin.adapter.crossadapter.FunnyPictureAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.ui.crosstalk.funnypicture.FunnyPictureFragment
import com.example.happyghost.showtimeforkotlin.ui.crosstalk.funnypicture.FunnyPicturePresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/12/9.
 * @description
 */
@Module
class FunnyPictureModule(funnyPictureFragment: FunnyPictureFragment) {
    var view = funnyPictureFragment
    @PerFragment
    @Provides
    fun providesPresenter():FunnyPicturePresenter = FunnyPicturePresenter(view)
    @PerFragment
    @Provides
    fun providesAdapter():FunnyPictureAdapter = FunnyPictureAdapter()
}