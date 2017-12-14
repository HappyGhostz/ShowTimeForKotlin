package com.example.happyghost.showtimeforkotlin.inject.component.picturecomponent

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.picture.WelfarePictureModule
import com.example.happyghost.showtimeforkotlin.ui.picture.WelfarePicture.WelfarePictureFragment
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/12/14.
 * @description
 */
@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(WelfarePictureModule::class))
interface WelfarePictureComponent {
    fun inject(welfarePictureFragment: WelfarePictureFragment)
}