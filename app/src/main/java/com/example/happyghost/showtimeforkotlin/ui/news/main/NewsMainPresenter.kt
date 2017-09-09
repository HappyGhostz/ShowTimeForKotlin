package com.example.happyghost.showtimeforkotlin.ui.news.main

import android.util.Log
import com.example.happyghost.showtimeforkotlin.RxBus.RxBus
import com.example.happyghost.showtimeforkotlin.ui.base.IRxBusPresenter
import com.example.happyghost.showtimeforkotlin.loacaldao.NewsTypeInfo
import com.example.happyghost.showtimeforkotlin.loacaldao.NewsTypeInfoDao
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * @author Zhao Chenping
 * @creat 2017/8/28.
 * @description
 */
class NewsMainPresenter(): IRxBusPresenter {
    lateinit var mView :IBaseMainNewsView
    lateinit var mDao :NewsTypeInfoDao
    lateinit var mRxBus : RxBus
    constructor(view: NewsMainFragment, newsTypeInfoDao: NewsTypeInfoDao, rxBus: RxBus):this() {
        mView = view
        mDao = newsTypeInfoDao
        mRxBus = rxBus
    }
    fun showString():String{
        return "NewsMainPresenter"
    }

    override fun <T> registerRxBus(eventType: Class<T>, action: Consumer<T>) {
        val disposable = mRxBus.doSubscribe(eventType, action, Consumer<Throwable> {
            throwable -> Log.e("NewsMainPresenter", throwable.toString()) })
        mRxBus.addSubscription(this, disposable)
    }

    override fun unregisterRxBus() {
        mRxBus.unSubscribe(this)
    }

    override fun getData() {
        Observable.create<List<NewsTypeInfo>> {
            //发送查询到的数据
            it.onNext( mDao.queryBuilder().build().forCurrentThread().list())
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t -> mView.loadData(t)
                })
    }

    override fun getMoreData() {

    }
}