package com.example.happyghost.showtimeforkotlin.ui.book.classify.classifydetail

import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.bean.bookdate.BooksByCats
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast

/**
 * @author Zhao Chenping
 * @creat 2017/11/15.
 * @description
 */
class ClassifyDetailPresenter(view: ICategoryBaseView, gender: String, name: String, type: String) :IBasePresenter{
    var mView:ICategoryBaseView = view
    var mGender = gender
    var mName = name
    var mType = type
    var addStart = 0
    override fun getData() {
        addStart=0
        RetrofitService.getBooksByCatsInfo(mGender, mType, mName, "", ConsTantUtils.START_PAGE+addStart,ConsTantUtils.LIMIT_POSITION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    mView.showLoading()
                }
                .compose(mView.bindToLife())
                .subscribe(object :Observer<BooksByCats>{
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

                    override fun onNext(t: BooksByCats) {
                        if(t!=null){
                            mView.loadCategoryList(t)
                            addStart += t.books!!.size
                        }
                    }
                })
    }

    override fun getMoreData() {
        RetrofitService.getBooksByCatsInfo(mGender, mType, mName, "", ConsTantUtils.START_PAGE+addStart,ConsTantUtils.LIMIT_POSITION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mView.bindToLife())
                .subscribe(object :Observer<BooksByCats>{
                    override fun onError(e: Throwable) {
                       AppApplication.instance.getContext().toast("没资源了!")
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onComplete() {
                    }

                    override fun onNext(t: BooksByCats) {
                        mView.loadMoreCategoryList(t)
                        addStart += t.books!!.size
                    }
                })
    }
}