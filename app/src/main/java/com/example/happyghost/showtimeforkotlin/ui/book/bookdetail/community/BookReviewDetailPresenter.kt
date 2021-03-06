package com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.community

import com.example.happyghost.showtimeforkotlin.bean.bookdate.CommentList
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Zhao Chenping
 * @creat 2017/11/20.
 * @description
 */
class BookReviewDetailPresenter() :IBasePresenter {
    lateinit var mView :IReviewBaseView
    lateinit var mBookId :String
    protected var start = 0
    protected var limit = 50
    constructor(view: BookReviewDetailActivity, bookid: String):this(){
        mView = view
        mBookId = bookid
    }
    override fun getData() {
        start=0
        RetrofitService.getBookReviewDetail(mBookId)
                .compose(mView.bindToLife())
                .subscribe {
                    mView.showBookReviewDetail(it)
                }
        getBestComments()
        getComments()
    }

    private fun getBestComments() {
        RetrofitService.getBestComments(mBookId)
                .compose(mView.bindToLife())
                .subscribe {
                    mView.showBestComments(it)
                }
    }

    private fun getComments() {
        RetrofitService.getBookReviewComments(mBookId, start.toString(), limit.toString())
                .doOnSubscribe { mView.showLoading() }
                .compose(mView.bindToLife())
                .subscribe(object : Observer<CommentList> {
                    override fun onNext(t: CommentList) {
                        mView.showBookReviewComments(t)
                        start += t.comments?.size!!
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        mView.showNetError(object : EmptyErrLayout.OnReTryListener {
                            override fun onReTry() {
                                getData()
                            }
                        })
                    }

                    override fun onComplete() {
                        mView.hideLoading()
                    }
                })
    }

    override fun getMoreData() {
    }
}