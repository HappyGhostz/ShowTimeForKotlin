package com.example.happyghost.showtimeforkotlin.ui.book.search

import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService

/**
 * @author Zhao Chenping
 * @creat 2017/11/21.
 * @description
 */
class BookSearchPresenter(view: BookSearchActivity) :IBasePresenter {
    var mView = view
    override fun getData() {

    }

    override fun getMoreData() {

    }
    fun getHotWordList(){
        RetrofitService.getHotWord()
                .compose(mView.bindToLife())
                .subscribe {
                    val list = it.hotWords
                    if (list != null && !list.isEmpty()) {
                        mView.showHotWordList(list)
                    }
                }
    }
    fun getAutoCompleteList(string: String){
        RetrofitService.getAutoComplete(string)
                .compose(mView.bindToLife())
                .subscribe {
                    val list = it.keywords
                    if (list != null && !list.isEmpty() ) {
                        mView.showAutoCompleteList(list)
                    }
                }
    }
    fun getSearchResultList(string: String){
        RetrofitService.getSearchResult(string)
                .compose(mView.bindToLife())
                .subscribe {
                    val list = it.books
                    if (list != null && !list.isEmpty()) {
                        mView.showSearchResultList(list)
                    }
                }
    }
}