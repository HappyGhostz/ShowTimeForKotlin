package com.example.happyghost.showtimeforkotlin.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener

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
            if (SharedPreferencesUtil.isShowImageAlways() || NetUtil.isWifiConnected(context)) {
                Glide.with(context).load(url).centerCrop().dontAnimate().placeholder(defaultResId).into(view)
            } else if(NetUtil.isMobileConnected(context)&& SharedPreferencesUtil.isShowImageAlways()){
                Glide.with(context).load(url).centerCrop().dontAnimate().placeholder(defaultResId).into(view)
            }else{
                view.setImageResource(defaultResId)
            }
        }
        fun loadFitCenter(context: Context, imgsrc: String?, imageView: ImageView, provideIcon: Int) {
            if(SharedPreferencesUtil.isShowImageAlways()||NetUtil.isWifiConnected(context)){
                Glide.with(context).load(imgsrc).fitCenter().dontAnimate().placeholder(provideIcon).into(imageView)
            }else{
                imageView.setImageResource(provideIcon)
            }
        }
        /**
         * 带监听处理
         *
         * @param context
         * @param url
         * @param view
         * @param listener
         */
        fun loadFitCenter(context: Context, imgsrc: String?, imageView: ImageView, requestListener: RequestListener<String, GlideDrawable>){
            Glide.with(context).load(imgsrc).fitCenter().dontAnimate().listener(requestListener).into(imageView)
        }
    }

}