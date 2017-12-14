package com.example.happyghost.showtimeforkotlin.inject.module.picture

import com.example.happyghost.showtimeforkotlin.adapter.pictureadapter.WelfPictureAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.ui.picture.WelfarePicture.WelfarePictureFragment
import com.example.happyghost.showtimeforkotlin.ui.picture.WelfarePicture.WelfarePicturePresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/12/12.
 * @description
 */
@Module
class WelfarePictureModule(welfarePictureFragment: WelfarePictureFragment) {
    var view = welfarePictureFragment
    @PerFragment
    @Provides
    fun providesPresenter(): WelfarePicturePresenter = WelfarePicturePresenter(view)
    @PerFragment
    @Provides
    fun providesAdapter():WelfPictureAdapter =WelfPictureAdapter()
}