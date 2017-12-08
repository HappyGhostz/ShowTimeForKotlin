package com.example.happyghost.showtimeforkotlin.inject.component

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.module.CrossTalkModule
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