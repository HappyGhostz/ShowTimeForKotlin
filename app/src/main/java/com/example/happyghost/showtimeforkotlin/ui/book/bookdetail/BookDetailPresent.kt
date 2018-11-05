package com.example.happyghost.showtimeforkotlin.ui.book.bookdetail

import com.example.happyghost.showtimeforkotlin.bean.bookdate.BookDetail
import com.example.happyghost.showtimeforkotlin.bean.bookdate.HotReview
import com.example.happyghost.showtimeforkotlin.bean.bookdate.Recommend
import com.example.happyghost.showtimeforkotlin.bean.bookdate.RecommendBookList
import com.example.happyghost.showtimeforkotlin.loacaldao.BookChapterInfoDao
import com.example.happyghost.showtimeforkotlin.loacaldao.LocalBookInfoDao
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.BookTransformer
import com.example.happyghost.showtimeforkotlin.utils.ListUtils
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Zhao Chenping
 * @creat 2017/11/15.
 * @description
 */
class BookDetailPresent(view: BookDetailInfoActivity, bookid: String, localBookInfoDao: LocalBookInfoDao, bookChapterInfoDao: BookChapterInfoDao) :IBasePresenter {
    var mView:IBookDetailBaseView = view
    var mBookId = bookid
    var mBookDao = localBookInfoDao
    var mChaptersDao = bookChapterInfoDao
    override fun getData() {
        RetrofitService.getBookDetailInfo(mBookId)
                .doOnSubscribe { mView.showLoading() }
                .compose(mView.bindToLife())
                .subscribe(object :Observer<BookDetail>{
                    override fun onError(e: Throwable) {
                        mView.showNetError(object :EmptyErrLayout.OnReTryListener{
                            override fun onReTry() {
                                getData()
                            }
                        })
                    }

                    override fun onNext(t: BookDetail) {
                        mView.loadBookDetail(t)
                    }

                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onSubscribe(d: Disposable) {
                    }
                })
        RetrofitService.getHotReview(mBookId)
                .compose(mView.bindToLife())
                .subscribe(object :Observer<HotReview>{
                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                        mView.showNetError(object :EmptyErrLayout.OnReTryListener{
                            override fun onReTry() {
                                getData()
                            }
                        })
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: HotReview) {
                        val reviews = t.reviews
                        if(reviews!=null){
                            mView.loadHotReview(reviews)
                        }
                    }
                })
        RetrofitService.getRecommendBookList(mBookId,"3")
                .compose(mView.bindToLife())
                .subscribe(object :Observer<RecommendBookList>{
                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                        mView.showNetError(object :EmptyErrLayout.OnReTryListener{
                            override fun onReTry() {
                                getData()
                            }
                        })
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: RecommendBookList) {
                        val booklists = t.booklists
                        if(booklists!=null){
                            mView.loadRecommendBookList(booklists)
                        }
                    }
                })
    }

    override fun getMoreData() {

    }
    fun queryBook(bookid: String): Boolean {
        val list = mBookDao.queryBuilder()
                .where(LocalBookInfoDao.Properties.Url.eq(bookid))
                .build()
                .list()
        if(list.size>0){
            return true
        }
        return false
    }
    /**
     * 插入
     * 不想该数据结构，我在这转了一层，可以改成新闻那个数据模式
     * 从而增加表
     *
     */
    fun insertBook(recommendBooks: Recommend.RecommendBooks){
        val allBooks = mBookDao.queryBuilder().list()
//        val size = allBooks.size
//        var id:Long=1
        val localBookInfo = BookTransformer.RecommendBooksConvertlocalBook(recommendBooks)
//        var laocId = (size+id).toLong()
//        localBookInfo.id = laocId
//        localBookInfo.isFromSD=true
        mBookDao.insert(localBookInfo)
    }
    /**
     * 删除
     */
    fun deleteBook(recommendBooks: Recommend.RecommendBooks) {
        val localBookInfo = BookTransformer.RecommendBooksConvertlocalBook(recommendBooks)
        val localBookInfos = mBookDao.queryBuilder().where(LocalBookInfoDao.Properties.Url.eq(localBookInfo.url)).build().list()
        val bookInfo = localBookInfos[0]
        mBookDao.deleteInTx(bookInfo)
        val build = mChaptersDao.queryBuilder().where(BookChapterInfoDao.Properties.BookId.eq(recommendBooks._id)).build()
        val list = build.list()
        if (list.size > 0 && !ListUtils.isEmpty(list)) {
            mChaptersDao.deleteInTx(list)
        }
    }

}