package com.example.happyghost.showtimeforkotlin.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * @author Zhao Chenping
 * @creat 2017/8/24.
 * @description
 */
class ImageLoader {
    companion object {
        fun loadImageFromRes(context: Context,resId:Int,imageView: ImageView){
            Glide.with(context).load(resId).into(imageView)
        }

        fun loadCenterCrop(context: Context, url: String, view: ImageView, defaultResId: Int) {
            if (PreferencesUtils.isShowImageAlways() || NetUtil.isWifiConnected(context)) {
                Glide.with(context).load(url).centerCrop().dontAnimate().placeholder(defaultResId).into(view)
            } else if(NetUtil.isMobileConnected(context)&&PreferencesUtils.isShowImageAlways()){
                Glide.with(context).load(url).centerCrop().dontAnimate().placeholder(defaultResId).into(view)
            }else{
                view.setImageResource(defaultResId)
            }
        }
    }

}