package com.example.happyghost.showtimeforkotlin.inject.component.picturecomponent

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.picture.BeautyPictureModule
import com.example.happyghost.showtimeforkotlin.ui.picture.beautypicture.BeautyPictureFragment
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/12/14.
 * @description
 */
@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(BeautyPictureModule::class))
interface BeautyPictureComponent {
    fun inject(beautyPictureFragment: BeautyPictureFragment)
}