package com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.community

import com.example.happyghost.showtimeforkotlin.bean.bookdata.HotReview
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
interface IBaseDetailReviewView:IBaseView {
    fun showBookDetailReviewList(list: List<HotReview.Reviews>)
    fun showBookDetailReviewMoreList(list: List<HotReview.Reviews>)

}