package com.example.happyghost.showtimeforkotlin.ui.news.photonews

import com.example.happyghost.showtimeforkotlin.bean.PhotoSetInfo
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Zhao Chenping
 * @creat 2017/9/13.
 * @description
 */
class PhotoSetPresenter(view: PhotoSetNewsActivity, id: String) :IBasePresenter {
    var mView:IPhotoSetView = view
    var key:String = id
    override fun getData() {
        RetrofitService.getPhotoSetNews(key)
                .doOnSubscribe { mView.showLoading() }
                .compose(mView.bindToLife())
                .subscribe(object :Observer<PhotoSetInfo>{
                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: PhotoSetInfo) {
                        mView.loadData(t)
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