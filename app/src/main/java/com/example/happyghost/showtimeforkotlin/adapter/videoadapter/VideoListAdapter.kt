package com.example.happyghost.showtimeforkotlin.adapter.videoadapter

import android.text.TextUtils
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.videodata.VideoListDate
import com.example.happyghost.showtimeforkotlin.utils.ScreenUtils
import com.facebook.drawee.view.SimpleDraweeView
import com.google.android.flexbox.*
import org.jetbrains.anko.margin


/**
 * @author Zhao Chenping
 * @creat 2017/12/25.
 * @description
 */
class VideoListAdapter:BaseQuickAdapter<VideoListDate.DataListBean,BaseViewHolder>(R.layout.adapter_video_list_item) {
    override fun convert(helper: BaseViewHolder?, item: VideoListDate.DataListBean?) {
//        val recyclerView = helper?.getView<RecyclerView>(R.id.adapterRecycleView)
//        helper?.setText(R.id.titleView,item?.nodeName)
//        val flexboxLayoutManager = FlexboxLayoutManager().apply {
//            flexWrap = FlexWrap.WRAP
//            flexDirection = FlexDirection.ROW
//            alignItems = AlignItems.STRETCH
//        }
//        var mAdapter = VideoItemAdapter()
//        recyclerView?.layoutManager = flexboxLayoutManager
//        recyclerView?.adapter = mAdapter
//        recyclerView.apply {
//            this!!.layoutManager = flexboxLayoutManager
//            adapter = mAdapter
//        }
////        RecyclerViewHelper.initRecycleViewSV(mContext,recyclerView!!,mAdapter,2,true )
//        val contList = item?.contList
//        if(contList!=null){
//            mAdapter.replaceData(item.contList!!)
//        }
        val flexboxLayout = helper?.getView<FlexboxLayout>(R.id.flexBox)
        flexboxLayout?.flexDirection=FlexDirection.ROW
        flexboxLayout?.flexWrap = FlexWrap.WRAP
        flexboxLayout?.justifyContent = JustifyContent.FLEX_START
        helper?.setText(R.id.titleView,item?.nodeName)
        val widthPixels = ScreenUtils.getScreenWidth()
        val marginPixels = mContext.resources.getDimensionPixelOffset(R.dimen.photo_margin_width)
        val width =widthPixels/2-marginPixels
        val contList = item?.contList
        var index = 0
//        var builder = GenericDraweeHierarchyBuilder(mContext.resources)
//        val hierarchy = builder
//                .setFadeDuration(300)
//                .setFailureImage(DefIconFactory.provideNoDataIcon())
//                .setPlaceholderImage(DefIconFactory.provideIcon())
//                .build()
        if(contList!=null){
            while (index<contList.size){

                var linearLayout = LinearLayout(mContext)
                linearLayout.orientation =LinearLayout.VERTICAL
//                var layoutParams = FlexboxLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
//                if(index == contList.size-1){
//                    layoutParams.width = widthPixels-marginPixels
//                    layoutParams.height =widthPixels/3
//                    linearLayout.layoutParams = layoutParams
//                }else{
//                    layoutParams.width = width
//                    layoutParams.height =widthPixels/3
//                    linearLayout.layoutParams = layoutParams
//                }

                flexboxLayout?.addView(linearLayout)

                var simpleDraweeView = SimpleDraweeView(mContext)
                var layoutParamsLin = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                val module = contList.size % 2
                if(index == contList.size-1&&module!=0){
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
//                simpleDraweeView.aspectRatio = 1.33f
//                simpleDraweeView.hierarchy = hierarchy
                simpleDraweeView.setImageURI(contList[index].pic)
                linearLayout.addView(simpleDraweeView)
                val textView = TextView(mContext)
                textView.setSingleLine(true)
                textView.ellipsize = TextUtils.TruncateAt.valueOf("END")
                textView.text = contList[index].name
                linearLayout.addView(textView)

                index++
            }
        }
    }
}