package com.example.happyghost.showtimeforkotlin.ui.book.rack

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.BookRackAdapter
import com.example.happyghost.showtimeforkotlin.bean.BookHelpList
import com.example.happyghost.showtimeforkotlin.bean.Recommend
import com.example.happyghost.showtimeforkotlin.inject.component.DaggerBookRackComponent
import com.example.happyghost.showtimeforkotlin.inject.module.BookRackMoudle
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/9/15.
 * @description
 */
class BookRackListFragment : BaseFragment<BookRackPresent>(),IBookRackView {
    @Inject lateinit var adapter :BookRackAdapter
    override fun loadRecommendList(list: List<Recommend.RecommendBooks>) {
        adapter.replaceData(list)
    }

    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView(mRootView: View?) {
        val recyclerView = mRootView!!.find<RecyclerView>(R.id.recyclerview)
        RecyclerViewHelper.initRecycleViewV(mContext,recyclerView,adapter,true)
    }

    override fun initInject() {
        DaggerBookRackComponent.builder()
                .applicationComponent(getAppComponent())
                .bookRackMoudle(BookRackMoudle(this))
                .build()
                .inject(this)
    }

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_list_layout
    }

    companion object {
        var BOOK_RACK_TYPE:String="bookracktype"
        fun lunch(type:String):Fragment{
            val fragment = BookRackListFragment()
            val bundle = Bundle()
            bundle.putString(BOOK_RACK_TYPE,type)
            fragment.arguments = bundle
            return fragment
        }
    }

}