package com.example.happyghost.showtimeforkotlin.inject.module.bookmodule

import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.DisscussionAdapter
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.DisscussionBestAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.ui.book.community.item.BookHelpDetailActivity
import com.example.happyghost.showtimeforkotlin.ui.book.community.item.BookHelpDetailPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/11/20.
 * @description
 */
@Module
class BookHelpDetailModule(bookHelpDetailActivity: BookHelpDetailActivity, mBookId: String) {
    var view=bookHelpDetailActivity
    var bookid = mBookId
    @PerActivity
    @Provides
    fun providerPresenter(): BookHelpDetailPresenter = BookHelpDetailPresenter(view,bookid)
    @PerActivity
    @Provides
    fun providerDisscussionAdapter(): DisscussionAdapter = DisscussionAdapter()
    @PerActivity
    @Provides
    fun providerDisscussionBestAdapter(): DisscussionBestAdapter = DisscussionBestAdapter()
}