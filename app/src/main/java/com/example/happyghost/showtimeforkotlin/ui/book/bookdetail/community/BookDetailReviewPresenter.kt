package com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.community

import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.bean.bookdata.HotReview
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.toast

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
class BookDetailReviewPresenter(view: BookDetailReviewFragment, bookid: String) :IBasePresenter {
    var mView:IBaseDetailReviewView= view
    var mBook =bookid
    protected var start = 0
    protected var limit = 20
    override fun getData() {
        start =0
        RetrofitService.getBookDetailReviewList(mBook, "updated", start.toString(), limit .toString())
                .doOnSubscribe { mView.showLoading() }
                .compose(mView.bindToLife())
                .subscribe(object :Observer<HotReview>{
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        mView.showNetError(object :EmptyErrLayout.OnReTryListener{
                            override fun onReTry() {
                                getData()
                            }
                        })
                    }

                    override fun onNext(t: HotReview) {
                        val reviews = t.reviews
                        if(reviews!=null){
                            mView.showBookDetailReviewList(reviews)
                            start+=reviews.size
                        }
                    }

                    override fun onComplete() {
                       mView.hideLoading()
                    }
                })
    }

    override fun getMoreData() {
        RetrofitService.getBookDetailReviewList(mBook, "updated", start.toString(), (limit+start) .toString())
                .compose(mView.bindToLife())
                .subscribe(object :Observer<HotReview>{
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        AppApplication.instance.getContext().toast("无数据!")
                    }

                    override fun onNext(t: HotReview) {
                        val reviews = t.reviews
                        if(reviews!=null){
                            mView.showBookDetailReviewList(reviews)
                            start+=reviews.size
                        }else if(t==null) {
                            AppApplication.instance.getContext().toast("无数据!")
                        }
                    }

                    override fun onComplete() {
                    }
                })
    }
}