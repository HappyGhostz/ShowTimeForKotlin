package com.example.happyghost.showtimeforkotlin.news.main

import com.example.happyghost.showtimeforkotlin.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.base.IRxBusPresenter
import io.reactivex.functions.Consumer

/**
 * @author Zhao Chenping
 * @creat 2017/8/28.
 * @description
 */
class NewsMainPresenter : IRxBusPresenter {
    override fun <T> registerRxBus(eventType: Class<T>, action: Consumer<T>) {

    }

    override fun unregisterRxBus() {

    }

    override fun getData() {

    }

    override fun getMoreData() {

    }
}