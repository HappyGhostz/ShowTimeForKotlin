package com.example.happyghost.showtimeforkotlin.ui.news.special

import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView
import com.example.happyghost.showtimeforkotlin.bean.newsdata.SpecialInfo
import com.example.happyghost.showtimeforkotlin.bean.newsdata.SpecialItem

/**
 * @author Zhao Chenping
 * @creat 2017/9/9.
 * @description
 */
interface INewsSpecialView:IBaseView {
    /**
     * 显示数据
     */
    fun loadData(specialItems:List<SpecialItem>)
    /**
     * 添加头部
     */
    fun loadBanner(specialInfo: SpecialInfo)
}