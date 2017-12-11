package com.example.happyghost.showtimeforkotlin.inject.component.crosscomponent

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.crossmodule.FunnyPictureModule
import com.example.happyghost.showtimeforkotlin.ui.crosstalk.funnypicture.FunnyPictureFragment
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/12/9.
 * @description
 */
@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(FunnyPictureModule::class))
interface FunnyPictureComponent {
    fun inject(funnyPictureFragment: FunnyPictureFragment)
}