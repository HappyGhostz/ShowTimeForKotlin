package com.example.happyghost.showtimeforkotlin.utils

import android.text.TextUtils

/**
 * @author Zhao Chenping
 * @creat 2017/9/7.
 * @description
 */
class StringUtils {
    companion object {

        /**
         * 裁剪新闻的 Source 数据
         *
         * @param source
         * @return
         */
        fun clipNewsSource(source:String):String{
            if(TextUtils.isEmpty(source)){
                return source
            }
            val indexOf = source.indexOf("-")
            if(indexOf!=-1){
               return source.substring(0,indexOf)
            }
            return source
        }

    }
}