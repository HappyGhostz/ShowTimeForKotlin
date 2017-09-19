package com.example.happyghost.showtimeforkotlin.ui.news.newslist

import android.util.Log
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.bean.newsdata.NewsInfo
import com.example.happyghost.showtimeforkotlin.bean.newsdata.NewsMultiItem
import com.example.happyghost.showtimeforkotlin.utils.ListUtils
import com.example.happyghost.showtimeforkotlin.utils.NewsUtils
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.*
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function


/**
 * @author Zhao Chenping
 * @creat 2017/9/5.
 * @description
 */
class NewsListPresenter() :IBasePresenter{

    lateinit var mView : INewsListView
    lateinit var mKeyId :String
    var mPage :Int = 0
  constructor(view: INewsListView, keyId: String) : this(){
      this.mView =view
      this.mKeyId = keyId
  }

    override fun getData() {
        RetrofitService.getNewsList(mKeyId,mPage)
                ?.doOnSubscribe { mView.showLoading() }
                ?.filter { t ->
                    if (NewsUtils.isAbNews(t)) {
                        mView.loadAdData(t)
                    }
                    !NewsUtils.isAbNews(t)
                }
                ?.map (Function<NewsInfo, NewsMultiItem> { t ->
                    //在这里不能在skipType使用'!!'符号，因为请求到的skipType字段可能为空，所以'?'，？,!!区别
                    if (NewsUtils.isNewsPhotoSet(t.skipType)){
                        return@Function NewsMultiItem(t, NewsMultiItem.NEWS_INFO_PHOTO_SET)
                    }
                    NewsMultiItem(t, NewsMultiItem.NEWS_INFO_NORMAL)
                })
                ?.toList()
                ?.compose(mView.bindToLife<List<NewsMultiItem>>())
                ?.subscribe(object :SingleObserver<List<NewsMultiItem>>{
                    override fun onError(e: Throwable) {
                        Log.e("NewsListPresenter",e.toString())
                        mView.showNetError(object :EmptyErrLayout.OnReTryListener{
                            override fun onReTry() {
                                getData()
                            }
                        })
                    }

                    override fun onSuccess(t: List<NewsMultiItem>) {
                        mView.hideLoading()
                        mView.loadData(t)
                        mPage++
                    }

                    override fun onSubscribe(d: Disposable) {

                    }
                } )


    }

    override fun getMoreData() {
        RetrofitService.getNewsList(mKeyId,mPage)
                ?.filter { t ->
                    if (NewsUtils.isAbNews(t)) {
                        mView.loadAdData(t)
                    }
                    !NewsUtils.isAbNews(t)
                }
                ?.map (Function<NewsInfo, NewsMultiItem> { t ->
                    //在这里不能在skipType使用'!!'符号，因为请求到的skipType字段可能为空，所以'?'，？,!!区别
                    if (NewsUtils.isNewsPhotoSet(t.skipType)){
                        return@Function NewsMultiItem(t, NewsMultiItem.NEWS_INFO_PHOTO_SET)
                    }
                    NewsMultiItem(t, NewsMultiItem.NEWS_INFO_NORMAL)
                })
                ?.toList()
                ?.compose(mView.bindToLife<List<NewsMultiItem>>())
                ?.subscribe(object :SingleObserver<List<NewsMultiItem>>{
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onError(e: Throwable) {
                        Log.e("NewsListPresenter",e.toString())
                    }

                    override fun onSuccess(t: List<NewsMultiItem>) {
                        if(ListUtils.isEmpty(t)){
                            mView.loadNoData()
                        }else{
                            mView.loadMoreData(t)
                            mPage++
                        }
                    }
                })

    }

}



