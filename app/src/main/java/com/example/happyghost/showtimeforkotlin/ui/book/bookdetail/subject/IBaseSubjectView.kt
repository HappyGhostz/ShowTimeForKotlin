package com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.subject

import com.example.happyghost.showtimeforkotlin.bean.bookdate.BookListDetail
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
interface IBaseSubjectView:IBaseView {
    fun showBookListDetail(data: BookListDetail)
}