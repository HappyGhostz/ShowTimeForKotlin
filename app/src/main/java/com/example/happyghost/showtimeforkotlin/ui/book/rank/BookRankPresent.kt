package com.example.happyghost.showtimeforkotlin.ui.book.rank

import com.example.happyghost.showtimeforkotlin.bean.bookdata.RankingListBean
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Zhao Chenping
 * @creat 2017/9/23.
 * @description
 */
class BookRankPresent(view: BookRankListFragment) :IBasePresenter {
    var mView:IBookRankView = view
    override fun getData() {
        RetrofitService.getBookRankList()
                .doOnSubscribe { mView.showLoading() }
                .compose(mView.bindToLife())
                .subscribe(object :Observer<RankingListBean>{
                    override fun onNext(t: RankingListBean) {
                        mView.LoadRankList(t)
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