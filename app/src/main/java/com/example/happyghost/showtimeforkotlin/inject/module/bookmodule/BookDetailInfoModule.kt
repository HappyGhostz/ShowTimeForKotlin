package com.example.happyghost.showtimeforkotlin.inject.module.bookmodule

import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.HotReviewAdapter
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.RecommendBookListAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.loacaldao.DaoSession
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.BookDetailInfoActivity
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.BookDetailPresent
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/11/16.
 * @description
 */
@Module
class BookDetailInfoModule(bookDetailInfoActivity: BookDetailInfoActivity, mBookId: String) {
    var view = bookDetailInfoActivity
    var bookid = mBookId
    @PerActivity
    @Provides
    fun providesPresenter(daoSession : DaoSession):BookDetailPresent = BookDetailPresent(view,bookid,
            daoSession.localBookInfoDao,daoSession.bookChapterInfoDao)
    @PerActivity
    @Provides
    fun providesHotAdapter():HotReviewAdapter = HotReviewAdapter()
    @PerActivity
    @Provides
    fun providesRecommendBookListAdapter(): RecommendBookListAdapter = RecommendBookListAdapter()

}