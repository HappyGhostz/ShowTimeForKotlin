package com.example.happyghost.showtimeforkotlin.adapter.crossadapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.crossdate.CrossTalkDate
import com.example.happyghost.showtimeforkotlin.utils.GlideCircleTransform
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader

/**
 * @author Zhao Chenping
 * @creat 2017/12/8.
 * @description
 */
class CrossTalkAdapter:BaseQuickAdapter<CrossTalkDate.DataBeanX.DataBean,BaseViewHolder>(R.layout.adapter_cross_talk) {
    override fun convert(helper: BaseViewHolder?, item: CrossTalkDate.DataBeanX.DataBean?) {
        val imageView = helper?.getView<ImageView>(R.id.ivBookCover)
        ImageLoader.loadCenterCropWithTransform(mContext,item?.group?.user?.avatar_url!!,imageView!!,GlideCircleTransform(mContext),R.mipmap.avatar_default)
        helper.setText(R.id.tvCrossTitle,item.group?.user?.name)
                .setText(R.id.tvContent,item.group?.content)
    }
}