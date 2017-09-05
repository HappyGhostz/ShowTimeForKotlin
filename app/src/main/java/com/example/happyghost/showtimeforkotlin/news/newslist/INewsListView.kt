package com.example.happyghost.showtimeforkotlin.news.newslist

import com.example.happyghost.showtimeforkotlin.base.IBaseView
import com.example.happyghost.showtimeforkotlin.bean.NewsInfo

/**
 * @author Zhao Chenping
 * @creat 2017/9/5.
 * @description
 */
interface INewsListView<T>:IBaseView{
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
    /**
     * 加载广告数据
     */
    fun loadAdData(newsBean: NewsInfo)

}