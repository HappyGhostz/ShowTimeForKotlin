package com.example.happyghost.showtimeforkotlin.ui.news.newslist

import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/9/6.
 * @description
 */
interface ILoadDataView<T> :IBaseView{
    /**
     * 加载数据
     */
    fun loadData(data:T)
    /**
     * 加载更多数据
     */
    fun loadMoreData(moreData:T)
    /**
     * 没有数据
     */
    fun loadNoData()
}