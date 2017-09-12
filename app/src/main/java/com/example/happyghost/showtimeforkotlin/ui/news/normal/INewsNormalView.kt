package com.example.happyghost.showtimeforkotlin.ui.news.normal

import com.example.happyghost.showtimeforkotlin.bean.NewsDetailInfo
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/9/12.
 * @description
 */
interface INewsNormalView :IBaseView{
    /**
     * 显示数据
     * @param newsDetailBean 新闻详情
     */
    abstract fun loadData(newsDetailBean: NewsDetailInfo)
}