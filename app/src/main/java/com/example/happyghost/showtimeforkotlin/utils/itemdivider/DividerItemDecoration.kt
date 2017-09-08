package com.example.happyghost.showtimeforkotlin.utils.itemdivider

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.LayoutParams

/**
 * @author Zhao Chenping
 * @creat 2017/9/6.
 * @description
 */
class DividerItemDecoration(): RecyclerView.ItemDecoration() {
    private val ATTRS = intArrayOf(android.R.attr.listDivider)
    lateinit var mDivider:Drawable
    var mOrientation:Int = 1

    /**
     * context:上下文
     * orientation:recycleView的横竖布局
     */
    constructor(context: Context,orientation:Int) : this() {
        val typedArray = context.obtainStyledAttributes(ATTRS)
        mDivider = typedArray.getDrawable(0)
        typedArray.recycle()
        setOrientation(orientation)
    }
    fun setOrientation(orientation: Int){
        if(orientation!=LinearLayoutManager.VERTICAL&&orientation!=LinearLayoutManager.HORIZONTAL){
            throw IllegalArgumentException("not init orientation")
        }
        mOrientation = orientation
    }
    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)
        if(mOrientation==LinearLayoutManager.HORIZONTAL){
            drawVertical(c,parent)
        }else if (mOrientation==LinearLayoutManager.VERTICAL){
            drawHorizontal(c,parent)
        }
    }
    fun drawHorizontal(c: Canvas?,parent: RecyclerView?){
        val top = parent?.paddingTop
        val bottom = parent?.height!! - parent?.paddingBottom

        val childCount = parent?.childCount
        if (childCount != null) {
            for (index in 0..childCount){
                val childView = parent.getChildAt(index)
                val params = parent.layoutParams as LayoutParams
                var lift = childView.right+params.rightMargin
                var right = lift+mDivider.intrinsicHeight
                mDivider.setBounds(lift, top!!,right,bottom)
                mDivider.draw(c)

            }
        }
    }
    fun drawVertical(c: Canvas?,parent: RecyclerView?){
        val left = parent?.paddingLeft
        val right = parent?.width!! - parent?.paddingRight
        val childCount = parent.childCount
        for (index in 0..childCount){
            val view = parent.getChildAt(index)
            val params = parent.layoutParams as LayoutParams
            var top = view.bottom+params.bottomMargin
            var bottom = top+mDivider.intrinsicHeight
            mDivider.setBounds(left!!,top,right,bottom)
            mDivider.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect?, itemPosition: Int, parent: RecyclerView?) {
        super.getItemOffsets(outRect, itemPosition, parent)
        if(mOrientation==LinearLayoutManager.VERTICAL){
            outRect?.set(0,0,0,mDivider.intrinsicHeight)
        }else if(mOrientation==LinearLayoutManager.HORIZONTAL){
            outRect?.set(0,0,mDivider.intrinsicWidth,0)
        }
    }
}