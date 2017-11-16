package com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent

import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookDetailInfoModule
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.BookDetailInfoActivity
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/11/16.
 * @description
 */
@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(BookDetailInfoModule::class))
interface BookDetailInfoComponent {
    fun inject(bookDetailInfoActivity: BookDetailInfoActivity)
}