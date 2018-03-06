package com.example.happyghost.showtimeforkotlin.adapter.crossadapter

import android.text.TextUtils
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.crossdate.FunnyPictureDate
import com.example.happyghost.showtimeforkotlin.utils.*
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView

/**
 * @author Zhao Chenping
 * @creat 2017/12/9.
 * @description
 */
class FunnyPictureAdapter: BaseQuickAdapter<FunnyPictureDate.DataBeanX.DataBean, BaseViewHolder>(R.layout.adapter_funny_picture) {
    override fun convert(helper: BaseViewHolder?, item: FunnyPictureDate.DataBeanX.DataBean?) {
        val widthPixels = ScreenUtils.getScreenWidth()
        val photoWidth = widthPixels
        val r_height = item?.group?.large_image?.r_height
        val r_width = item?.group?.large_image?.r_width
        val photoHeight = ScreenUtils.calcPhotoHeight(r_height, r_width, photoWidth)
        val avatar = helper?.getView<ImageView>(R.id.iv_avatar)
        ImageLoader.loadCenterCropWithTransform(mContext,item?.group?.user?.avatar_url,avatar!!,GlideCircleTransform(mContext),R.mipmap.avatar_default)
        helper.setText(R.id.tv_title,item?.group?.text)
        val photo = helper.getView<SimpleDraweeView>(R.id.iv_photo)
        val layoutParams = photo.layoutParams
        layoutParams.width = photoWidth
        layoutParams.height = photoHeight
        photo.layoutParams = layoutParams
        var url: String? = null
//        Log.e("FunnyPictureAdapter",ConsTantUtils.CROSS_PICTURE_URL+item.group?.large_image?.uri)
        if(TextUtils.isEmpty(item?.group?.large_image?.uri)){

            item?.group?.large_image?.url_list?.forEach {
                if(!TextUtils.isEmpty(it.url)){
                    url = it.url
                }else{
                    url = "http://u.candou.com/2016/0422/1461293198116.jpg"
                }
            }
        }else{
            url = item?.group?.large_image?.uri
        }
        var  controller = Fresco.newDraweeControllerBuilder()
                .setUri(ConsTantUtils.CROSS_PICTURE_URL+url)
                .setAutoPlayAnimations(true)
                .setTapToRetryEnabled(true)
                .build()
        photo.controller = controller
//        ImageLoader.loadCenterCrop(mContext,ConsTantUtils.CROSS_PICTURE_URL+item.group?.large_image?.uri,photo,DefIconFactory.provideIcon())
    }
}