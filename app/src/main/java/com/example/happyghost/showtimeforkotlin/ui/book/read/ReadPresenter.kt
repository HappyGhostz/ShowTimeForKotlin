package com.example.happyghost.showtimeforkotlin.ui.book.read

import android.util.Log
import com.example.happyghost.showtimeforkotlin.RxBus.RxBus
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookMixATocBean
import com.example.happyghost.showtimeforkotlin.ui.base.IRxBusPresenter
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

/**
 * @author Zhao Chenping
 * @creat 2017/9/28.
 * @description
 */
class ReadPresenter(view: ReadActivity, rxBus: RxBus) : IRxBusPresenter {
    var mView:IReadView =view
    var mRxBus = rxBus
    override fun getData() {

    }

    override fun getMoreData() {

    }

    fun getBookMixAToc(bookId: String, view: String){
        RetrofitService.getBookMixATocInfo(bookId,view)
                .compose(mView.bindToLife())
                .subscribe(object :Observer<BookMixATocBean>{
                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                        getBookMixAToc(bookId, view)
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: BookMixATocBean) {
                        val chapters = t.getMixToc()?.chapters
                        if (chapters != null) {
                            mView.loadBookToc(chapters)
                        }
                    }

                })
    }

    fun getChapterRead(url: String, chapter: Int){
        RetrofitService.getChapterBody(url)
                .compose(mView.bindToLife())
                .subscribe {
                    mView.loadChapterRead(it.chapter, chapter)
                }
    }
    override fun <T> registerRxBus(eventType: Class<T>, action: Consumer<T>) {
        val disposable = mRxBus.doSubscribe(eventType, action, Consumer<Throwable> {
            throwable -> Log.e("NewsMainPresenter", throwable.toString()) })
        mRxBus.addSubscription(this, disposable)
    }

    override fun unregisterRxBus() {
        mRxBus.unSubscribe(this)
    }
}