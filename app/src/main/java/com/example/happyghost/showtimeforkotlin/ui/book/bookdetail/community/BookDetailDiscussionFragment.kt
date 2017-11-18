package com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.community

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.BookDetailDiscussionAdapter
import com.example.happyghost.showtimeforkotlin.bean.bookdata.DiscussionList
import com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent.DaggerBookDetailDiscussionComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookDetailDiscussionModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
class BookDetailDiscussionFragment: BaseFragment<BookDetailDiscussionPresenter>(),IBaseDetialDiscussionView {
    override fun showBookDetailDiscussionMoreList(list: List<DiscussionList.PostsBean>) {
        mAdapter.loadMoreComplete()
        mAdapter.addData(list)
    }

    lateinit var mBookId:String
    @Inject lateinit var mAdapter: BookDetailDiscussionAdapter
    override fun showBookDetailDiscussionList(list: List<DiscussionList.PostsBean>) {
        mAdapter.replaceData(list)
    }

    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView(mRootView: View?) {
        val recyclerView = mRootView!!.find<RecyclerView>(R.id.recyclerview)
        RecyclerViewHelper.initRecycleViewV(mContext,recyclerView,mAdapter,true)
    }

    override fun initInject() {
        mBookId = arguments.getString(BUNDLE_ID)
        DaggerBookDetailDiscussionComponent.builder()
                .applicationComponent(getAppComponent())
                .bookDetailDiscussionModule(BookDetailDiscussionModule(this,mBookId))
                .build()
                .inject(this)
    }

    override fun getFragmentLayout(): Int = R.layout.fragment_list_layout
    companion object {
        var BUNDLE_ID ="bundleid"
        fun newInstance(bookId: String): Fragment {
            val fragment = BookDetailDiscussionFragment()
            val bundle = Bundle()
            bundle.putString(BUNDLE_ID, bookId)
            fragment.arguments = bundle
            return fragment
        }
    }
}