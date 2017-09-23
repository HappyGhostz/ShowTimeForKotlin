package com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookCommunityModule
import com.example.happyghost.showtimeforkotlin.ui.book.community.BookCommunityListFragment
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/9/19.
 * @description
 */
@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(BookCommunityModule::class))
interface BookCommunityComponent {
    fun inject(bookCommunityListFragment: BookCommunityListFragment)
}