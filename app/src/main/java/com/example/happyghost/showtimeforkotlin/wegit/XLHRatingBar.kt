package com.example.happyghost.showtimeforkotlin.wegit

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.utils.ScreenUtils

/**
 * @author Zhao Chenping
 * @creat 2017/11/16.
 * @description
 */
class XLHRatingBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {
    private var countNum: Int = 0
    private var countSelected: Int = 0
    private var stateResId: Int = 0
    private val widthAndHeight: Float
    private val dividerWidth: Float
    private val canEdit: Boolean
    private val differentSize: Boolean

    var onRatingChangeListener: OnRatingChangeListener? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.XlHRatingBar)
        countNum = typedArray.getInt(R.styleable.XlHRatingBar_starCount, 5)
        countSelected = typedArray.getInt(R.styleable.XlHRatingBar_countSelected, 0)
        canEdit = typedArray.getBoolean(R.styleable.XlHRatingBar_canEdit, false)
        differentSize = typedArray.getBoolean(R.styleable.XlHRatingBar_differentSize, false)
        widthAndHeight = typedArray.getDimension(R.styleable.XlHRatingBar_widthAndHeight, ScreenUtils.dpToPxInt(0f).toFloat())
        dividerWidth = typedArray.getDimension(R.styleable.XlHRatingBar_dividerWidth, ScreenUtils.dpToPxInt(0f).toFloat())
        stateResId = typedArray.getResourceId(R.styleable.XlHRatingBar_stateResId, -1)
        initView()
    }

    fun getCountNum(): Int {
        return countNum
    }

    fun setCountNum(countNum: Int) {
        this.countNum = countNum
        initView()
    }

    fun getCountSelected(): Int {
        return countSelected
    }

    fun setCountSelected(countSelected: Int) {
        if (countSelected > countNum) {
            return
        }
        this.countSelected = countSelected
        initView()
    }


    private fun initView() {
        removeAllViews()
        for (i in 0 until countNum) {
            val cb = CheckBox(context)
            val layoutParams: LinearLayout.LayoutParams
            if (widthAndHeight == 0f) {
                layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            } else {
                layoutParams = LinearLayout.LayoutParams(widthAndHeight.toInt(), widthAndHeight.toInt())
            }
            if (differentSize && countNum % 2 != 0) {
                Log.e("xxx", layoutParams.width.toString() + "")
                var index = i
                if (index > countNum / 2) {
                    index = countNum - 1 - index
                }
                val scale = (index + 1) / (countNum / 2 + 1).toFloat()
                layoutParams.width = (layoutParams.width * scale).toInt()
                layoutParams.height = layoutParams.width
            }
            layoutParams.gravity = Gravity.CENTER_VERTICAL
            if (i != 0 && i != countNum - 1) {
                layoutParams.leftMargin = dividerWidth.toInt()
                layoutParams.rightMargin = dividerWidth.toInt()
            } else if (i == 0) {
                layoutParams.rightMargin = dividerWidth.toInt()
            } else if (i == countNum - 1) {
                layoutParams.leftMargin = dividerWidth.toInt()
            }
            addView(cb, layoutParams)
            cb.buttonDrawable = ColorDrawable(resources.getColor(android.R.color.transparent))
            if (stateResId == -1) {
                stateResId = R.drawable.book_review_rating_bar_selector
            }
            cb.setBackgroundResource(stateResId)
            if (i + 1 <= countSelected) {
                cb.isChecked = true
            }
            cb.isEnabled = canEdit
            cb.setOnClickListener(MyClickListener(i))
        }

    }

    private inner class MyClickListener(internal var position: Int) : View.OnClickListener {

        override fun onClick(v: View) {
            countSelected = position + 1

            for (i in 0 until countNum) {
                val cb = getChildAt(i) as CheckBox

                if (i <= position) {
                    cb.isChecked = true
                } else if (i > position) {
                    cb.isChecked = false
                }
            }
            if (onRatingChangeListener != null) {
                onRatingChangeListener!!.onChange(countSelected)
            }
        }

    }

    interface OnRatingChangeListener {
        fun onChange(countSelected: Int)
    }
}
