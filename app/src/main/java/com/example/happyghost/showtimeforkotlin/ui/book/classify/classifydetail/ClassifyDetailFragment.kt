package com.example.happyghost.showtimeforkotlin.ui.book.classify.classifydetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.ClassifyDetailAdapter
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BooksByCats
import com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent.DaggerClassifyDetailComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.ClassifyDetailModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/11/15.
 * @description
 */
class ClassifyDetailFragment : BaseFragment<ClassifyDetailPresenter>(),ICategoryBaseView {
    @Inject lateinit var mAdapter: ClassifyDetailAdapter
    override fun loadCategoryList(data: BooksByCats) {
        val books = data.books
        if (books != null) {
            mAdapter.replaceData(books)
        }
    }

    override fun loadMoreCategoryList(data: BooksByCats) {
        val books = data.books
        mAdapter.loadMoreComplete()
        if (books != null) {
            mAdapter.addData(books)
        }
    }

    override fun upDataView() {
         mPresenter.getData()
    }

    override fun initView(mRootView: View?) {
        val recyclerView = mRootView!!.find<RecyclerView>(R.id.recyclerview)
        RecyclerViewHelper.initRecycleViewV(mContext,recyclerView,mAdapter,true)
    }
    lateinit var mType :String
    lateinit var mName :String
    lateinit var mSex :String

    override fun initInject() {
        val arguments = arguments
        mType = arguments.getString(CLASSIFY_FRAGMENT_MAJOR)
        mSex = arguments.getString(CLASSIFY_FRAGMENT_SEX)
        mName = arguments.getString(CLASSIFY_FRAGMENT_NAME)
        DaggerClassifyDetailComponent.builder()
                .applicationComponent(getAppComponent())
                .classifyDetailModule(ClassifyDetailModule(this, mSex, mName, mType))
                .build()
                .inject(this)
    }

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_list_layout
    }
    companion object {
        var CLASSIFY_FRAGMENT_SEX = "sex"
        var CLASSIFY_FRAGMENT_MAJOR = "booktype"
        var CLASSIFY_FRAGMENT_NAME = "name"
        fun lunch(mCategoryName: String, mCategoryType: String, bookType: String):Fragment {
            val fragment = ClassifyDetailFragment()
            val bundle = Bundle()
            bundle.putString(CLASSIFY_FRAGMENT_SEX, mCategoryType)
            bundle.putString(CLASSIFY_FRAGMENT_MAJOR, bookType)
            bundle.putString(CLASSIFY_FRAGMENT_NAME, mCategoryName)
            fragment.arguments = bundle
            return fragment
        }
    }
}