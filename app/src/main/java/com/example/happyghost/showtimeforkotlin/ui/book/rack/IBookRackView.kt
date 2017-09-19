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

    //    void showBookToc(String bookId, List<BookMixAToc.mixToc.Chapters> list);
//    fun loadCommunityList(list: List<BookHelpList.HelpsBean>)

//    fun loadMoreCommunity(list: List<BookHelpList.HelpsBean>)
}