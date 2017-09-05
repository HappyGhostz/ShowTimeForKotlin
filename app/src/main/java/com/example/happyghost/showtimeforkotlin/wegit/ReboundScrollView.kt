package com.example.happyghost.showtimeforkotlin.wegit

import android.content.Context
import android.graphics.Rect
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.ScrollView

/**
 * @author Zhao Chenping
 * @creat 2017/9/4.
 * @description
 */
class ReboundScrollView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1) : ScrollView(context,attrs,defStyleAttr) {

    //ScrollView中的唯一子控件
    lateinit var contentView:View
    //记录子View的原始位置
    var rect :Rect = Rect()
    //手指按下时的Y位置
    var downY :Float = 0.0f
    var moveY:Float = 0.0f
    //手指按下时是否可以上拉或下拉
    var canPullDown:Boolean = false
    var canPullUp:Boolean = false
    //移动百分比，延迟效果
    var MOVE_FACTOR:Float = 0.5f
    //是否移动
    var isMove:Boolean = false
    //动画时间
    var ANIMAT_TIME :Long = 300

    override fun onFinishInflate() {
        if(childCount>0){
            contentView =getChildAt(0)
        }
    }
    /**
     * 在该方法中获取ScrollView中的唯一子控件的位置信息 这个位置信息在整个控件的生命周期中保持不变
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if(contentView==null){
            return
        }
        rect.set(contentView.left,contentView.top,contentView.right,contentView.bottom)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if(contentView==null){
            return super.dispatchTouchEvent(ev)
        }
        when(ev?.action){
            MotionEvent.ACTION_DOWN->{
                downY = ev.y
                canPullDown = isPullDown()
                canPullUp = isPullUp()
            }
            MotionEvent.ACTION_MOVE-> run {
                // 在移动的过程中， 既没有滚动到可以上拉的程度， 也没有滚动到可以下拉的程度
                if(!canPullDown&&!canPullUp){
                    downY = ev.y
                    canPullUp = isPullUp()
                    canPullDown = isPullDown()
                    return@run
                }
                moveY = ev.y
                val dansY =(moveY - downY) .toInt()
                // 是否应该移动布局
                var shouldMove :Boolean = (canPullDown&&dansY>0)//可以下拉并且手指正在下移
                || (canPullUp&&dansY<0)//可以上拉并且手指正在上移
                ||(canPullDown&&canPullUp) // 既可以上拉也可以下拉（这种情况出现在ScrollView包裹的控件比ScrollView还小）
                if(shouldMove){
                    //布局偏移量
                    val offset = (dansY * MOVE_FACTOR).toInt()
                    //移动布局
                    contentView.layout(rect.left,rect.top+offset,rect.right,rect.bottom+offset)
                    isMove = true
                }
            }
            MotionEvent.ACTION_UP-> run {
                if(!isMove){
                    return@run
                }
                var transAnimation = TranslateAnimation(0.0F,0.0f,contentView.top.toFloat(),rect.top.toFloat())
                transAnimation.duration = ANIMAT_TIME
                transAnimation.start()
                contentView.layout(rect.left,rect.top,rect.right,rect.bottom)
                //回归标志位
                canPullDown=false
                canPullUp=false
                isMove=false
            }

        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 是否下拉到顶部
     */
    fun isPullDown() : Boolean{
        return scrollY == 0 || contentView.height<height+scaleY
    }
    /**
     * 是否下拉到底部
     */
    fun isPullUp():Boolean{
        return contentView.height<=height+scrollY
    }
}