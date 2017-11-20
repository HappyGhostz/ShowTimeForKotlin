package com.example.happyghost.showtimeforkotlin.ui.book.rank.iteminfo

import com.example.happyghost.showtimeforkotlin.bean.bookdata.Rankings
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
class SubOtherRankListPresenter(view: SubOtherHomeRankActivity, otherid: String) :IBasePresenter {
    var mView = view
    var mSubOtherId = otherid
    override fun getData() {
        RetrofitService.getRanking(mSubOtherId)
                .doOnSubscribe { mView.showLoading() }
                .compose(mView.bindToLife())
                .subscribe(object :Observer<Rankings>{
                    override fun onNext(t: Rankings) {
                        mView.loadRankList(t.ranking!!)
                    }

                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onSubscribe(d: Disposable) {

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