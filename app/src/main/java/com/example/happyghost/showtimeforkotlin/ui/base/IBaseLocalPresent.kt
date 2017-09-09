package com.example.happyghost.showtimeforkotlin.ui.base

/**
 * @author Zhao Chenping
 * @creat 2017/9/4.
 * @description
 */
interface IBaseLocalPresent<T> :IBasePresenter{
    /**
     * 插入数据
     */
    fun insert (data:T)
    /**
     * 删除数据
     */
    fun delete(data: T)
    /**
     * 更新数据
     */
    fun upData(list:MutableList<T>)

    /**
     * 交换
     * @param fromPos
     * @param toPos
     */
    fun swap(fromPos: Int, toPos: Int)
}