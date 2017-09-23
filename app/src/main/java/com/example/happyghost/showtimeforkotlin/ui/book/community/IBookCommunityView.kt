package com.example.happyghost.showtimeforkotlin.ui.book.community

import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookHelpList
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/9/19.
 * @description
 */
interface IBookCommunityView:IBaseView {

    fun loadCommunityList(list: List<BookHelpList.HelpsBean>)

    fun loadMoreCommunity(list: List<BookHelpList.HelpsBean>)
    fun loadNoData()
}