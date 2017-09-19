package com.example.happyghost.showtimeforkotlin.inject.module.bookmodule


import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.BookFemaleClassifyAdapter
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.BookMaleClassifyAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.ui.book.classify.BookClassifyListFragment
import com.example.happyghost.showtimeforkotlin.ui.book.classify.BookClassifyPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/9/19.
 * @description
 */
@Module
class BookClassifyListModule(bookClassifyListFragment: BookClassifyListFragment) {
    var view = bookClassifyListFragment
    @PerFragment
    @Provides
    fun providesPresenter():BookClassifyPresenter = BookClassifyPresenter(view)
    @PerFragment
    @Provides
    fun providesMaleAdapter(): BookMaleClassifyAdapter = BookMaleClassifyAdapter()
    @PerFragment
    @Provides
    fun providesFemaleAdapter(): BookFemaleClassifyAdapter = BookFemaleClassifyAdapter()
}