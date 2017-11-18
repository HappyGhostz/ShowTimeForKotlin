package com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.discussion

import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
class BookDiscussionDetailPresenter(view: BookDiscussionDetailActivity, commendid: String) :IBasePresenter {
    var mView =view
    var mCommendId = commendid
    protected var start = 0
    protected var limit = 50
    override fun getData() {
        RetrofitService.getBookDisscussionDetail(mCommendId)
                .compose(mView.bindToLife())
                .subscribe {
                    mView.showBookDisscussionDetail(it)
                }
        RetrofitService.getBestComments(mCommendId)
                .compose(mView.bindToLife())
                .subscribe {
                    mView.showBestComments(it)
                }
        RetrofitService.getBookDisscussionComments(mCommendId,start.toString(),limit.toString())
                .compose(mView.bindToLife())
                .subscribe {
                    mView.showBookDisscussionComments(it)
                }
    }

    override fun getMoreData() {

    }
}