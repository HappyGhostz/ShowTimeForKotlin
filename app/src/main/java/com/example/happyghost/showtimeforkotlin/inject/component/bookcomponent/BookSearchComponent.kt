package com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent

import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookSearchModule
import com.example.happyghost.showtimeforkotlin.ui.book.search.BookSearchActivity
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/11/21.
 * @description
 */
@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(BookSearchModule::class))
interface BookSearchComponent {
    fun inject(bookSearchActivity: BookSearchActivity)
}