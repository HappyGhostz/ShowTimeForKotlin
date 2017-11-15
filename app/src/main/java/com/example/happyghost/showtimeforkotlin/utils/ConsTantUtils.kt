package com.example.happyghost.showtimeforkotlin.utils

import android.graphics.Color
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * @author Zhao Chenping
 * @creat 2017/9/7.
 * @description
 */
class ConsTantUtils {
    companion object {
        var showImageKey :String = "imagekey"
        var SHOW_POPUP_DETAIL :String = "showpopupdetial"
        val IMG_BASE_URL = "http://statics.zhuishushenqi.com"
        val START_PAGE = 0
        val LIMIT_POSITION = 20
        val TYPE_LEVEL_0 = 0
//        val TYPE_LEVEL_1 = 1
        val TYPE_BOOK_RANK = 1

        val FLIP_STYLE = "flipStyle"
        val ISNIGHT = "isNight"
        val tagColors = intArrayOf(Color.parseColor("#90C5F0"), Color.parseColor("#91CED5"),
                Color.parseColor("#F88F55"), Color.parseColor("#C0AFD0"), Color.parseColor("#E78F8F"),
                Color.parseColor("#67CCB7"), Color.parseColor("#F6BC7E"))
    }

}