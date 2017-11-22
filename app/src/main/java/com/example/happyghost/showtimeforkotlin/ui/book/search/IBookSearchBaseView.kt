package com.example.happyghost.showtimeforkotlin.ui.book.search

import com.example.happyghost.showtimeforkotlin.bean.bookdata.SearchDetail
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/11/21.
 * @description
 */
interface IBookSearchBaseView:IBaseView {
    abstract fun showHotWordList(list: List<String>)

    abstract fun showAutoCompleteList(list: List<String>)

    abstract fun showSearchResultList(list: List<SearchDetail.SearchBooks>)
}