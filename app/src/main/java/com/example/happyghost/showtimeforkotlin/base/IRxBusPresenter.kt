package com.example.happyghost.showtimeforkotlin.base

import io.reactivex.functions.Consumer

/**
 * @author Zhao Chenping
 * @creat 2017/8/30.
 * @description
 */
interface IRxBusPresenter :IBasePresenter {
    /**
     * 注册
     * @param eventType
     * @param <T>
    </T> */
    abstract fun <T> registerRxBus(eventType: Class<T>, action: Consumer<T>)

    /**
     * 注销
     */
    abstract fun unregisterRxBus()
}