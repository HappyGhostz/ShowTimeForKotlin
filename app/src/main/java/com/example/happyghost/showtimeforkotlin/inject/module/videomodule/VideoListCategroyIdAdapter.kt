package com.example.happyghost.showtimeforkotlin.inject.module.videomodule

import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.videodata.VideoListDate
import com.example.happyghost.showtimeforkotlin.utils.ScreenUtils
import com.facebook.drawee.view.SimpleDraweeView
import org.jetbrains.anko.margin

/**
 * @author Zhao Chenping
 * @creat 2017/12/29.
 * @description
 */
class VideoListCategroyIdAdapter:BaseQuickAdapter<VideoListDate.ContListBean,BaseViewHolder>(R.layout.adapter_video_cateory_item) {
    override fun convert(helper: BaseViewHolder?, item: VideoListDate.ContListBean?) {
        val widthPixels = ScreenUtils.getScreenWidth()
        val marginPixels = mContext.resources.getDimensionPixelOffset(R.dimen.photo_margin_width)

        var simpleDraweeView = helper?.getView<SimpleDraweeView>(R.id.categoryVideo)
//        var builder = GenericDraweeHierarchyBuilder(mContext.resources)
//        val hierarchy = builder
//                .setFadeDuration(300)
//                .setFailureImage(DefIconFactory.provideNoDataIcon())
//                .setPlaceholderImage(DefIconFactory.provideIcon())
//                .build()
        var layoutParamsLin = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParamsLin.width = widthPixels-marginPixels-4
        layoutParamsLin.height =widthPixels/3
        layoutParamsLin.margin =2
        simpleDraweeView?.layoutParams = layoutParamsLin
//        simpleDraweeView?.hierarchy = hierarchy
        simpleDraweeView?.setImageURI(item?.pic)

        helper?.setText(R.id.cateVideoTitle,item?.name)
    }
}