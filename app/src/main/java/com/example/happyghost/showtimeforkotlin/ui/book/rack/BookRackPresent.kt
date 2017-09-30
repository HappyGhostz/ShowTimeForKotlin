package com.example.happyghost.showtimeforkotlin.ui.book.rack

import com.example.happyghost.showtimeforkotlin.RxBus.RxBus
import com.example.happyghost.showtimeforkotlin.bean.bookdata.Recommend
import com.example.happyghost.showtimeforkotlin.loacaldao.LocalBookInfo
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
 * @creat 2017/9/15.
 * @description
 */
class BookRackPresent(view: IBookRackView, localBookInfoDao: LocalBookInfoDao, rxBus: RxBus) :IBasePresenter {
    var mView = view
    var mBookDao = localBookInfoDao
    var mRxBus = rxBus
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
        var id:Long=0
        val localBookInfo = BookTransformer.RecommendBooksConvertlocalBook(recommendBooks)
        localBookInfo.id = size+id
        mBookDao.insert(localBookInfo)
    }
    fun insertBooks(recBooks:List<Recommend.RecommendBooks>){
        val allBooks = mBookDao.queryBuilder().list()
        val size = allBooks.size
        var index = 0
        while (index <= recBooks.size-1)
        {
            var  book = recBooks.get(index)
            val localBookInfo = BookTransformer.RecommendBooksConvertlocalBook(book)
            localBookInfo.id=size+index.toLong()
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
     * 替换
     */
    fun replace(recommendBooks: Recommend.RecommendBooks){
        val localBookInfo = BookTransformer.RecommendBooksConvertlocalBook(recommendBooks)
        mBookDao.insertOrReplace(localBookInfo)
    }
}