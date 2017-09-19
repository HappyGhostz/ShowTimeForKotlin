package com.example.happyghost.showtimeforkotlin.ui.news.special

import com.example.happyghost.showtimeforkotlin.bean.newsdata.NewsItemInfo
import com.example.happyghost.showtimeforkotlin.bean.newsdata.SpecialInfo
import com.example.happyghost.showtimeforkotlin.bean.newsdata.SpecialItem
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

/**
 * @author Zhao Chenping
 * @creat 2017/9/9.
 * @description
 */
class NewsSpecialPresenter(view: NewsSpecialActivity, id: String) :IBasePresenter {
    var mView :INewsSpecialView = view
    var mId :String = id
    override fun getData() {
        RetrofitService.getNewsSpeciaList(mId)
                .doOnSubscribe { mView.showLoading() }
                .flatMap { special: SpecialInfo ->
                    mView.loadBanner(special)
                    return@flatMap convertSpecialInfo(special)
                }
                .toList()
                .compose(mView.bindToLife())
                .subscribe(object :SingleObserver<List<SpecialItem>>{
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onError(e: Throwable) {
                        mView.showNetError(object :EmptyErrLayout.OnReTryListener{
                            override fun onReTry() {
                                getData()
                            }
                        })
                    }

                    override fun onSuccess(t: List<SpecialItem>) {
                        mView.hideLoading()
                        mView.loadData(t)
                    }

                })
    }

    override fun getMoreData() {
    }
    /**
     * 转换数据，接口数据有点乱，这里做一些处理
     * @param specialBean
     * @return
     */
    fun convertSpecialInfo(specialInfo: SpecialInfo):Observable<SpecialItem>{
        // 这边 +1 是接口数据还有个 topicsplus 的字段可能是穿插在 topics 字段列表中间。这里没有处理 topicsplus
        val specialItems = arrayOfNulls<SpecialItem>(specialInfo.getTopics()!!.size + 1)
        return Observable.fromIterable(specialInfo.getTopics())
                .doOnNext {
                    specialItems[it.index - 1] = SpecialItem(true,
                            it.index.toString() + "/" + specialItems.size + " " + it.tname)
                }
                //排序
                .toSortedList { topicsEntity1, topicsEntity2 ->
                    return@toSortedList topicsEntity1.index-topicsEntity2.index
                }
                //转换成Observable，以方便下面的使用
                .toObservable()
                //拆分
                .flatMap {
                    return@flatMap Observable.fromIterable(it)
                }
                //转换并在每个列表添加头部
                .flatMap{
                    top->
                    return@flatMap Observable.fromIterable(top.docs)
                            .map {
                                newsItemInfo: NewsItemInfo ->
                                return@map SpecialItem(newsItemInfo)
                            }
                            .startWith(specialItems[top.index-1])
                }
    }
}