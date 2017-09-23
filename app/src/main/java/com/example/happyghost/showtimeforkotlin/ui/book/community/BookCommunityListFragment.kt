package com.example.happyghost.showtimeforkotlin.ui.book.community

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.BookCommunityAdapter
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookHelpList
import com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent.DaggerBookCommunityComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookCommunityModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.utils.DefIconFactory
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import kotlinx.android.synthetic.main.fragment_list_layout.*
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/9/15.
 * @description
 */
class BookCommunityListFragment: BaseFragment<BookCommunityPresenter>(),IBookCommunityView {

    @Inject lateinit var mAdapter:BookCommunityAdapter
    override fun loadCommunityList(list: List<BookHelpList.HelpsBean>) {
        mAdapter.replaceData(list)
    }

    override fun loadMoreCommunity(list: List<BookHelpList.HelpsBean>) {
        mAdapter.loadMoreComplete()
        mAdapter.addData(list)
    }
    override fun loadNoData() {
        val inflate = View.inflate(mContext, R.layout.item_no_data_layout, null)
        val imageView = inflate.find<ImageView>(R.id.iv_noData)
        ImageLoader.loadImageFromRes(this!!.mContext!!, DefIconFactory.provideNoDataIcon(),imageView)
        mAdapter.addFooterView(inflate)
        smart_refresh.setLoadmoreFinished(true)
    }


    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView(mRootView: View?) {
        val recyclerView = mRootView!!.find<RecyclerView>(R.id.recyclerview)
        RecyclerViewHelper.initRecycleViewV(mContext,recyclerView,mAdapter,true)
        mAdapter.openLoadAnimation()
    }

    override fun initInject() {
        DaggerBookCommunityComponent.builder()
                .applicationComponent(getAppComponent())
                .bookCommunityModule(BookCommunityModule(this))
                .build()
                .inject(this)
    }

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_list_layout
    }

    companion object {
        var BOOK_RACK_TYPE:String="bookracktype"
        fun lunch(type:String):Fragment{
            val fragment = BookCommunityListFragment()
            val bundle = Bundle()
            bundle.putString(BOOK_RACK_TYPE,type)
            fragment.arguments = bundle
            return fragment
        }
    }
}