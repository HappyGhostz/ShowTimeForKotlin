package com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookRackMoudle
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.ClassifyDetailModule
import com.example.happyghost.showtimeforkotlin.ui.book.classify.classifydetail.ClassifyDetailFragment
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/11/15.
 * @description
 */
@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(ClassifyDetailModule::class))
interface ClassifyDetailComponent {
    fun inject(classifyDetailFragment: ClassifyDetailFragment)
}