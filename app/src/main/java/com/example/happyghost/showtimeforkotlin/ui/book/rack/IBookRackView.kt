package com.example.happyghost.showtimeforkotlin.ui.book.rack

import com.example.happyghost.showtimeforkotlin.bean.bookdata.Recommend
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/9/15.
 * @description
 */
interface IBookRackView:IBaseView {
    fun loadRecommendList(list: List<Recommend.RecommendBooks>)


}