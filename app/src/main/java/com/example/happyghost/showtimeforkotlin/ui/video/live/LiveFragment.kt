package com.example.happyghost.showtimeforkotlin.ui.video.live

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.RxBus.event.LiveTypeChangeEvent
import com.example.happyghost.showtimeforkotlin.adapter.videoadapter.LiveAdapter
import com.example.happyghost.showtimeforkotlin.bean.videodata.LiveDetailBean
import com.example.happyghost.showtimeforkotlin.adapter.videoadapter.LiveDouyuAdapter
import com.example.happyghost.showtimeforkotlin.bean.videodata.DouyuVideoInfo
import com.example.happyghost.showtimeforkotlin.bean.videodata.LiveListBean
import com.example.happyghost.showtimeforkotlin.inject.component.videocomponent.DaggerLiveFragmentComponent
import com.example.happyghost.showtimeforkotlin.inject.module.videomodule.LiveFragmentModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.ui.video.play.DouyuWebViewPlayActivity
import com.example.happyghost.showtimeforkotlin.ui.video.play.VideoPlayActivity
import com.example.happyghost.showtimeforkotlin.utils.ListUtils
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import io.reactivex.functions.Consumer
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/12/15.
 * @description
 */
class LiveFragment: BaseFragment<LivePresenter>(),ILiveBaseView {
    @Inject lateinit var mAdapter: LiveAdapter
    @Inject lateinit var mDouyuAdapter: LiveDouyuAdapter
    lateinit var mGameType:String
    lateinit var mLiveType:String
    var liveTitle:String?=null
    var douyuCateid:String?=null

    override fun loadLiveDate(live: LiveListBean) {
        if(!TextUtils.equals(mLiveType,"douyu")){
            mAdapter.replaceData(live.result!!)
        }else{
            mDouyuAdapter.replaceData(live.data!!)
        }
    }

    override fun loadMoreLiveDate(live: LiveListBean) {
        if(!TextUtils.equals(mLiveType,"douyu")){
            mAdapter.loadMoreComplete()
            mAdapter.addData(live.result!!)
        }else{
            mDouyuAdapter.loadMoreComplete()
            mDouyuAdapter.addData(live.data!!)
        }
    }


    override fun upDataView() {
        if(TextUtils.equals(mLiveType,"douyu")){
            mPresenter.getDoyuLivDate(mLiveType,mGameType)
        }else{
            mPresenter.getLiveDate(mGameType,mLiveType)
        }
    }
    override fun openVideoPlay(liveDetailBean: LiveDetailBean) {
        var superVideo = ArrayList<String>()
        var hightVideo = ArrayList<String>()
        var defaultVideo = ArrayList<String>()
        val liveTitle = liveDetailBean.result?.live_title
        val liveUrls = liveDetailBean.result?.stream_list
        var liveUrl = ""
        var index = 0
        if(ListUtils.isEmpty(liveUrls)){
            return
        }
        while (index<liveUrls!!.size){
            val streamListBean = liveUrls[index]
            if(TextUtils.equals("超清",streamListBean.type)){
                superVideo.add(streamListBean.url!!)

            }else if(TextUtils.equals("高清",streamListBean.type)){
                hightVideo.add(streamListBean.url!!)
            }else if(TextUtils.equals("普清",streamListBean.type)){
                defaultVideo.add(streamListBean.url!!)
            }
            index++
        }
        if(superVideo.size>0){
            liveUrl = superVideo[0]
        }else if (hightVideo.size>0){
            liveUrl = hightVideo[0]
        }else if (defaultVideo.size>0){
            liveUrl = defaultVideo[0]
        }else{
            toast("视频地址不存在!")
            return
        }
        VideoPlayActivity.open(mContext!!, liveTitle!!, liveUrl, "sensorLandscape")
    }
    override fun openDouyuVideoPlay(liveDetailBean: DouyuVideoInfo) {
        val dataEntity = liveDetailBean.data
        val liveUrl = dataEntity!!.hls_url
        val roomName = liveTitle
        var screenOrientation =""
        if(!TextUtils.equals(douyuCateid,"webVideo")){
            screenOrientation = douyuCateid!!
            VideoPlayActivity.open(mContext!!,roomName!!,liveUrl!!,screenOrientation)
        }else{
            toast("需Web页播放")
        }
    }



    override fun initView(mRootView: View?) {
        val recyclerView = mRootView!!.find<RecyclerView>(R.id.recyclerview)
        if(TextUtils.equals(mLiveType,"douyu")){
            RecyclerViewHelper.initRecycleViewG(mContext,recyclerView,mDouyuAdapter,2,true)
        }else{
            RecyclerViewHelper.initRecycleViewG(mContext,recyclerView,mAdapter,2,true)
        }
        mPresenter.registerRxBus(LiveTypeChangeEvent::class.java, Consumer {
            t -> handlChangeLiveType(t)
        })
        mAdapter.setOnItemClickListener { adapter, view, position ->
            //我在点击条目的时候获取直播详情，这样传递给播放页面一条URL就可以了，这样播放页面可重用性就会很高
            var itemDate = adapter.getItem(position) as LiveListBean.ResultBean
            val game_type = itemDate.game_type
            val live_type = itemDate.live_type
            val live_id = itemDate.live_id
            mPresenter.getVideoPlayDetail(game_type,live_type,live_id)
        }
        mDouyuAdapter.setOnItemClickListener { adapter, _, position ->
            val dataBean = adapter.getItem(position) as LiveListBean.DataBean
            liveTitle = dataBean.room_name
            if(dataBean.cate_id== 201){
                douyuCateid = "sensorPortrait"
            }else if (dataBean.cate_id==207){
                douyuCateid="webVideo"
                DouyuWebViewPlayActivity.open(mContext!!,dataBean.room_name!!,dataBean.jumpUrl!!)
                return@setOnItemClickListener
            }else{
                douyuCateid="sensorLandscape"
            }
            mPresenter.getDouyuVideo(dataBean.room_id!!)
        }
    }

    private fun handlChangeLiveType(liveTypeChangeEvent: LiveTypeChangeEvent) {
        mLiveType = liveTypeChangeEvent.liveType
        upDataView()
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