package com.example.happyghost.showtimeforkotlin.news.main

import com.example.happyghost.showtimeforkotlin.RxBus.RxBus
import com.example.happyghost.showtimeforkotlin.base.IRxBusPresenter
import com.example.happyghost.showtimeforkotlin.loacaldao.NewsTypeInfoDao
import com.example.happyghost.showtimeforkotlin.news.NewsMainFragment
import io.reactivex.functions.Consumer

/**
 * @author Zhao Chenping
 * @creat 2017/8/28.
 * @description
 */
class NewsMainPresenter: IRxBusPresenter {
    lateinit var mView :IBaseMainNewsView
    lateinit var mDao :NewsTypeInfoDao
    lateinit var mRxBus : RxBus
    constructor(view: NewsMainFragment, newsTypeInfoDao: NewsTypeInfoDao, rxBus: RxBus)  {
        mView = view
        mDao = newsTypeInfoDao
        mRxBus = rxBus
    }

    override fun <T> registerRxBus(eventType: Class<T>, action: Consumer<T>) {

    }

    override fun unregisterRxBus() {

    }

    override fun getData() {

    }

    override fun getMoreData() {

    }
}