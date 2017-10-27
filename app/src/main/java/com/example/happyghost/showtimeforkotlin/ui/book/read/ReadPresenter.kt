package com.example.happyghost.showtimeforkotlin.ui.book.read

import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookMixATocBean
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.ListUtils
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.toast

/**
 * @author Zhao Chenping
 * @creat 2017/9/28.
 * @description
 */
class ReadPresenter(view: ReadActivity) :IBasePresenter {
    var mView:IReadView =view
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
}