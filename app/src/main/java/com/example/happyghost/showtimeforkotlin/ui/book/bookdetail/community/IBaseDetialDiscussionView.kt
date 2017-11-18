package com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.community

import com.example.happyghost.showtimeforkotlin.bean.bookdata.DiscussionList
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
interface IBaseDetialDiscussionView:IBaseView {
    fun showBookDetailDiscussionList(list: List<DiscussionList.PostsBean>)
    fun showBookDetailDiscussionMoreList(list: List<DiscussionList.PostsBean>)
}