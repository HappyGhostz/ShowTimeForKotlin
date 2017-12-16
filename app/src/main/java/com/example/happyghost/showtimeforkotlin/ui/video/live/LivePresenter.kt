package com.example.happyghost.showtimeforkotlin.ui.video.live

import com.example.happyghost.showtimeforkotlin.bean.videodata.LiveListBean
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Zhao Chenping
 * @creat 2017/12/15.
 * @description
 */
class LivePresenter(view: LiveFragment) :IBasePresenter {
    var mView = view
    var offSet =0
    override fun getData() {

    }
    fun getLiveDate(gameType:String,liveType:String){
        offSet = 0
        RetrofitService.getLiveListDate(offSet,ConsTantUtils.LIMIT_POSITION,liveType,gameType)
                .doOnSubscribe { mView.showLoading() }
                .compose(mView.bindToLife())
                .subscribe(object :Observer<LiveListBean>{
                    override fun onNext(t: LiveListBean) {
                        mView.loadLiveDate(t)
                        offSet+=t.result!!.size
                    }

                    override fun onError(e: Throwable) {
                        mView.showNetError(object :EmptyErrLayout.OnReTryListener{
                            override fun onReTry() {
                                getLiveDate(gameType,liveType)
                            }
                        })
                    }

                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onSubscribe(d: Disposable) {
                    }
                })
    }

    override fun getMoreData() {

    }
}