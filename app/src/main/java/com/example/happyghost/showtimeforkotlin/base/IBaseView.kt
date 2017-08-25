package com.example.happyghost.showtimeforkotlin.base

import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import com.trello.rxlifecycle2.LifecycleTransformer


/**
 * @author Zhao Chenping
 * @creat 2017/8/23.
 * @description
 */
interface IBaseView {
    /**
     * 显示加载动画
     */
    fun showLoading()
    /**
     * 隐藏加载
     */
    fun hideLoading()
    /**
     * 显示网络错误
     * @param onRetryListener 点击监听
     */
    fun showNetError(onReTryListener: EmptyErrLayout.OnReTryListener)
    /**
     * 绑定生命周期
     */
    fun <T> bindToLife(): LifecycleTransformer<T>
}