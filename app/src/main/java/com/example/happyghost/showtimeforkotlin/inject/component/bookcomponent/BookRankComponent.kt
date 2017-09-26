package com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookRankListModule
import com.example.happyghost.showtimeforkotlin.ui.book.rank.BookRankListFragment
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/9/25.
 * @description
 */
@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(BookRankListModule::class))
interface BookRankComponent {
    fun inject(bookRankListFragment: BookRankListFragment)
}