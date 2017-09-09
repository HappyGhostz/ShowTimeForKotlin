package com.example.happyghost.showtimeforkotlin.ui.news.main

import com.example.happyghost.showtimeforkotlin.loacaldao.NewsTypeInfo

/**
 * @author Zhao Chenping
 * @creat 2017/8/28.
 * @description
 */
interface IBaseMainNewsView {
    /**
     * 加载选中的条目
     */
    fun loadData(checkList:List<NewsTypeInfo>)
}