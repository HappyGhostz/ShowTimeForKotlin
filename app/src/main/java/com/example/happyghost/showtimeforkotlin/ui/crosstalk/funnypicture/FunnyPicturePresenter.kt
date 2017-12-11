package com.example.happyghost.showtimeforkotlin.ui.crosstalk.funnypicture

import com.example.happyghost.showtimeforkotlin.bean.crossdate.FunnyPictureDate
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.utils.SharedPreferencesUtil
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Zhao Chenping
 * @creat 2017/12/9.
 * @description
 */
class FunnyPicturePresenter(view: FunnyPictureFragment) :IBasePresenter {
    var mView = view
    override fun getData() {
        var locTime = SharedPreferencesUtil.getInt(ConsTantUtils.CURRENT_MILL_TIME_FUNNY, 0)
        var currentTimeMillis = System.currentTimeMillis().toInt()
        SharedPreferencesUtil.putInt(ConsTantUtils.CURRENT_MILL_TIME_FUNNY,currentTimeMillis)
        RetrofitService.getFunnyPicture(currentTimeMillis,locTime,ConsTantUtils.CROSS_PICTURE,60)
                .doOnSubscribe { mView.showLoading() }
                .compose(mView.bindToLife())
                .subscribe(object :Observer<FunnyPictureDate>{
                    override fun onNext(t: FunnyPictureDate) {
                        mView.loadFunnyPictureDate(t)
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

    }
}