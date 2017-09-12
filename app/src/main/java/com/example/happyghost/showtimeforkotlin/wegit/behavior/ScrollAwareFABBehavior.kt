package com.example.happyghost.showtimeforkotlin.wegit.behavior

import android.content.Context
import android.os.Build
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams

/**
 * @author Zhao Chenping
 * @creat 2017/9/9.
 * @description
 * 这个support包在25.1.1及以上版本都会使浮标的动画效果消失
 * 这是因为在25SDK中CoordinatorLayout的onNestedScroll()这个方法中for循环里面添加了一个判断
 *if (view.getVisibility() == GONE) {
 * If the child is GONE, skip...
 *continue;
 *}直接使用hide()方法或者直接设置View的状态为GONE的话，都会是该条件判断成立，从而跳出for循环，后面Behavior就没法响应事件了
 * 但我们可以设置FloatingActionButton在动画结束的时候的状态为INVISIBLE，就可以解决
 */
class ScrollAwareFABBehavior : FloatingActionButton.Behavior{
    var isAnimationOut :Boolean=false
    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet){
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout?, child: FloatingActionButton?,
                                     directTargetChild: View?, target: View?, nestedScrollAxes: Int): Boolean {
        return nestedScrollAxes==ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout?, child: FloatingActionButton?,
                                target: View?, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
         if(dyConsumed>0&&!isAnimationOut&&child?.visibility==View.VISIBLE){
              animatorOut(child)
         }else if(dyConsumed<0&&child?.visibility!=View.VISIBLE){
             animatorIn(child)
         }
    }
    fun animatorOut(child: FloatingActionButton?) {
        if(Build.VERSION.SDK_INT>=14){
            ViewCompat.animate(child).translationY((child?.height!!.plus(getMarginBottom(child))).toFloat())
                    .setInterpolator(FastOutSlowInInterpolator())
                    .withLayer()
                    .setListener(object :ViewPropertyAnimatorListener{
                        override fun onAnimationEnd(view: View?) {
                            isAnimationOut=false
                            child.visibility = View.INVISIBLE//这里不要设置为GONE，不然会取消FloatingActionButton的联动效果
                        }

                        override fun onAnimationCancel(view: View?) {
                            isAnimationOut=false
                        }

                        override fun onAnimationStart(view: View?) {
                            isAnimationOut = true
                        }
                    })
                    .start()
        }
    }
    fun animatorIn(child: FloatingActionButton?) {
        child?.visibility=View.VISIBLE
        if(Build.VERSION.SDK_INT>=14){
            ViewCompat.animate(child).translationY(0f)
                    .setInterpolator(FastOutSlowInInterpolator())
                    .withLayer()
                    .setListener(null)
                    .start()

        }

    }
    fun getMarginBottom(child: View?): Int {
        var marginBottom :Int = 0
        val layoutParams = child?.layoutParams
        if (layoutParams is ViewGroup.MarginLayoutParams) {
            marginBottom = layoutParams.bottomMargin
        }
        return marginBottom
    }

}