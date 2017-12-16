package com.example.happyghost.showtimeforkotlin.ui.video.live

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.videoadapter.LiveAdapter
import com.example.happyghost.showtimeforkotlin.bean.videodata.LiveListBean
import com.example.happyghost.showtimeforkotlin.inject.component.videocomponent.DaggerLiveFragmentComponent
import com.example.happyghost.showtimeforkotlin.inject.module.videomodule.LiveFragmentModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/12/15.
 * @description
 */
class LiveFragment: BaseFragment<LivePresenter>(),ILiveBaseView {
    @Inject lateinit var mAdapter: LiveAdapter
    lateinit var mGameType:String
    lateinit var mLiveType:String

    override fun loadLiveDate(live: LiveListBean) {
        mAdapter.replaceData(live.result!!)
    }

    override fun loadMoreLiveDate(live: LiveListBean) {
        mAdapter.loadMoreComplete()
        mAdapter.addData(live.result!!)
    }


    override fun upDataView() {
        mPresenter.getLiveDate(mGameType,mLiveType)
    }

    override fun initView(mRootView: View?) {
        val recyclerView = mRootView!!.find<RecyclerView>(R.id.recyclerview)
        RecyclerViewHelper.initRecycleViewG(mContext,recyclerView,mAdapter,2,true)
    }

    override fun initInject() {
        mGameType = arguments.getString(GAME_TYPE)
        mLiveType = arguments.getString(LIVE_TYPE)
        DaggerLiveFragmentComponent.builder()
                .applicationComponent(getAppComponent())
                .liveFragmentModule(LiveFragmentModule(this))
                .build()
                .inject(this)
    }

    override fun getFragmentLayout(): Int  = R.layout.fragment_list_layout
    companion object {
        var GAME_TYPE="gametype"
        var LIVE_TYPE="livetype"
        fun newInstance(gameType: String, liveType: String):Fragment{
            var liveFragment = LiveFragment()
            var bundle = Bundle()
            bundle.putString(GAME_TYPE,gameType)
            bundle.putString(LIVE_TYPE,liveType)
            liveFragment.arguments = bundle
            return liveFragment
        }
    }
}