package com.example.happyghost.showtimeforkotlin.ui.music.local

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RelativeLayout
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.musicadapter.MusicLocalListAdapter
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongLocalBean
import com.example.happyghost.showtimeforkotlin.inject.component.musiccomponent.DaggerMusicLocalListComponent
import com.example.happyghost.showtimeforkotlin.inject.module.musicmodule.MusicLocalListModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * Created by e445 on 2017/11/23.
 */
class MusicLocalFragment:BaseFragment<MusicLocalListPresenter>(),IBaseLocalMusicView {
    @Inject lateinit var mAdapter:MusicLocalListAdapter
    lateinit var allMusicLayout: RelativeLayout
    override fun loadLocalMusicListInfo(localBeanList: List<SongLocalBean>) {
        mAdapter.replaceData(localBeanList)
    }
    override fun upDataView() {
        mPresenter.getData()
    }
    override fun initView(mRootView: View?) {
        val recyclerView = mRootView!!.find<RecyclerView>(R.id.recyclerview)
        RecyclerViewHelper.initRecycleViewV(mContext,recyclerView,mAdapter,true)
        var musicHead = View.inflate(mContext, R.layout.item_head_music, null)
        allMusicLayout = musicHead!!.find<RelativeLayout>(R.id.rl_play_all_layout)
        if (mAdapter.headerLayout != null) {
            mAdapter.removeAllHeaderView()
            mAdapter.addHeaderView(musicHead)
        } else {
            mAdapter.addHeaderView(musicHead)
        }
    }

    override fun initInject() {
        DaggerMusicLocalListComponent.builder()
                .applicationComponent(getAppComponent())
                .musicLocalListModule(MusicLocalListModule(this))
                .build()
                .inject(this)
    }

    override fun getFragmentLayout(): Int = R.layout.fragment_list_layout
    companion object {
        fun lunch():Fragment{
            val fragment = MusicLocalFragment()
            return fragment
        }
    }
}