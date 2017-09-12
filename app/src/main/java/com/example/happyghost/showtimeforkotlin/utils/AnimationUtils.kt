package com.example.happyghost.showtimeforkotlin.utils

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateInterpolator

/**
 * @author Zhao Chenping
 * @creat 2017/9/12.
 * @description
 */
class AnimationUtils {
    companion object {
        /**
         * 裁剪视图宽度
         * @param view
         * @param srcHeight
         * @param endHeight
         * @param duration
         */
        fun doClipViewHeight(view: View,srcHeight:Int,endHeight:Int,duration:Int){
            val valueAnimator = ValueAnimator.ofInt(srcHeight, endHeight)
            valueAnimator.setDuration(duration.toLong())
            valueAnimator.addUpdateListener {
                val value = it.animatedValue
                val layoutParams = view.layoutParams
                layoutParams.height = value as Int
                view.layoutParams = layoutParams
            }
            valueAnimator.interpolator = AccelerateInterpolator()
            valueAnimator.start()
        }

        /**
         * 动画是否在运行
         * @param animator
         */
        fun isRunning(animator: Animator?): Boolean {
            return animator != null && animator.isRunning
        }

        /**
         * 垂直偏移动画
         * @param view
         * @param startY
         * @param endY
         * @param duration
         * @return
         */
        fun doMoveVertical(view: View, startY: Float, endY: Float, duration: Int): Animator {
            val animator = ObjectAnimator.ofFloat(view, "translationY", startY, endY).setDuration(duration.toLong())
            animator.start()
            return animator
        }

        /**
         * 停止动画
         * @param animator
         */
        fun stopAnimator(animator: Animator?) {
            if (animator != null && animator.isRunning) {
                animator.cancel()
            }
        }
    }
}