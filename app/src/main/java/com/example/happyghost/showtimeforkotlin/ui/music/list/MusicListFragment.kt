package com.example.happyghost.showtimeforkotlin.ui.music.list

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.musicadapter.MusicListAdapter
import com.example.happyghost.showtimeforkotlin.bean.musicdate.WrapperSongListInfo
import com.example.happyghost.showtimeforkotlin.inject.component.musiccomponent.DaggerMusicListComponent
import com.example.happyghost.showtimeforkotlin.inject.module.musicmodule.MusicLisModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * Created by e445 on 2017/11/23.
 */
class MusicListFragment: BaseFragment<MusicListPresenter>(),IListMusicView {
    @Inject lateinit var mAdapter: MusicListAdapter
    override fun loadListMusic(infos: List<WrapperSongListInfo.SongListInfo>) {

    }

    override fun loadMoreListMusic(infos: List<WrapperSongListInfo.SongListInfo>) {

    }

    override fun loadNoData() {

    }

    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView(mRootView: View?) {
        val recyclerView = mRootView!!.find<RecyclerView>(R.id.recyclerview)
        RecyclerViewHelper.initRecycleViewG(mContext,recyclerView,mAdapter,2,true)
    }

    override fun initInject() {
        DaggerMusicListComponent.builder()
                .applicationComponent(getAppComponent())
                .musicLisModule(MusicLisModule(this))
                .build()
                .inject(this)
    }

    override fun getFragmentLayout(): Int = R.layout.fragment_list_layout
    companion object {
        fun lunch(): Fragment {
            val fragment = MusicListFragment()
            return fragment
        }
    }
}