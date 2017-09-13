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
        /**
         * 裁剪图集ID
         *
         * @param photoId
         * @return
         */
        fun clipPhotoSetId(photoId: String):String?{
            if (TextUtils.isEmpty(photoId)) {
                return photoId
            }
            val i = photoId.indexOf("|")
            if (i >= 4) {
                val result = photoId.replace('|', '/')
                return result.substring(i - 4)
            }
            return null
        }

    }
}