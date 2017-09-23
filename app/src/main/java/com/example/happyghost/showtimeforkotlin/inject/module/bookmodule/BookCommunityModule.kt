package com.example.happyghost.showtimeforkotlin.inject.module.bookmodule

import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.BookCommunityAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.ui.book.community.BookCommunityListFragment
import com.example.happyghost.showtimeforkotlin.ui.book.community.BookCommunityPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/9/19.
 * @description
 */
@Module
class BookCommunityModule(bookCommunityListFragment: BookCommunityListFragment) {
    var view = bookCommunityListFragment
    @PerFragment
    @Provides
    fun providesPresenter():BookCommunityPresenter = BookCommunityPresenter(view)
    @PerFragment
    @Provides
    fun providesAdapter(): BookCommunityAdapter =BookCommunityAdapter()
}