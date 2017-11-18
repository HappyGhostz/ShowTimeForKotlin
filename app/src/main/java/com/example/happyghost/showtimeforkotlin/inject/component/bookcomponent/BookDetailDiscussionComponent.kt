package com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookDetailDiscussionModule
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.community.BookDetailDiscussionFragment
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(BookDetailDiscussionModule::class))
interface BookDetailDiscussionComponent {
    fun inject(fragmet: BookDetailDiscussionFragment)
}