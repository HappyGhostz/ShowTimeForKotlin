package com.example.happyghost.showtimeforkotlin.ui.book.rank.iteminfo

import com.example.happyghost.showtimeforkotlin.bean.bookdata.BooksByCats
import com.example.happyghost.showtimeforkotlin.bean.bookdata.Rankings
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.ArrayList

/**
 * @author Zhao Chenping
 * @creat 2017/11/20.
 * @description
 */
class SubRankListPresenter(view: SubRankFragment, type: String) :IBasePresenter {
    var mView = view
    var mRankType = type
    override fun getData() {
       RetrofitService.getRanking(mRankType)
               .doOnSubscribe { mView.showLoading() }
               .compose(mView.bindToLife())
               .subscribe(object :Observer<Rankings>{
                   override fun onSubscribe(d: Disposable) {
                   }

                   override fun onNext(t: Rankings) {
                       val books = t.ranking
                       if (books != null) {
                           mView.loadRankList(books)
                       }
                   }

                   override fun onError(e: Throwable) {
                       mView.showNetError(object :EmptyErrLayout.OnReTryListener{
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