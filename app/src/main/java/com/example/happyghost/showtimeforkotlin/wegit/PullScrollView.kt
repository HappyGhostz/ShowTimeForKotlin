package com.example.happyghost.showtimeforkotlin.wegit

import android.content.Context
import android.support.v4.view.MotionEventCompat
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.utils.AnimationUtils

/**
 * @author Zhao Chenping
 * @creat 2017/9/12.
 * @description
 */
class PullScrollView : NestedScrollView {


    var isPullStatus:Boolean=false
    var mLastY :Float = 0.0f
    var mPullDistance:Int = 0
    lateinit var mPullListener:OnPullListener
    lateinit var mFootView :View
    constructor(context: Context) :this(context,null){}
    constructor(context: Context,attributeSet: AttributeSet?):super(context,attributeSet){
        mPullDistance = resources.getDimensionPixelSize(R.dimen.pull_critical_distance)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if(t>=(getChildAt(0).measuredHeight-height)&& mPullListener!=null){
            mPullListener.isDoPull()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        when(MotionEventCompat.getActionMasked(ev)){
            MotionEvent.ACTION_MOVE->
                    if(!isPullStatus){
                        if(scrollY>=(getChildAt(0).measuredHeight-height)||getChildAt(0).measuredHeight<height){
                            if(mPullListener!=null&&mPullListener.isDoPull()){
                                isPullStatus =true
                                mLastY = ev!!.y
                            }
                        }
                    }else if(mLastY<ev!!.y){
                        isPullStatus=false
                        pullFootView(0.0f)
                    }else{
                        val offset = mLastY - ev.y
                        pullFootView(offset)
                    }
            MotionEvent.ACTION_UP,MotionEvent.ACTION_CANCEL->
                    if(isPullStatus){
                        if(mFootView.height>mPullDistance&&mPullListener!=null){
                            if(!mPullListener.handlePull()){
                                AnimationUtils.doClipViewHeight(mFootView,mFootView.height,0,200)
                            }
                        }else{
                            AnimationUtils.doClipViewHeight(mFootView,mFootView.height,0,200)
                        }
                        isPullStatus=false
                    }

        }
        return super.onTouchEvent(ev)
    }
    fun setFootView(view:View){
        mFootView = view
    }
    fun pullFootView(offset:Float){
        if(mFootView!=null){
            val layoutParams = mFootView.layoutParams
            layoutParams.height = (offset*1/2).toInt()
            mFootView.layoutParams = layoutParams
        }
    }
    fun setOnPullListener(onPullListener: OnPullListener){
        this.mPullListener = onPullListener
    }
    public interface OnPullListener{
        fun isDoPull():Boolean
        fun handlePull():Boolean
    }
}