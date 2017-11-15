package com.example.happyghost.showtimeforkotlin.ui.book.classify

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.BookFemaleClassifyAdapter
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.BookMaleClassifyAdapter
import com.example.happyghost.showtimeforkotlin.bean.bookdata.CategoryList
import com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent.DaggerBookClassifyListComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookClassifyListModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/9/15.
 * @description
 */
class BookClassifyListFragment: BaseFragment<BookClassifyPresenter>(),IBookClassifyView {
    @Inject lateinit var maleAdapter:BookMaleClassifyAdapter
    @Inject lateinit var femaleAdapter :BookFemaleClassifyAdapter
    override fun LoadMaleCategoryList(data: List<CategoryList.MaleBean>) {
        maleAdapter.replaceData(data)
    }

    override fun LoadFemaleCategoryList(data: List<CategoryList.MaleBean>) {
        femaleAdapter.replaceData(data)
    }


    override fun upDataView() {
       mPresenter.getData()
    }

    override fun initView(mRootView: View?) {
        val maleView = mRootView!!.find<RecyclerView>(R.id.rv_checked_list)
        val femaleView = mRootView!!.find<RecyclerView>(R.id.rv_unchecked_list)
        RecyclerViewHelper.initRecycleViewG(mContext,maleView,maleAdapter,3,true)
        RecyclerViewHelper.initRecycleViewG(mContext,femaleView,femaleAdapter,3,true)
    }

    override fun initInject() {
        DaggerBookClassifyListComponent.builder()
                .applicationComponent(getAppComponent())
                .bookClassifyListModule(BookClassifyListModule(this))
                .build()
                .inject(this)
    }

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_book_classify_list_layout
    }

    companion object {
        var BOOK_RACK_TYPE:String="bookracktype"
        fun lunch(type:String):Fragment{
            val fragment = BookClassifyListFragment()
            val bundle = Bundle()
            bundle.putString(BOOK_RACK_TYPE,type)
            fragment.arguments = bundle
            return fragment
        }
    }
}