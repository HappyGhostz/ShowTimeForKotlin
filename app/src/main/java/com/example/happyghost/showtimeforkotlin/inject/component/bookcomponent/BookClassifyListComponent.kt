package com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent

import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookClassifyListModule
import com.example.happyghost.showtimeforkotlin.ui.book.classify.BookClassifyListFragment
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/9/19.
 * @description
 */
@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(BookClassifyListModule::class))
interface BookClassifyListComponent {
    fun inject(bookClassifyListFragment: BookClassifyListFragment)
}