package com.example.happyghost.showtimeforkotlin.ui.book.rack

import com.example.happyghost.showtimeforkotlin.bean.bookdata.Recommend
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.utils.StringUtils
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Zhao Chenping
 * @creat 2017/9/15.
 * @description
 */
class BookRackPresent(view: IBookRackView) :IBasePresenter {
    var mView = view
    override fun getData() {
        RetrofitService.getBookRack("male")
                .doOnSubscribe {
                    mView.showLoading()
                }
                .compose(mView.bindToLife())
                .subscribe(object :Observer<Recommend>{
                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: Recommend) {
                        val books = t.books
                        mView.loadRecommendList(books!!)
                    }

                    override fun onError(e: Throwable) {
                        mView.showNetError(object :EmptyErrLayout.OnReTryListener{
                            override fun onReTry() {
                                getData()
                            }
                        })
                    }
                })
    }

    override fun getMoreData() {

    }
}