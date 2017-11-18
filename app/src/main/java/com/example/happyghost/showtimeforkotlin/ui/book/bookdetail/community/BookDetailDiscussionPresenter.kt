package com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.community

import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.bean.bookdata.DiscussionList
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
class BookDetailDiscussionPresenter(view: BookDetailDiscussionFragment, bookid: String) :IBasePresenter {
    var mView:IBaseDetialDiscussionView = view
    var mBoodId = bookid
    protected var start = 0
    protected var limit = 20
    override fun getData() {
        start=0
        RetrofitService.getBookDetailDisscussionList(mBoodId, "updated", "normal,vote", start .toString(), (start+limit) .toString())
                .doOnSubscribe { mView.showLoading() }
                .compose(mView.bindToLife())
                .subscribe(object :Observer<DiscussionList>{
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: DiscussionList) {
                        if(t.posts!=null){
                            mView.showBookDetailDiscussionList(t.posts!!)
                            start+=t.posts!!.size
                        }
                    }

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
                })
    }

    override fun getMoreData() {
        RetrofitService.getBookDetailDisscussionList(mBoodId, "updated", "normal,vote", start .toString(), (start+limit) .toString())
                .compose(mView.bindToLife())
                .subscribe(object :Observer<DiscussionList>{
                    override fun onSubscribe(d: Disposable) {
                    }
                    override fun onNext(t: DiscussionList) {
                        if(t.posts!=null){
                            mView.showBookDetailDiscussionList(t.posts!!)
                            start+=t.posts!!.size
                        }else if(t.posts!!.isEmpty()){
                            AppApplication.instance.getContext().toast("已无数据!")
                        }
                    }

                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {
                        AppApplication.instance.getContext().toast("已无数据!")
                    }
                })
    }
}