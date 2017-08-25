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
    fun loadImageFromRes(context: Context,resId:Int,imageView: ImageView){
        Glide.with(context).load(resId).into(imageView)
    }
}