package com.example.happyghost.showtimeforkotlin.inject.module.newsmodule

import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.ui.news.photonews.PhotoSetNewsActivity
import com.example.happyghost.showtimeforkotlin.ui.news.photonews.PhotoSetPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/9/13.
 * @description
 */
@Module
class PhotoSetNewsModule(photoSetNewsActivity: PhotoSetNewsActivity, photoId: String) {
    var view = photoSetNewsActivity
    var id =photoId
    @PerActivity
    @Provides
    fun providesPresenter():PhotoSetPresenter{
        return PhotoSetPresenter(view,id)
    }
}