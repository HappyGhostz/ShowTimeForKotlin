package com.example.happyghost.showtimeforkotlin.ui.crosstalk.crossfragment

import com.example.happyghost.showtimeforkotlin.bean.CrossTalkDate
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.utils.SharedPreferencesUtil
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Zhao Chenping
 * @creat 2017/12/8.
 * @description
 */
class CrossTalkPresenter(view: CrossTalkFragment) :IBasePresenter {
    var mView = view
    override fun getData() {
        var locTime = SharedPreferencesUtil.getInt(ConsTantUtils.CURRENT_MILL_TIME, 0)
        var currentTimeMillis = System.currentTimeMillis().toInt()
        SharedPreferencesUtil.putInt(ConsTantUtils.CURRENT_MILL_TIME,currentTimeMillis)
        RetrofitService.getCrossTalk(currentTimeMillis,locTime,ConsTantUtils.CROSS_TYPE,60)
                .doOnSubscribe { mView.showLoading() }
                .compose(mView.bindToLife())
                .subscribe (object :Observer<CrossTalkDate>{
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

                    override fun onNext(t: CrossTalkDate) {
                        mView.showCrossTalk(t)
                    }
                })

    }

    override fun getMoreData() {

    }
}