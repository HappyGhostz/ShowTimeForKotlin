package com.example.happyghost.showtimeforkotlin.inject.module.picture

import com.example.happyghost.showtimeforkotlin.adapter.pictureadapter.BeautyPictureAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.ui.picture.beautypicture.BeautyPictureFragment
import com.example.happyghost.showtimeforkotlin.ui.picture.beautypicture.BeautyPicturePresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/12/14.
 * @description
 */
@Module
class BeautyPictureModule(beautyPictureFragment: BeautyPictureFragment) {
    var view = beautyPictureFragment
    @PerFragment
    @Provides
    fun providesPresenter():BeautyPicturePresenter = BeautyPicturePresenter(view)
    @PerFragment
    @Provides
    fun providesAdapter():BeautyPictureAdapter= BeautyPictureAdapter()
}