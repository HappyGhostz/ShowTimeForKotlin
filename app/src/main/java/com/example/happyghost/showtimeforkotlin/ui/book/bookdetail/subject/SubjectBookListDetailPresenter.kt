package com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.subject

import com.example.happyghost.showtimeforkotlin.bean.bookdate.BookListDetail
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
class SubjectBookListDetailPresenter(view: SubjectBookListDetailActivity, bookid: String?) :IBasePresenter {
    var mView:IBaseSubjectView = view
    var mBookId = bookid
    override fun getData() {
        RetrofitService.getBookListDetail(mBookId!!)
                .doOnSubscribe { mView.showLoading() }
                .compose(mView.bindToLife())
                .subscribe (object :Observer<BookListDetail>{
                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onError(e: Throwable) {
                        mView.showNetError(object :EmptyErrLayout.OnReTryListener{
                            override fun onReTry() {
                                getData()
                            }
                        })
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BookListDetail) {
                        mView.showBookListDetail(t)
                    }
                })
    }

    override fun getMoreData() {

    }
}