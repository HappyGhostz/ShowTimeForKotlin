package com.example.happyghost.showtimeforkotlin.ui.book.classify.classifydetail

import com.example.happyghost.showtimeforkotlin.bean.bookdata.BooksByCats
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/11/15.
 * @description
 */
interface ICategoryBaseView:IBaseView {
    /**
     * 加载数据
     * @param data
     */
    abstract fun loadCategoryList(data: BooksByCats)

    /**
     * 加载更多数据
     * @param data
     */
    abstract fun loadMoreCategoryList(data: BooksByCats)
}