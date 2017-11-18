package com.example.happyghost.showtimeforkotlin.inject.module.bookmodule

import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.BookDetailReviewAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.community.BookDetailReviewFragment
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.community.BookDetailReviewPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/11/18.
 * @description
 */
@Module
class BookDetailReviewModule(bookDetailReviewFragment: BookDetailReviewFragment, mBookId: String) {
    var view= bookDetailReviewFragment
    var bookid = mBookId
    @PerFragment
    @Provides
    fun providesPresenter(): BookDetailReviewPresenter=BookDetailReviewPresenter(view,bookid)
    @PerFragment
    @Provides
    fun providesAdapter(): BookDetailReviewAdapter =BookDetailReviewAdapter()
}