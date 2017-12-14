package com.example.happyghost.showtimeforkotlin.adapter.pictureadapter

import android.graphics.Bitmap
import android.net.Uri
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.picturedate.WelfarePhotoList
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.BasePostprocessor
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.example.happyghost.showtimeforkotlin.utils.ScreenUtils
import com.facebook.drawee.backends.pipeline.PipelineDraweeController
import com.example.happyghost.showtimeforkotlin.ui.picture.BegPictureActivity


/**
 * @author Zhao Chenping
 * @creat 2017/12/13.
 * @description
 */
class WelfPictureAdapter:BaseQuickAdapter<WelfarePhotoList.WelfarePhotoInfo,BaseViewHolder>(R.layout.adapter_welf_picture) {
    var photoHeight = 0
    override fun convert(helper: BaseViewHolder?, item: WelfarePhotoList.WelfarePhotoInfo?) {
        val widthPixels = ScreenUtils.getScreenWidth()
        val marginPixels = mContext.resources.getDimensionPixelOffset(R.dimen.photo_margin_width)
        var mPhotoWidth = widthPixels / 2 - marginPixels
        helper?.setText(R.id.tv_title,item?.createdAt)
        var simpleDraweeView = helper?.getView<SimpleDraweeView>(R.id.iv_photo)

        //改变图片大小(Fresco文档都有)
        var redMeshPostprocessor = object : BasePostprocessor() {
            override fun  getName():String {
                return "redMeshPostprocessor";
            }

            override fun process( bitmap:Bitmap) {
                photoHeight = ScreenUtils.calcPhotoHeight(bitmap.height, bitmap.width, mPhotoWidth)
            }
        }
        val parse = Uri.parse(item?.url)
        var  request = ImageRequestBuilder.newBuilderWithSource(parse)
                .setPostprocessor(redMeshPostprocessor)
                .build()

        var  controller = Fresco.newDraweeControllerBuilder()
                         .setImageRequest(request)
                          .setTapToRetryEnabled(true)
                         .setOldController(simpleDraweeView!!.controller)
                         .build() as PipelineDraweeController
        var layoutParams = simpleDraweeView.layoutParams
        layoutParams?.width = mPhotoWidth
        layoutParams?.height =photoHeight
        simpleDraweeView.layoutParams = layoutParams
        simpleDraweeView.controller = controller
        helper?.itemView?.setOnClickListener {
            BegPictureActivity.open(mContext,item?.url!!,item.createdAt!!)
        }
    }
}