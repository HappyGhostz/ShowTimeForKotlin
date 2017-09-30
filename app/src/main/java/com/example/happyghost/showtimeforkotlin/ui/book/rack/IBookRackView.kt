package com.example.happyghost.showtimeforkotlin.ui.book.rack

import com.example.happyghost.showtimeforkotlin.bean.bookdata.Recommend
import com.example.happyghost.showtimeforkotlin.loacaldao.LocalBookInfo
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/9/15.
 * @description
 */
interface IBookRackView:IBaseView {
    fun loadRecommendList(list: List<Recommend.RecommendBooks>)
    /**
     * 加载保存数据库中的书籍
     */
    fun loadLocalBookList(list: List<LocalBookInfo>)

}