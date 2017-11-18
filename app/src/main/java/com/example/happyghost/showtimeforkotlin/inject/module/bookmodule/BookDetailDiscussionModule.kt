package com.example.happyghost.showtimeforkotlin.inject.module.bookmodule

import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.BookDetailDiscussionAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.community.BookDetailDiscussionFragment
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.community.BookDetailDiscussionPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
@Module
class BookDetailDiscussionModule(bookDetailDiscussionFragment: BookDetailDiscussionFragment, mBookId: String) {
    var view = bookDetailDiscussionFragment
    var bookid = mBookId
    @PerFragment
    @Provides
    fun providesPresenter(): BookDetailDiscussionPresenter = BookDetailDiscussionPresenter(view,bookid)
    @PerFragment
    @Provides
    fun providesAdapter(): BookDetailDiscussionAdapter = BookDetailDiscussionAdapter()

}