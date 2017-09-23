package com.example.happyghost.showtimeforkotlin.utils.itemdivider

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.LayoutParams
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View

/**
 * @author Zhao Chenping
 * @creat 2017/9/19.
 * @description
 */
class DividerGridItemDecoration(mContext: Context?) : RecyclerView.ItemDecoration() {
    private val ATTRS = intArrayOf(android.R.attr.listDivider)
    private var mDivider: Drawable?=null
   init {
       val typedArray = mContext?.obtainStyledAttributes(ATTRS)
       mDivider = typedArray?.getDrawable(0)
       typedArray?.recycle()
   }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)
        drawHorizontal(c,parent)
        drawVertical(c,parent)
    }

    private fun drawVertical(c: Canvas?, parent: RecyclerView?) {
        val childCount = parent?.childCount
            for (i in 0..childCount!! - 1) {
                val childAt = parent.getChildAt(i)
                val params = childAt.layoutParams as LayoutParams
                val left = childAt.left - params.leftMargin
                val right = childAt.right + params.rightMargin+mDivider!!.intrinsicWidth
                val top = childAt.top + params.topMargin
                val bottom = childAt.bottom + params.bottomMargin + mDivider!!.intrinsicHeight
                mDivider?.setBounds(left,top,right,bottom)
                mDivider?.draw(c)
            }
    }

    private fun drawHorizontal(c: Canvas?, parent: RecyclerView?) {
        val childCount = parent?.childCount
        for (i in 0..childCount!! -1){
            val view = parent.getChildAt(i)
            val params = view.layoutParams as LayoutParams
            val left = view.right + params.rightMargin
            val right = left + mDivider!!.intrinsicWidth
            val top = view.top - params.topMargin
            val bootom = view.bottom + params.bottomMargin
            mDivider?.setBounds(left,top,right,bootom)
            mDivider?.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect?, itemPosition: Int, parent: RecyclerView?) {
        val spanCount = getSpanCount(parent)
        val itemCount = parent?.adapter?.itemCount

        if(isLastRaw(parent, itemPosition, spanCount, itemCount))// 如果是最后一行，则不需要绘制底部
        {
            outRect?.set(0, 0, mDivider!!.intrinsicWidth, 0)
        }else if(isLastColum(parent, itemPosition, spanCount, itemCount)){// 如果是最后一列，则不需要绘制右边
            outRect?.set(0, 0,0, mDivider!!.intrinsicHeight)
        }else{
            outRect?.set(0, 0,mDivider!!.intrinsicWidth, mDivider!!.intrinsicHeight)
        }
    }

    private fun isLastColum(parent: RecyclerView?, itemPosition: Int, spanCount: Int, itemCount: Int?): Boolean {
        val layoutManager = parent?.layoutManager
        if(layoutManager is GridLayoutManager){
            if((itemPosition+1)% spanCount==0){// 如果是最后一列，则不需要绘制右边
                return true
            }else if(layoutManager is StaggeredGridLayoutManager){
                val orientation = (layoutManager as StaggeredGridLayoutManager).orientation
                if(orientation == StaggeredGridLayoutManager.VERTICAL){
                    if ((itemPosition + 1) % spanCount == 0)
                    // 如果是最后一列，则不需要绘制右边
                    {
                        return true
                    }
                }
            }else{
                val childCount = itemCount!! - itemCount!! % spanCount
                if (itemPosition >= childCount)
                // 如果是最后一列，则不需要绘制右边
                    return true
            }
        }
        return false
    }

    private fun isLastRaw(parent: RecyclerView?, itemPosition: Int, spanCount: Int, itemCount: Int?): Boolean {
        val layoutManager = parent?.layoutManager
        if(layoutManager is GridLayoutManager){
            var childCount = itemCount!! -itemCount%spanCount
            if(itemPosition>=childCount){// 如果是最后一行，则不需要绘制底部
                return true
            }
        }else if (layoutManager is StaggeredGridLayoutManager){
            val orientation = (layoutManager as StaggeredGridLayoutManager).orientation
            if(orientation==StaggeredGridLayoutManager.VERTICAL){
                var childCount = itemCount!! -itemCount%spanCount
                if(itemPosition>childCount){// 如果是最后一行，则不需要绘制底部
                    return true
                }
            }
        }else{
            // 如果是最后一行，则不需要绘制底部
            if ((itemPosition + 1) % spanCount == 0) {
                return true
            }
        }
        return false
    }

    private fun getSpanCount(parent: RecyclerView?): Int{
        var spanCount=-1
        val layoutManager = parent?.layoutManager
        if(layoutManager is GridLayoutManager){
            spanCount = layoutManager.spanCount

        }else if(layoutManager is StaggeredGridLayoutManager){
            spanCount = layoutManager.spanCount
        }
        return spanCount
    }
}