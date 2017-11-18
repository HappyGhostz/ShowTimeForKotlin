package com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent

import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookDiscussionDetailModule
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.discussion.BookDiscussionDetailActivity
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(BookDiscussionDetailModule::class))
interface BookDiscussionDetailComponent {
    fun inject(bookDiscussionDetailActivity: BookDiscussionDetailActivity)
}