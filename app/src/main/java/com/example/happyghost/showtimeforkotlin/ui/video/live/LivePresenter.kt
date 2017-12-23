package com.example.happyghost.showtimeforkotlin.ui.video.live

import android.text.TextUtils
import android.util.Log
import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.bean.videodata.DouyuVideoInfo
import com.example.happyghost.showtimeforkotlin.bean.videodata.LiveDetailBean
import com.example.happyghost.showtimeforkotlin.bean.videodata.LiveListBean
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.Md5Utils
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import org.jetbrains.anko.support.v4.toast

/**
 * @author Zhao Chenping
 * @creat 2017/12/15.
 * @description
 */
class LivePresenter(view: LiveFragment) :IBasePresenter {
    var mView = view
    var offSet =0
    lateinit var mGameType:String
    lateinit var mLiveType:String
    override fun getData() {

    }
    fun getLiveDate(gameType:String,liveType:String){
        mGameType = gameType
        mLiveType = liveType
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
    fun getDoyuLivDate(liveType: String, gameType: String) {
        mGameType = gameType
        mLiveType = liveType
        offSet=0
        if (TextUtils.equals(mGameType, "live")) {
            mGameType = ""
        }
        if (TextUtils.equals(mGameType, "hs")) {
            mGameType = "how"
        }
        if (TextUtils.equals(mGameType, "ow")) {
            mGameType = "overwatch"
        }
        //limit不要超过30,否则会报404
        RetrofitService.getDouyuLiveList(offSet,ConsTantUtils.LIMIT_POSITION,mGameType)
                .doOnSubscribe { mView.showLoading() }
                .compose(mView.bindToLife())
                .subscribe(object :Observer<LiveListBean>{
                    override fun onError(e: Throwable) {
                        mView.showNetError(object :EmptyErrLayout.OnReTryListener{
                            override fun onReTry() {
                                getDoyuLivDate(gameType,liveType)
                            }
                        })
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onNext(t: LiveListBean) {
                        mView.loadLiveDate(t)
                        offSet+=t.data!!.size
                    }
                })
    }

    override fun getMoreData() {
        if(!TextUtils.equals(mLiveType,"douyu")){
            RetrofitService.getLiveListDate(offSet,ConsTantUtils.LIMIT_POSITION,mLiveType,mGameType)
                    .compose(mView.bindToLife())
                    .subscribe(object :Observer<LiveListBean>{
                        override fun onNext(t: LiveListBean) {
                            mView.loadMoreLiveDate(t)
                            offSet+=t.result!!.size
                        }

                        override fun onError(e: Throwable) {
                            mView.toast("已无资源!")
                        }

                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable) {
                        }
                    })
        }else{
            RetrofitService.getDouyuLiveList(offSet,ConsTantUtils.LIMIT_POSITION,mGameType)
                    .compose(mView.bindToLife())
                    .subscribe(object :Observer<LiveListBean>{
                        override fun onError(e: Throwable) {
                            mView.toast("已无资源!")
                        }

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onComplete() {
                        }

                        override fun onNext(t: LiveListBean) {
                            mView.loadMoreLiveDate(t)
                            offSet+=t.data!!.size
                        }
                    })
        }
    }
    fun getVideoPlayDetail(game_type: String?, live_type: String?, live_id: String?) {
        RetrofitService.getLiveDetail(live_type, live_id, game_type)
                .compose(mView.bindToLife())
                .subscribe(object :Observer<LiveDetailBean>{
                    override fun onNext(t: LiveDetailBean) {
                        mView.openVideoPlay(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        mView.toast("程序出错,请稍后再试!")
                    }

                    override fun onComplete() {
                    }
                })

    }
//    int time = (int) (System.currentTimeMillis()/1000);
//    String signContent = "lapi/live/thirdPart/getPlay/" + roomId + "?aid=pcclient&rate=0&time="
//    + time + "9TUk5fjjUjg9qIMH3sdnh";
//    String sign = Md5.strToMd5Low32(signContent);
//
//    HashMap<String, String> map = new HashMap<>();
//    map.put("auth", sign);
//    map.put("time", "" + time);
//    map.put("aid", "pcclient");
    fun getDouyuVideo(roomId:String){
        /**
         * 房间加密处理
         */
        val time = (System.currentTimeMillis() / 1000).toInt()
        val str = "lapi/live/thirdPart/getPlay/" + roomId + "?aid=pcclient&rate=0&time=" + time + "9TUk5fjjUjg9qIMH3sdnh"
        val auth = Md5Utils.getToMd5Low32(str)
        var headMap = HashMap<String, String>()
        headMap.put("aid","pcclient")
        headMap.put("auth",auth)
        headMap.put("time", time.toString())
        RetrofitService.getDouyuVideo(roomId)
                .compose(mView.bindToLife())
                .subscribe(object :Observer<DouyuVideoInfo>{
                    override fun onNext(t: DouyuVideoInfo) {
                        mView.openDouyuVideoPlay(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        mView.toast("程序出错,请稍后再试!")
                    }

                    override fun onComplete() {
                    }
                })
    }
    fun <T> registerRxBus(eventType: Class<T>, action: Consumer<T>) {
        val disposable = AppApplication.instance.mRxBus?.doSubscribe(eventType, action, Consumer {
            throwable -> Log.e("NewsMainPresenter", throwable.toString()) })
        AppApplication.instance.mRxBus?.addSubscription(this, disposable!!)
    }

    fun unregisterRxBus() {
        AppApplication.instance.mRxBus?.unSubscribe(this)
    }
}