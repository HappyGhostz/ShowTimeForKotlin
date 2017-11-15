package com.example.happyghost.showtimeforkotlin.ui.book.bookdetail

import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookDetail
import com.example.happyghost.showtimeforkotlin.bean.bookdata.HotReview
import com.example.happyghost.showtimeforkotlin.bean.bookdata.RecommendBookList
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * Created by e445 on 2017/11/15.
 */
interface IBookDetailBaseView:IBaseView {
    abstract fun loadBookDetail(data: BookDetail)

    abstract fun loadHotReview(list: List<HotReview.Reviews>)

    abstract fun loadRecommendBookList(list: List<RecommendBookList.RecommendBook>)
}