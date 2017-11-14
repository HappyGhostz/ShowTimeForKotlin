package com.example.happyghost.showtimeforkotlin.inject.module.bookmodule

import com.example.happyghost.showtimeforkotlin.RxBus.RxBus
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.BookRackAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.loacaldao.DaoSession
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
    fun providesPresenter(daoSession : DaoSession, rxBus: RxBus):BookRackPresent{
        return BookRackPresent(bookRackView,daoSession.localBookInfoDao,rxBus,daoSession.bookChapterInfoDao)
    }
    @PerFragment
    @Provides
    fun providesAdapter(): BookRackAdapter = BookRackAdapter()

}