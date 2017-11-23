package com.example.happyghost.showtimeforkotlin.ui.book.rank.iteminfo

import android.app.Activity
import android.content.Context
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.SubRankListAdapter
import com.example.happyghost.showtimeforkotlin.bean.bookdate.Rankings
import com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent.DaggerSubOtherHomeRankComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.SubOtherHomeRankModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import kotlinx.android.synthetic.main.activity_subject_detail.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/11/20.
 * @description
 */
class SubOtherHomeRankActivity:BaseActivity<SubOtherRankListPresenter>(),ISubOtherBaseView {
    lateinit var mRankOtherId:String
    lateinit var mRankOtherTitle:String
    @Inject lateinit var mAdapter:SubRankListAdapter
    override fun loadRankList(data: Rankings.RankingBean) {
        mAdapter.replaceData(data.books!!)
    }

    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView() {
        initActionBar(common_toolbar,mRankOtherTitle,true)
        RecyclerViewHelper.initRecycleViewV(this,rvDisscussionComments,mAdapter,true)
    }

    override fun initInjector() {
        mRankOtherId = intent.getStringExtra(RANK_OTHER_ID)
        mRankOtherTitle = intent.getStringExtra(RANK_OTHER_TITLE)
        DaggerSubOtherHomeRankComponent.builder()
                .applicationComponent(getAppComponent())
                .subOtherHomeRankModule(SubOtherHomeRankModule(this, mRankOtherId))
                .build()
                .inject(this)
    }

    override fun getContentView(): Int =R.layout.activity_subject_detail
    companion object {
        var RANK_OTHER_ID = "rankotherid"
        var RANK_OTHER_TITLE = "rankothertitle"
        fun startActivity(mContext: Context, _id: String, title: String) {
            mContext.startActivity<SubOtherHomeRankActivity>(RANK_OTHER_ID to _id,RANK_OTHER_TITLE to title)
            (mContext as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }
}