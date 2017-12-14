package com.example.happyghost.showtimeforkotlin.ui.picture.beautypicture

import com.example.happyghost.showtimeforkotlin.bean.picturedate.BeautyPicture
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.support.v4.toast

/**
 * @author Zhao Chenping
 * @creat 2017/12/14.
 * @description
 */
class BeautyPicturePresenter(view: BeautyPictureFragment) :IBasePresenter {
    var startImage = 0
    var imageSize = 30
    var mView = view
    override fun getData() {
        startImage = 0
        RetrofitService.getBeautyPicture(startImage,imageSize,"美女","全部")
                .doOnSubscribe { mView.showLoading() }
                .compose(mView.bindToLife())
                .subscribe(object :Observer<BeautyPicture>{
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

                    override fun onNext(t: BeautyPicture) {
                        mView.loadBeautyData(t)
                        startImage+=t.imgs?.size!!
                    }

                    override fun onSubscribe(d: Disposable) {
                    }
                })
    }

    override fun getMoreData() {
        RetrofitService.getBeautyPicture(startImage,imageSize,"美女","全部")
                .compose(mView.bindToLife())
                .subscribe(object :Observer<BeautyPicture>{
                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                        mView.toast("到底线了,骚年!")
                    }

                    override fun onNext(t: BeautyPicture) {
                        mView.loadMoreBeautyData(t)
                        startImage+=t.imgs?.size!!
                    }

                    override fun onSubscribe(d: Disposable) {
                    }
                })
    }
}