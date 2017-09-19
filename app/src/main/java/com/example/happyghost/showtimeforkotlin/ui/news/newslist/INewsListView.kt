package com.example.happyghost.showtimeforkotlin.ui.news.newslist

import com.example.happyghost.showtimeforkotlin.bean.newsdata.NewsInfo
import com.example.happyghost.showtimeforkotlin.bean.newsdata.NewsMultiItem

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