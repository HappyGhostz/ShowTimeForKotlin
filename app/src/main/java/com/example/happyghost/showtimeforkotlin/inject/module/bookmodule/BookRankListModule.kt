package com.example.happyghost.showtimeforkotlin.inject.module.bookmodule

import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.BookFemaleRankAdapter
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.BookMaleRankAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.ui.book.rank.BookRankListFragment
import com.example.happyghost.showtimeforkotlin.ui.book.rank.BookRankPresent
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/9/25.
 * @description
 */
@Module
class BookRankListModule(bookRankListFragment: BookRankListFragment) {
    var view = bookRankListFragment
    @PerFragment
    @Provides
    fun providesPresenter():BookRankPresent = BookRankPresent(view)
//    @PerFragment
//    @Provides
//    fun providesMaleAdapter(): BookMaleRankAdapter = BookMaleRankAdapter()
//    @PerFragment
//    @Provides
//    fun providesFemaleAdapter(): BookFemaleRankAdapter = BookFemaleRankAdapter()
}