package com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.SubRankListModule
import com.example.happyghost.showtimeforkotlin.ui.book.rank.iteminfo.SubRankFragment
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/11/20.
 * @description
 */
@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(SubRankListModule::class))
interface SubRankListComponent {
    fun inject(subRankFragment: SubRankFragment)
}