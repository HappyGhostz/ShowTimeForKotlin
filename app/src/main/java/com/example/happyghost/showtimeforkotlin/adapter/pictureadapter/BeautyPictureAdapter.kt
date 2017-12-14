package com.example.happyghost.showtimeforkotlin.adapter.pictureadapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.picturedate.BeautyPicture
import com.example.happyghost.showtimeforkotlin.ui.picture.BegPictureActivity
import com.example.happyghost.showtimeforkotlin.utils.ScreenUtils
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView

/**
 * @author Zhao Chenping
 * @creat 2017/12/14.
 * @description
 */
class BeautyPictureAdapter:BaseQuickAdapter<BeautyPicture.ImgsBean,BaseViewHolder>(R.layout.adapter_beauty_picyure) {
    var descDefault =""
    override fun convert(helper: BaseViewHolder?, item: BeautyPicture.ImgsBean?) {
        val widthPixels = ScreenUtils.getScreenWidth()
        val marginPixels = mContext.resources.getDimensionPixelOffset(R.dimen.photo_margin_width)
        var mPhotoWidth = widthPixels / 2 - marginPixels
        helper?.setText(R.id.tv_title,item?.desc)
        var simpleDraweeView = helper?.getView<SimpleDraweeView>(R.id.iv_photo)

        val photoHeight = ScreenUtils.calcPhotoHeight(item?.imageHeight, item?.imageWidth, mPhotoWidth)

        var layoutParams = simpleDraweeView!!.layoutParams
        layoutParams?.width = mPhotoWidth
        layoutParams?.height =photoHeight
        simpleDraweeView.layoutParams = layoutParams
        var  controller = Fresco.newDraweeControllerBuilder()
                .setUri(item?.imageUrl)
                .setAutoPlayAnimations(true)
                .setTapToRetryEnabled(true)
                .build()
        simpleDraweeView.controller = controller

        helper?.itemView?.setOnClickListener {
            if(item?.desc!=null){
                descDefault = item.desc!!
            }else{
                descDefault = "美图"
            }
             BegPictureActivity.open(mContext,item?.imageUrl!!,descDefault)
        }
    }
}