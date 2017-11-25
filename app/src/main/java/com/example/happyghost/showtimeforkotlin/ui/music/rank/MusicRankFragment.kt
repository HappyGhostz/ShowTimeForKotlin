package com.example.happyghost.showtimeforkotlin.ui.music.rank

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.musicadapter.MusicRankAdapter
import com.example.happyghost.showtimeforkotlin.bean.musicdate.RankingListItem
import com.example.happyghost.showtimeforkotlin.inject.component.musiccomponent.DaggerMusicRankComponent
import com.example.happyghost.showtimeforkotlin.inject.module.musicmodule.MusicRankModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * Created by e445 on 2017/11/23.
 */
class MusicRankFragment: BaseFragment<MusicRankPresenter>(),IMusicRankView {
    @Inject lateinit var mAdapter: MusicRankAdapter

    override fun loadListMusic(details: List<RankingListItem.RangkingDetail>) {
        mAdapter.replaceData(details)
    }
    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView(mRootView: View?) {
        val recyclerView = mRootView!!.find<RecyclerView>(R.id.recyclerview)
        RecyclerViewHelper.initRecycleViewV(mContext,recyclerView,mAdapter,true)
    }

    override fun initInject() {
        DaggerMusicRankComponent.builder()
                .applicationComponent(getAppComponent())
                .musicRankModule(MusicRankModule(this))
                .build()
                .inject(this)
    }

    override fun getFragmentLayout(): Int  = R.layout.fragment_list_layout
    companion object {
        fun lunch(): Fragment {
            val fragment = MusicRankFragment()
            return fragment
        }
    }
}