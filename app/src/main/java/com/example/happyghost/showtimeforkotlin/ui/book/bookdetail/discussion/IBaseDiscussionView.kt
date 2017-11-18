package com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.discussion

import com.example.happyghost.showtimeforkotlin.bean.bookdata.CommentList
import com.example.happyghost.showtimeforkotlin.bean.bookdata.Disscussion
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
interface IBaseDiscussionView:IBaseView {
    fun showBookDisscussionDetail(disscussion: Disscussion)

    fun showBestComments(list: CommentList)

    fun showBookDisscussionComments(list: CommentList)
}