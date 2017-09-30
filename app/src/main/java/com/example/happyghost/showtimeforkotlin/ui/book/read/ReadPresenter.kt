package com.example.happyghost.showtimeforkotlin.ui.book.read

import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookMixATocBean
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.ListUtils
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService

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
                .subscribe {
                    val chapters = it.getMixToc()?.chapters
                    if(chapters!=null&&ListUtils.isEmpty(chapters)&&mView!=null){
                        mView.loadBookToc(chapters)
                    }
                }
    }

    fun getChapterRead(url: String, chapter: Int){}
}