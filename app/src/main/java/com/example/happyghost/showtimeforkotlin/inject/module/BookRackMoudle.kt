package com.example.happyghost.showtimeforkotlin.inject.module

import com.example.happyghost.showtimeforkotlin.adapter.BookRackAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.ui.book.rack.BookRackListFragment
import com.example.happyghost.showtimeforkotlin.ui.book.rack.BookRackPresent
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/9/18.
 * @description
 */
@Module
class BookRackMoudle(bookRackListFragment: BookRackListFragment) {
    var bookRackView = bookRackListFragment
    @PerFragment
    @Provides
    fun providesPresenter():BookRackPresent{
        return BookRackPresent(bookRackView)
    }
    @PerFragment
    @Provides
    fun providesAdapter(): BookRackAdapter = BookRackAdapter()

}