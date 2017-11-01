package com.example.happyghost.showtimeforkotlin.inject.module.bookmodule

import com.example.happyghost.showtimeforkotlin.RxBus.RxBus
import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.ui.book.read.ReadActivity
import com.example.happyghost.showtimeforkotlin.ui.book.read.ReadPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/9/29.
 * @description
 */
@Module
class ReadModule(readActivity: ReadActivity) {
    var view= readActivity
    @PerActivity
    @Provides
    fun providesPresenter(rxBus: RxBus):ReadPresenter = ReadPresenter(view,rxBus)
}