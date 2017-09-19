package com.example.happyghost.showtimeforkotlin.ui.book.classify

import com.example.happyghost.showtimeforkotlin.bean.bookdata.CategoryList
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Zhao Chenping
 * @creat 2017/9/19.
 * @description
 */
class BookClassifyPresenter(view: BookClassifyListFragment) :IBasePresenter {
    var mView:IBookClassifyView = view
    override fun getData() {
        RetrofitService.getBookClassifyInfo()
                .doOnSubscribe { mView.showLoading() }
                .compose(mView.bindToLife())
                .subscribe(object :Observer<CategoryList>{
                    override fun onNext(t: CategoryList) {
                        val males = t.male
                        val female = t.female
                        mView.LoadFemaleCategoryList(female!!)
                        mView.LoadMaleCategoryList(males!!)
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

                    override fun onComplete() {
                       mView.hideLoading()
                    }
                })

    }

    override fun getMoreData() {

    }
}