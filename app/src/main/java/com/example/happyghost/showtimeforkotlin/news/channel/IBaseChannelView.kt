package com.example.happyghost.showtimeforkotlin.news.channel

import com.example.happyghost.showtimeforkotlin.base.IBaseView
import com.example.happyghost.showtimeforkotlin.loacaldao.NewsTypeInfo

/**
 * @author Zhao Chenping
 * @creat 2017/9/4.
 * @description
 */
interface IBaseChannelView {
    /**
     * 显示数据
     * @param checkList     选中栏目
     * @param uncheckList   未选中栏目
     */
     fun loadData(checkList: ArrayList<NewsTypeInfo>, uncheckList: ArrayList<NewsTypeInfo>)
}