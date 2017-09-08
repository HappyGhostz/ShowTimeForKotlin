package com.example.happyghost.showtimeforkotlin.news.newslist

import com.example.happyghost.showtimeforkotlin.base.IBaseView
import com.example.happyghost.showtimeforkotlin.bean.NewsInfo
import com.example.happyghost.showtimeforkotlin.bean.NewsMultiItem

/**
 * @author Zhao Chenping
 * @creat 2017/9/5.
 * @description
 */
interface INewsListView:ILoadDataView<List<NewsMultiItem>>{
    /**
     * 加载广告数据
     * @param newsBean 新闻
     */
    fun loadAdData(newsBean: NewsInfo)

}