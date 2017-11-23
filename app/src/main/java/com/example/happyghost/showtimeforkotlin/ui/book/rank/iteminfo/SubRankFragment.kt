package com.example.happyghost.showtimeforkotlin.ui.book.rank.iteminfo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.SubRankListAdapter
import com.example.happyghost.showtimeforkotlin.bean.bookdate.Rankings
import com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent.DaggerSubRankListComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.SubRankListModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/11/20.
 * @description
 */
class SubRankFragment:BaseFragment<SubRankListPresenter>(),ISubRankBaseView {
    lateinit var mRankType:String
    @Inject lateinit var mAdapter: SubRankListAdapter
    override fun loadRankList(data: Rankings.RankingBean) {
        mAdapter.replaceData(data.books!!)
    }

    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView(mRootView: View?) {
        val recyclerView = mRootView!!.find<RecyclerView>(R.id.recyclerview)
        RecyclerViewHelper.initRecycleViewV(mContext,recyclerView,mAdapter,true)
    }

    override fun initInject() {
        mRankType = arguments.getString(SUB_RANK_FRAGMENT_TYPE)
        DaggerSubRankListComponent.builder()
                .applicationComponent(getAppComponent())
                .subRankListModule(SubRankListModule(this, mRankType))
                .build()
                .inject(this)
    }

    override fun getFragmentLayout(): Int = R.layout.fragment_list_layout
    companion object {
        var SUB_RANK_FRAGMENT_TYPE = "subranktype"
        fun newInstance(mId: String):Fragment {
            val fragment = SubRankFragment()
            val bundle = Bundle()
            bundle.putString(SUB_RANK_FRAGMENT_TYPE, mId)
            fragment.arguments = bundle
            return fragment
        }
    }
}