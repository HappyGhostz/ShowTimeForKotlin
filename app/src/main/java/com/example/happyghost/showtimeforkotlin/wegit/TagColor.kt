package com.example.happyghost.showtimeforkotlin.wegit

import android.graphics.Color
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import java.util.ArrayList

/**
 * @author Zhao Chenping
 * @creat 2017/11/15.
 * @description
 */
class TagColor {
    var borderColor = Color.parseColor("#49C120")
    var backgroundColor = Color.parseColor("#49C120")
    var textColor = Color.WHITE
    companion object {
        fun getRandomColors(size: Int): List<TagColor> {

            val list = ArrayList<TagColor>()
            for (i in 0 until size) {
                val color = TagColor()
                color.backgroundColor = ConsTantUtils.tagColors[i % ConsTantUtils.tagColors.size]
                color.borderColor = color.backgroundColor
                list.add(color)
            }
            return list
        }
    }

}