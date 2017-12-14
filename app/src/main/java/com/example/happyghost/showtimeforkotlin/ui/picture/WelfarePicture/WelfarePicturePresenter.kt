package com.example.happyghost.showtimeforkotlin.ui.picture.WelfarePicture

import com.example.happyghost.showtimeforkotlin.bean.picturedate.WelfarePhotoList
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.support.v4.toast

/**
 * @author Zhao Chenping
 * @creat 2017/12/12.
 * @description
 */
class WelfarePicturePresenter(view: WelfarePictureFragment) :IBasePresenter {
    var mView = view
    var mPage = 1
    override fun getData() {
        mPage = 1
        RetrofitService.getWelfarePhoto(mPage)
                .doOnSubscribe { mView.showLoading() }
                .compose(mView.bindToLife())
                .subscribe(object :Observer<WelfarePhotoList>{
                    override fun onNext(t: WelfarePhotoList) {
                        mView.showWelfDate(t)
                        mPage++
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

                    override fun onSubscribe(d: Disposable) {
                    }
                })
    }

    override fun getMoreData() {
        RetrofitService.getWelfarePhoto(mPage)
                .compose(mView.bindToLife())
                .subscribe(object :Observer<WelfarePhotoList>{
                    override fun onNext(t: WelfarePhotoList) {
                        mView.showMoreWelfDate(t)
                        mPage++
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                        mView.toast("底线到了,骚年!")
                    }

                    override fun onSubscribe(d: Disposable) {
                    }
                })
    }
}