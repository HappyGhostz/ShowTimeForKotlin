package com.example.happyghost.showtimeforkotlin.inject.module.bookmodule

import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.DisscussionAdapter
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.DisscussionBestAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.discussion.BookDiscussionDetailActivity
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.discussion.BookDiscussionDetailPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
@Module
class BookDiscussionDetailModule(bookDiscussionDetailActivity: BookDiscussionDetailActivity, mCommendId: String) {
    var view=bookDiscussionDetailActivity
    var commendid=mCommendId
    @PerActivity
    @Provides
    fun providesPresenter():BookDiscussionDetailPresenter= BookDiscussionDetailPresenter(view,commendid)
    @PerActivity
    @Provides
    fun providesBestAdapter():DisscussionBestAdapter=DisscussionBestAdapter()
    @PerActivity
    @Provides
    fun providesDisscusssionAdapter():DisscussionAdapter=DisscussionAdapter()
}