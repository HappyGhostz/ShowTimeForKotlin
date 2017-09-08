package com.example.happyghost.showtimeforkotlin.news.newslist

import android.util.Log
import com.example.happyghost.showtimeforkotlin.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.bean.NewsInfo
import com.example.happyghost.showtimeforkotlin.bean.NewsMultiItem
import com.example.happyghost.showtimeforkotlin.news.newlist.NewsListFragment
import com.example.happyghost.showtimeforkotlin.utils.ListUtils
import com.example.happyghost.showtimeforkotlin.utils.NewsUtils
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.*
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiConsumer
import java.util.concurrent.Callable
import javax.xml.transform.Transformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers



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
                ?.filter(object: Predicate<NewsInfo>{
                    override fun test(t: NewsInfo): Boolean {
                        if (NewsUtils.isAbNews(t)) {
                             mView.loadAdData(t)
                           }
                         return !NewsUtils.isAbNews(t)
                    }
                })
                ?.map (object :Function<NewsInfo,NewsMultiItem>{
                    override fun apply(t: NewsInfo): NewsMultiItem {
                        //在这里不能在skipType使用'!!'符号，因为请求到的skipType字段可能为空，所以'?'，？,!!区别
                        if (NewsUtils.isNewsPhotoSet(t.skipType)){
                            return NewsMultiItem(t,NewsMultiItem.NEWS_INFO_PHOTO_SET)
                        }
                        return NewsMultiItem(t,NewsMultiItem.NEWS_INFO_NORMAL)
                    }
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
                ?.filter(object: Predicate<NewsInfo>{
                    override fun test(t: NewsInfo): Boolean {
                        if (NewsUtils.isAbNews(t)) {
                            mView.loadAdData(t)
                        }
                        return !NewsUtils.isAbNews(t)
                    }
                })
                ?.map (object :Function<NewsInfo,NewsMultiItem>{
                    override fun apply(t: NewsInfo): NewsMultiItem {
                        //在这里不能在skipType使用'!!'符号，因为请求到的skipType字段可能为空，所以'?'，？,!!区别
                        if (NewsUtils.isNewsPhotoSet(t.skipType)){
                            return NewsMultiItem(t,NewsMultiItem.NEWS_INFO_PHOTO_SET)
                        }
                        return NewsMultiItem(t,NewsMultiItem.NEWS_INFO_NORMAL)
                    }
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



