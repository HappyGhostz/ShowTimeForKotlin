package com.example.happyghost.showtimeforkotlin.inject.component

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.module.BookRackMoudle
import com.example.happyghost.showtimeforkotlin.ui.book.rack.BookRackListFragment
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/9/18.
 * @description
 */
@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(BookRackMoudle::class))
interface BookRackComponent {
    fun inject(bookRackListFragment: BookRackListFragment)
}