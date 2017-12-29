package com.example.happyghost.showtimeforkotlin.ui.video.kankan

import com.example.happyghost.showtimeforkotlin.bean.videodata.VideoListDate
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.support.v4.toast

/**
 * @author Zhao Chenping
 * @creat 2017/12/25.
 * @description
 */
class VideoListPresenter(view: VideoListFragment) :IBasePresenter {
    var mCategoryId :String?=null
    var headersMap:HashMap<String,String> = HashMap<String, String>()
    var page=1
    var start=0
    var mView =view
    init {
        headersMap.put("X-Channel-Code","official")
        headersMap.put("X-Client-Agent","Xiaomi")
        headersMap.put("X-Client-Hash","2f3d6ffkda95dlz2fhju8d3s6dfges3t")
        headersMap.put("X-Client-ID","123456789123456")
        headersMap.put("X-Client-Version","2.3.2")
        headersMap.put("X-Long-Token","")
        headersMap.put("X-Platform-Type","0")
        headersMap.put("X-Platform-Version","5.0")
        headersMap.put("X-User-ID","")
    }
    override fun getData() {
        var time = System.currentTimeMillis()/1000
        headersMap.put("X-Serial-Num",time.toString())
        var lastLikeIds ="1063871%2C1063985%2C1064069%2C1064123%2C1064078%2C1064186%2C1062372%2C1064164%2C1064081%2C1064176%2C1064070%2C1064019"
        RetrofitService.getHotFristVideoDate(headersMap,lastLikeIds)
                .doOnSubscribe { mView.showLoading() }
                .compose(mView.bindToLife())
                .subscribe(object : Observer<VideoListDate>{
                    override fun onError(e: Throwable) {
                        mView.showNetError(object :EmptyErrLayout.OnReTryListener{
                            override fun onReTry() {
                                getData()
                            }
                        })
                    }

                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: VideoListDate) {
                        mView.loadVideoListDate(t)
                    }

                })
    }

    override fun getMoreData() {
        if(mCategoryId!=null){
            var time = System.currentTimeMillis()/1000
            headersMap.put("X-Serial-Num",time.toString())
            RetrofitService.getKankanVideoFromCate(headersMap,page,start, mCategoryId!!)
                    .compose(mView.bindToLife())
                    .subscribe(object :Observer<VideoListDate>{
                        override fun onComplete() {
                        }

                        override fun onSubscribe(d: Disposable) {
                        }

                        override fun onNext(t: VideoListDate) {
                            mView.loadMoreVideoForCategoryidDate(t)
                            start+=t.contList?.size!!
                        }

                        override fun onError(e: Throwable) {
                           mView.toast("人家到底线了啊!!!")
                        }
                    })
        }
    }
    fun getCategoryDate(categoryId: String) {
        mCategoryId = categoryId
        var time = System.currentTimeMillis()/1000
        headersMap.put("X-Serial-Num",time.toString())
        start = 0
        RetrofitService.getKankanVideoFromCate(headersMap,page,start,categoryId)
                .doOnSubscribe { mView.showLoading() }
                .compose(mView.bindToLife())
                .subscribe(object :Observer<VideoListDate>{
                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: VideoListDate) {
                        mView.loadVideoForCategoryidDate(t)
                        page++
                        start+=t.contList?.size!!
                    }

                    override fun onError(e: Throwable) {
                        mView.showNetError(object :EmptyErrLayout.OnReTryListener{
                            override fun onReTry() {
                                getCategoryDate(categoryId)
                            }
                        })
                    }
                })
    }
}