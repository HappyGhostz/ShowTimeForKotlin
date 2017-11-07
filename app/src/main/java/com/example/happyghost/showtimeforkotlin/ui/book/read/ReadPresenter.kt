package com.example.happyghost.showtimeforkotlin.ui.book.read

import android.text.TextUtils
import android.util.Log
import com.example.happyghost.showtimeforkotlin.RxBus.RxBus
import com.example.happyghost.showtimeforkotlin.RxBus.event.ReadEvent
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookMixATocBean
import com.example.happyghost.showtimeforkotlin.loacaldao.LocalBookInfoDao
import com.example.happyghost.showtimeforkotlin.ui.base.IRxBusPresenter
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * @author Zhao Chenping
 * @creat 2017/9/28.
 * @description
 */
class ReadPresenter(view: ReadActivity, rxBus: RxBus, localBookInfoDao: LocalBookInfoDao) : IRxBusPresenter {
    var mView:IReadView =view
    var mRxBus = rxBus
    var mBookDao = localBookInfoDao
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
    fun queryBook(bookId:String):Boolean{
        val list = mBookDao.queryBuilder().build().list()
        var index = 0
        while (index<list.size){
            val bookInfo = list[index]
            return TextUtils.equals(bookInfo.url,bookId)
        }
       return false
    }
}