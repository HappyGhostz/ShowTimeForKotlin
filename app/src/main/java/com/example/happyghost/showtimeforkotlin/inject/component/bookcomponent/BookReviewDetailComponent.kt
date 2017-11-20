package com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent

import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookReviewDetailModule
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.community.BookReviewDetailActivity
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/11/20.
 * @description
 */
@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(BookReviewDetailModule::class))
interface BookReviewDetailComponent {
    fun inject(activity: BookReviewDetailActivity)
}