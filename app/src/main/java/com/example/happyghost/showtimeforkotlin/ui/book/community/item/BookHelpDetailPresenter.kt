package com.example.happyghost.showtimeforkotlin.ui.book.community.item

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
class BookHelpDetailPresenter(view: BookHelpDetailActivity, bookid: String) :IBasePresenter {
    var mHelpView = view
    var mBookId = bookid
    protected var start = 0
    protected var limit = 50
    override fun getData() {
        RetrofitService.getBookHelpDetail(mBookId)
                .compose(mHelpView.bindToLife())
                .subscribe {
                    mHelpView.loadBookHelpDetail(it)
                }
        RetrofitService.getBestComments(mBookId)
                .compose(mHelpView.bindToLife())
                .subscribe {
                    mHelpView.loadBestComments(it)
                }
        RetrofitService.getBookReviewComments(mBookId,start.toString(),limit.toString())
                .doOnSubscribe {
                    mHelpView.showLoading()
                }
                .compose(mHelpView.bindToLife())
                .subscribe(object : Observer<CommentList> {
                    override fun onComplete() {
                        mHelpView.hideLoading()
                    }

                    override fun onNext(t: CommentList) {
                        mHelpView.loadBookHelpComments(t)
                    }

                    override fun onError(e: Throwable) {
                        mHelpView.showNetError(object : EmptyErrLayout.OnReTryListener{
                            override fun onReTry() {
                                getData()
                            }
                        })
                    }

                    override fun onSubscribe(d: Disposable) {
                    }
                })
    }

    override fun getMoreData() {

    }
}