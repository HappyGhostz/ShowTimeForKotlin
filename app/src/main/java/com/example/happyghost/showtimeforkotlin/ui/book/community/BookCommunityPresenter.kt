package com.example.happyghost.showtimeforkotlin.ui.book.community

import com.example.happyghost.showtimeforkotlin.bean.bookdate.BookHelpList
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Zhao Chenping
 * @creat 2017/9/19.
 * @description
 */
class BookCommunityPresenter(view: BookCommunityListFragment) :IBasePresenter {
    var mView :IBookCommunityView = view
    private var addStart = 0
    override fun getData() {
        addStart =0
         RetrofitService.getBookHelpListInfo((ConsTantUtils.START_PAGE+addStart).toString(),ConsTantUtils.LIMIT_POSITION.toString())
                 .doOnSubscribe { mView.showLoading() }
                 .compose(mView.bindToLife())
                 .subscribe(object :Observer<BookHelpList>{
                     override fun onNext(t: BookHelpList) {
                         val helpsBooks = t.helps
                         mView.loadCommunityList(helpsBooks!!)
                         addStart = addStart+helpsBooks.size
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

                     override fun onComplete() {
                         mView.hideLoading()
                     }
                 })
    }

    override fun getMoreData() {
        RetrofitService.getBookHelpListInfo((ConsTantUtils.START_PAGE+addStart).toString(),ConsTantUtils.LIMIT_POSITION.toString())
                .subscribe(object :Observer<BookHelpList>{
                    override fun onNext(t: BookHelpList) {
                        val helpsBooks = t.helps
                        mView.loadMoreCommunity(helpsBooks!!)
                        addStart = addStart+helpsBooks.size
                    }

                    override fun onError(e: Throwable) {
                        mView.loadNoData()
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onComplete() {

                    }
                })
    }
}