package com.example.happyghost.showtimeforkotlin.ui.news.normal

import com.example.happyghost.showtimeforkotlin.bean.NewsDetailInfo
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.ListUtils
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Zhao Chenping
 * @creat 2017/9/12.
 * @description
 */
class NewsNormalPresenter(baseView: INewsNormalView, id: String) :IBasePresenter {
    var mView:INewsNormalView
    var detialId:String
    private val HTML_IMG_TEMPLATE = "<img src=\"http\" />"
    init {
        mView = baseView
        detialId=id
    }
    override fun getData() {
        RetrofitService.getNewsDetial(detialId)
                .doOnSubscribe {
                    mView.showLoading()
                }
                .doOnNext {
                    handleRichTextWithImage(it)
                }
                .compose(mView.bindToLife())
                .subscribe(object :Observer<NewsDetailInfo>{
                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onError(e: Throwable) {
                        mView.showNetError(object :EmptyErrLayout.OnReTryListener{
                            override fun onReTry() {
                                getData()
                            }
                        })
                    }

                    override fun onNext(t: NewsDetailInfo) {
                        mView.loadData(t)
                    }
                })

    }

    override fun getMoreData() {

    }
    fun handleRichTextWithImage(it: NewsDetailInfo) {
        if(!ListUtils.isEmpty(it.getImg())){
            var body = it.getBody()
            for (imgEmenty in it.getImg()!!){
                val ref = imgEmenty.ref
                val src = imgEmenty.src
                val img = HTML_IMG_TEMPLATE.replace("http", src!!,false)
                body = body?.replace(ref!!, img,false)
            }
            it.setBody(body!!)
        }
    }
}