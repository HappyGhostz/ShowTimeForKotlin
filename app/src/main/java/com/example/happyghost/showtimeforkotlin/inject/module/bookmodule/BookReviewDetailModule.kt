package com.example.happyghost.showtimeforkotlin.inject.module.bookmodule

import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.DisscussionAdapter
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.DisscussionBestAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.community.BookReviewDetailActivity
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.community.BookReviewDetailPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/11/20.
 * @description
 */
@Module
class BookReviewDetailModule(bookReviewDetailActivity: BookReviewDetailActivity, mBookId: String) {
    var view = bookReviewDetailActivity
    var bookid =mBookId
    @PerActivity
    @Provides
    fun providerPresenter(): BookReviewDetailPresenter = BookReviewDetailPresenter(view,bookid)
    @PerActivity
    @Provides
    fun providerDisscussionAdapter(): DisscussionAdapter = DisscussionAdapter()
    @PerActivity
    @Provides
    fun providerDisscussionBestAdapter(): DisscussionBestAdapter = DisscussionBestAdapter()
}