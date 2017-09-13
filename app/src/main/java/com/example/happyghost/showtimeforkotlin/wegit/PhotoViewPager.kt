package com.example.happyghost.showtimeforkotlin.wegit

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * @author Zhao Chenping
 * @creat 2017/9/13.
 * @description
 */
class PhotoViewPager @JvmOverloads constructor(context: Context ,attrs: AttributeSet? = null) : ViewPager(context,attrs) {
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        try {
            return super.onTouchEvent(ev)
        } catch (ex: IllegalArgumentException) {
            ex.printStackTrace()
        }

        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        try {
            return super.onInterceptTouchEvent(ev)
        } catch (ex: IllegalArgumentException) {
            ex.printStackTrace()
        }

        return false
    }
}