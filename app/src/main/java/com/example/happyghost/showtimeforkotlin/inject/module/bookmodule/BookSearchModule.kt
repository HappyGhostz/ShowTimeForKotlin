package com.example.happyghost.showtimeforkotlin.inject.module.bookmodule

import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.SearchHistoryAdapter
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.SearchResultAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.ui.book.search.BookSearchActivity
import com.example.happyghost.showtimeforkotlin.ui.book.search.BookSearchPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/11/21.
 * @description
 */
@Module
class BookSearchModule(bookSearchActivity: BookSearchActivity) {
    var view = bookSearchActivity
    @PerActivity
    @Provides
    fun providesPresenter():BookSearchPresenter= BookSearchPresenter(view)
    @PerActivity
    @Provides
    fun providesSearchHistoryAdapter():SearchHistoryAdapter= SearchHistoryAdapter()
    @PerActivity
    @Provides
    fun providesSearchResultAdapter(): SearchResultAdapter = SearchResultAdapter()
}