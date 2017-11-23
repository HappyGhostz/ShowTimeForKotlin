package com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.community

import com.example.happyghost.showtimeforkotlin.bean.bookdate.BookReview
import com.example.happyghost.showtimeforkotlin.bean.bookdate.CommentList
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/11/20.
 * @description
 */
interface IReviewBaseView:IBaseView {
    fun showBookReviewDetail(data: BookReview)

    fun showBestComments(list: CommentList)

    fun showBookReviewComments(list: CommentList)
}