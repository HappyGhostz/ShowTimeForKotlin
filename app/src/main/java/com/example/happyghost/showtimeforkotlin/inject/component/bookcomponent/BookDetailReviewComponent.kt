package com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookDetailReviewModule
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.community.BookDetailReviewFragment
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/11/18.
 * @description
 */
@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(BookDetailReviewModule::class))
interface BookDetailReviewComponent {
    fun inject(fragment: BookDetailReviewFragment)
}