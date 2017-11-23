package com.example.happyghost.showtimeforkotlin.ui.book.community.item

import com.example.happyghost.showtimeforkotlin.bean.bookdate.BookHelp
import com.example.happyghost.showtimeforkotlin.bean.bookdate.CommentList
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/11/20.
 * @description
 */
interface IBookHelperBaseView:IBaseView {
    fun loadBookHelpDetail(data: BookHelp)

    fun loadBestComments(list: CommentList)

    fun loadBookHelpComments(list: CommentList)
}