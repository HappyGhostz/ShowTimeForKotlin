package com.example.happyghost.showtimeforkotlin.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader
import com.github.ybq.android.spinkit.SpinKitView
import org.jetbrains.anko.find
import uk.co.senab.photoview.PhotoView
import java.lang.Exception

/**
 * @author Zhao Chenping
 * @creat 2017/9/13.
 * @description
 */
class PhotoSetAdapter(context: Context,list:MutableList<String>) : PagerAdapter() {
    var mContext = context
    var mImgList = list
    lateinit var mTapListener :OnTapListener
    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mImgList.size
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val view = LayoutInflater.from(mContext).inflate(R.layout.adapter_photo_set_news, null,false)
        val photoView = view.find<PhotoView>(R.id.iv_photo)
        val spinKitView = view.find<SpinKitView>(R.id.sv_loading)
        val textView = view.find<TextView>(R.id.tv_reload)

        val requestListener: RequestListener<String, GlideDrawable> = object : RequestListener<String, GlideDrawable> {
            override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                spinKitView.visibility = View.GONE
                textView.visibility = View.VISIBLE
                return false
            }

            override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                spinKitView.visibility = View.GONE
                textView.visibility = View.GONE
                photoView.setImageDrawable(resource)
                return true
            }
        }
        ImageLoader.loadFitCenter(mContext,mImgList.get(position % mImgList.size),photoView, requestListener)
        photoView.setOnPhotoTapListener { view, x, y ->
            if(mTapListener!=null){
                mTapListener.onPhotoClick()
            }
        }
        textView.setOnClickListener {
            textView.visibility = View.GONE
            spinKitView.visibility = View.VISIBLE
            ImageLoader.loadFitCenter(mContext,mImgList.get(position % mImgList.size),photoView, requestListener)
        }
        container?.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container?.removeView(`object` as View)
    }

    fun setTapListener(listener: OnTapListener) {
        mTapListener = listener
    }

    interface OnTapListener {
        fun onPhotoClick()
    }
}