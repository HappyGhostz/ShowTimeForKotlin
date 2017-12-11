package com.example.happyghost.showtimeforkotlin.inject.component.crosscomponent

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.crossmodule.CrossTalkModule
import com.example.happyghost.showtimeforkotlin.ui.crosstalk.crossfragment.CrossTalkFragment
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/12/8.
 * @description
 */
@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(CrossTalkModule::class))
interface CrossTalkComponent {
    fun inject(crossTalkFragment: CrossTalkFragment)
}