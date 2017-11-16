package com.example.happyghost.showtimeforkotlin.ui.book.rack

import android.util.Log
import com.example.happyghost.showtimeforkotlin.RxBus.RxBus
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookMixATocBean
import com.example.happyghost.showtimeforkotlin.bean.bookdata.Recommend
import com.example.happyghost.showtimeforkotlin.loacaldao.BookChapterInfo
import com.example.happyghost.showtimeforkotlin.loacaldao.BookChapterInfoDao
import com.example.happyghost.showtimeforkotlin.loacaldao.LocalBookInfo
import com.example.happyghost.showtimeforkotlin.loacaldao.LocalBookInfoDao
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.BookTransformer
import com.example.happyghost.showtimeforkotlin.utils.ListUtils
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

/**
 * @author Zhao Chenping
 * @creat 2017/9/15.
 * @description
 */
class BookRackPresent(view: IBookRackView, localBookInfoDao: LocalBookInfoDao, rxBus: RxBus, bookChapterInfoDao: BookChapterInfoDao) :IBasePresenter {
    var mView = view
    var mBookDao = localBookInfoDao
    var mRxBus = rxBus
    var mBookChapterDao = bookChapterInfoDao
    override fun getData() {
        val mLocalBooks = mBookDao.queryBuilder().list()
        if(!ListUtils.isEmpty(mLocalBooks)){
            mView.hideLoading()
            mView.loadLocalBookList(mLocalBooks)
        }else{
            RetrofitService.getBookRack("male")
                    .doOnSubscribe {
                        mView.showLoading()
                    }
                    .compose(mView.bindToLife())
                    .subscribe(object :Observer<Recommend>{
                        override fun onComplete() {
                            mView.hideLoading()
                        }

                        override fun onSubscribe(d: Disposable) {

                        }

                        override fun onNext(t: Recommend) {
                            val books = t.books
                            mView.loadRecommendList(books!!)
                        }

                        override fun onError(e: Throwable) {
                            mView.showNetError(object :EmptyErrLayout.OnReTryListener{
                                override fun onReTry() {
                                    getData()
                                }
                            })
                        }
                    })
        }

    }

    override fun getMoreData() {

    }

    /**
     * 插入
     * 不想该数据结构，我在这转了一层，可以改成新闻那个数据模式
     * 从而增加表
     *
     */
    fun insertBook(recommendBooks: Recommend.RecommendBooks){
        val allBooks = mBookDao.queryBuilder().list()
        val size = allBooks.size
        var id:Long=1
        val localBookInfo = BookTransformer.RecommendBooksConvertlocalBook(recommendBooks)
        localBookInfo.id = size+id
        localBookInfo.isFromSD=true
        mBookDao.insert(localBookInfo)
    }
    fun insertBooks(recBooks:List<Recommend.RecommendBooks>){
        val allBooks = mBookDao.queryBuilder().list()
        val size = allBooks.size
        var index = 1
        while (index <= recBooks.size-1)
        {
            var  book = recBooks.get(index)
            val localBookInfo = BookTransformer.RecommendBooksConvertlocalBook(book)
            localBookInfo.id=size+index.toLong()
            localBookInfo.isFromSD=true
            mBookDao.insert(localBookInfo)
            index++

        }

    }

    /**
     * 删除
     */
    fun deleteBook(recommendBooks: Recommend.RecommendBooks){
        val localBookInfo = BookTransformer.RecommendBooksConvertlocalBook(recommendBooks)
        mBookDao.delete(localBookInfo)
//        mBookDao.deleteAll()
    }
    /**
     * 同时删除所有章节
     */
    fun deleteBookChapters(bookId :String){
        val build = mBookChapterDao.queryBuilder().where(BookChapterInfoDao.Properties.BookId.eq(bookId)).build()
        val list = build.list()
        mBookChapterDao.deleteInTx(list)
    }

    /**
     * 置顶
     */
    fun isTop(recommendBooks: Recommend.RecommendBooks){
        val localBookInfo = BookTransformer.RecommendBooksConvertlocalBook(recommendBooks)
        mBookDao.delete(localBookInfo)
        val allBooks = mBookDao.queryBuilder().list()
        var trans = ArrayList<LocalBookInfo>()
        trans.addAll(allBooks)
        trans.add(0,localBookInfo)
        mBookDao.deleteAll()
        var index = 0
        while (index <= trans.size-1)
        {
            var  book = trans.get(index)
            book.id=index.toLong()
            if(index==0){
                book.isTop=true
            }else{
                book.isTop=false
            }
            mBookDao.insert(book)
            index++

        }
    }
    /**
     * 查询所有数据
     */
    fun queryAll():List<LocalBookInfo>{
        return mBookDao.queryBuilder().list()
    }
    /**
     * 查询所有章节数据
     */
    fun queryChapterAll(bookId: String): ArrayList<BookMixATocBean.MixTocBean.ChaptersBean> {
        val build = mBookChapterDao.queryBuilder().where(BookChapterInfoDao.Properties.BookId.eq(bookId)).build()
        val list = build.list()
        val chaptersBean = BookTransformer.locaBookChaptersConvertChaptersBean(list)
        return chaptersBean
    }
    /**
     * 替换
     */
    fun replace(recommendBooks: Recommend.RecommendBooks){
        val localBookInfo = BookTransformer.RecommendBooksConvertlocalBook(recommendBooks)
        localBookInfo.isFromSD = true
        mBookDao.insertOrReplace(localBookInfo)
    }
    fun <T> registerRxBus(eventType: Class<T>, action: Consumer<T>) {
        val disposable = mRxBus.doSubscribe(eventType, action, Consumer<Throwable> {
            throwable -> Log.e("NewsMainPresenter", throwable.toString()) })
        mRxBus.addSubscription(this, disposable)
    }

   fun unregisterRxBus() {
        mRxBus.unSubscribe(this)
    }
}