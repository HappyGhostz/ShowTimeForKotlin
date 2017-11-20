package com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent

import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookHelpDetailModule
import com.example.happyghost.showtimeforkotlin.ui.book.community.item.BookHelpDetailActivity
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/11/20.
 * @description
 */
@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(BookHelpDetailModule::class))
interface BookHelpDetailComponent {
    fun inject(bookHelpDetailActivity: BookHelpDetailActivity)
}