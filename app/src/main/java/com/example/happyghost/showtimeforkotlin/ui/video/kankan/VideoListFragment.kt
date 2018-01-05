package com.example.happyghost.showtimeforkotlin.ui.video.kankan

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.videoadapter.VideoListAdapter
import com.example.happyghost.showtimeforkotlin.bean.videodata.VideoDetailInfo
import com.example.happyghost.showtimeforkotlin.bean.videodata.VideoListDate
import com.example.happyghost.showtimeforkotlin.inject.component.videocomponent.DaggerVideoListComponent
import com.example.happyghost.showtimeforkotlin.inject.module.videomodule.VideoListCategroyIdAdapter
import com.example.happyghost.showtimeforkotlin.inject.module.videomodule.VideoListModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.ui.video.play.DefaultVideoPlayActivity
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import com.example.happyghost.showtimeforkotlin.utils.ScreenUtils
import com.facebook.drawee.view.SimpleDraweeView
import com.google.android.flexbox.*
import org.jetbrains.anko.find
import org.jetbrains.anko.margin
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/12/25.
 * @description
 */
class VideoListFragment: BaseFragment<VideoListPresenter>(),IBaseViedoListView, View.OnClickListener {
    lateinit var categoryId:String
    var titles=ArrayList<String>()
    var addId=0
    var defaultId = 111111
    @Inject lateinit var mAdapter: VideoListAdapter
    @Inject lateinit var mCategroyIdAdapter: VideoListCategroyIdAdapter
    var bundle = Bundle()
    var picName = ""
    var index = 0
    var videoSize = 0
    var handle =object :Handler(){
        override fun handleMessage(msg: Message?) {
            val what = msg?.what
            if(what==0){
                DefaultVideoPlayActivity.open(mContext!!,bundle)
            }
        }
    }
    override fun loadMoreVideoForCategoryidDate(date: VideoListDate) {
        mCategroyIdAdapter.loadMoreComplete()
        mCategroyIdAdapter.addData(date.contList!!)
    }
    override fun loadVideoInfo(date: VideoDetailInfo,boolean: Boolean) {
        val videos = date.content?.videos
        var url = ""
        videos?.forEach {
            if(TextUtils.equals(it.tag,"sd")){
                url = it.url!!
            }
        }
        bundle.putString(date.content?.name,url)
        if(!boolean){
            handle.sendEmptyMessage(0)
        }else{
            index++
            if(index == videoSize){
                handle.sendEmptyMessage(0)
            }
        }

    }
    override fun loadVideoForCategoryidDate(date: VideoListDate) {
        val hotList = date.hotList
        if(hotList!=null){
            initHeadView(hotList)
        }
        val contList = date.contList
        if(contList!=null){
           mCategroyIdAdapter.replaceData(contList)
        }
    }

    private fun initHeadView(hotList: List<VideoListDate.HotListBean>) {
        val inflate = View.inflate(mContext, R.layout.adapter_video_list_item, null)
        val titleView = inflate.find<TextView>(R.id.titleView).apply {
            visibility = View.GONE
        }
        val flexboxLayout = inflate.find<FlexboxLayout>(R.id.flexBox).apply {
            flexDirection=FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.FLEX_START
        }
        val widthPixels = ScreenUtils.getScreenWidth()
        val marginPixels = mContext?.resources?.getDimensionPixelOffset(R.dimen.photo_margin_width)
        val width =widthPixels/2-marginPixels!!
        var index = 0
        while (index<hotList.size){
            var linearLayout = LinearLayout(mContext)
            linearLayout.id = (addId+1)*defaultId
            linearLayout.tag = hotList[index].contId
            linearLayout.setOnClickListener(this)
            linearLayout.orientation =LinearLayout.VERTICAL
            flexboxLayout.addView(linearLayout)
            var simpleDraweeView = SimpleDraweeView(mContext)
            var layoutParamsLin = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            val module = hotList.size % 2
            if(index == hotList.size-1&&module!=0){
                layoutParamsLin.width = widthPixels-marginPixels-4
                layoutParamsLin.height =widthPixels/3
                layoutParamsLin.margin =2
                simpleDraweeView.layoutParams = layoutParamsLin
            }else{
                layoutParamsLin.width = width-4
                layoutParamsLin.height =widthPixels/3
                layoutParamsLin.margin =2
                simpleDraweeView.layoutParams = layoutParamsLin
            }
            simpleDraweeView.setImageURI(hotList[index].pic)
            linearLayout.addView(simpleDraweeView)
            val textView = TextView(mContext)
            textView.setSingleLine(true)
            textView.ellipsize = TextUtils.TruncateAt.valueOf("END")
            textView.text = hotList[index].name
            linearLayout.addView(textView)
            index++
            addId++
        }
        if (mCategroyIdAdapter.headerLayout != null) {
            mCategroyIdAdapter.removeAllHeaderView()
            mCategroyIdAdapter.addHeaderView(inflate)
        } else {
            mCategroyIdAdapter.addHeaderView(inflate)
        }
    }
    override fun onClick(v: View?) {
        val id = v?.id
        var index = 0
        while (index<=addId){
            if(id == (index+1)*defaultId){
                val linearLayout = find<LinearLayout>(id)
                val tag = linearLayout.tag as String
                mPresenter.getVideoInfoUrl(tag,false)
            }
            index++
        }
    }
    override fun loadVideoListDate(date: VideoListDate) {
         mAdapter.replaceData(date.dataList!!)
    }
    override fun upDataView() {
         if(TextUtils.equals(categoryId,"0")){
             mPresenter.getData()
         }else{
             mPresenter.getCategoryDate(categoryId)
         }
    }

    override fun initView(mRootView: View?) {
        val recyclerView = mRootView!!.find<RecyclerView>(R.id.recyclerview)
        if(TextUtils.equals(categoryId,"0")){
            RecyclerViewHelper.initRecycleViewV(mContext,recyclerView,mAdapter,true)
            mAdapter.setOnItemClickListener { adapter, view, position ->
                bundle.clear()
//                titles.clear()
                index=0
                val dataListBean = adapter.getItem(position) as VideoListDate.DataListBean
                val contList = dataListBean.contList
                videoSize = contList?.size!!
                contList.forEach {
                    val coverVideo = it.coverVideo
                    if(coverVideo!=null){
                        bundle.putString(it.name,coverVideo)
                        index++
                        if(index == videoSize){
                            handle.sendEmptyMessage(0)
                        }
                    }else{
                        picName = it.name!!
                        mPresenter.getVideoInfoUrl(it.contId, true)
                    }
//                    titles.add(it.name!!)
                }
            }
        }else{
            RecyclerViewHelper.initRecycleViewV(mContext,recyclerView,mCategroyIdAdapter,true)
            mCategroyIdAdapter.setOnItemClickListener { adapter, _, position ->
                val contListBean = adapter.getItem(position) as VideoListDate.ContListBean
                mPresenter.getVideoInfoUrl(contListBean.contId, false)
            }
        }
    }

    override fun initInject() {
        categoryId = arguments!!.getString(CATEGORY_ID)
        DaggerVideoListComponent.builder()
                .applicationComponent(getAppComponent())
                .videoListModule(VideoListModule(this))
                .build()
                .inject(this)
    }

    override fun getFragmentLayout(): Int = R.layout.fragment_list_layout
    companion object {
        var CATEGORY_ID = "id"
        fun instance(categoryId: String):Fragment{
            val fragment = VideoListFragment()
            val bundle = Bundle()
            bundle.putString(CATEGORY_ID,categoryId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handle.removeCallbacksAndMessages(null)
    }
}