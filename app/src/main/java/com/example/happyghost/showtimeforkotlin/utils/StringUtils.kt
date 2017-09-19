package com.example.happyghost.showtimeforkotlin.utils

import android.text.TextUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Zhao Chenping
 * @creat 2017/9/7.
 * @description
 */
class StringUtils {
    companion object {
        var mTimeFormat = SimpleDateFormat()
        val FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss.SSS"
        private val ONE_MINUTE = 60000L
        private val ONE_HOUR = 3600000L
        private val ONE_DAY = 86400000L
        private val ONE_WEEK = 604800000L

        private val ONE_SECOND_AGO = "秒前"
        private val ONE_MINUTE_AGO = "分钟前"
        private val ONE_HOUR_AGO = "小时前"
        private val ONE_DAY_AGO = "天前"
        private val ONE_MONTH_AGO = "月前"
        private val ONE_YEAR_AGO = "年前"

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
        /**
         * 获取列表缓存Key
         * @param parms
         * @return
         */
        fun creatAcacheKey(vararg parms:Any):String{
            var key = ""
            for (parm in parms){
                key+="-" + parm
            }
            return key.replaceFirst("-".toRegex(), "")
        }
        /**
         * 根据时间字符串获取描述性时间，如3分钟前，1天前
         *
         * @param dateString 时间字符串
         * @return
         */
        fun getDescriptionTimeFromDateString(dateString:String?):String{
            if(dateString?.isEmpty()!!){
                return ""
            }
            mTimeFormat.applyPattern(FORMAT_DATE_TIME)
            try {
                return getDescriptionTimeFromDate(mTimeFormat.parse(formatZhuiShuDateString(dateString)))
            }catch (e:Exception){
                e.printStackTrace()
            }
            return ""
        }

        private fun getDescriptionTimeFromDate(parse: Date?): String {
            val delta = Date().time - parse?.getTime()!!
            if (delta < 1L * ONE_MINUTE) {
                val seconds = toSeconds(delta)
                return (if (seconds <= 0) 1 else seconds).toString() + ONE_SECOND_AGO
            }
            if (delta < 45L * ONE_MINUTE) {
                val minutes = toMinutes(delta)
                return (if (minutes <= 0) 1 else minutes).toString() + ONE_MINUTE_AGO
            }
            if (delta < 24L * ONE_HOUR) {
                val hours = toHours(delta)
                return (if (hours <= 0) 1 else hours).toString() + ONE_HOUR_AGO
            }
            if (delta < 48L * ONE_HOUR) {
                return "昨天"
            }
            if (delta < 30L * ONE_DAY) {
                val days = toDays(delta)
                return (if (days <= 0) 1 else days).toString() + ONE_DAY_AGO
            }
            if (delta < 12L * 4L * ONE_WEEK) {
                val months = toMonths(delta)
                return (if (months <= 0) 1 else months).toString() + ONE_MONTH_AGO
            } else {
                val years = toYears(delta)
                return (if (years <= 0) 1 else years).toString() + ONE_YEAR_AGO
            }
        }

        fun formatZhuiShuDateString(dateString: String): String? {
            return dateString.replace("T", " ").replace("Z", "")
        }

        private fun toSeconds(date: Long): Long {
            return date / 1000L
        }

        private fun toMinutes(date: Long): Long {
            return toSeconds(date) / 60L
        }

        private fun toHours(date: Long): Long {
            return toMinutes(date) / 60L
        }

        private fun toDays(date: Long): Long {
            return toHours(date) / 24L
        }

        private fun toMonths(date: Long): Long {
            return toDays(date) / 30L
        }

        private fun toYears(date: Long): Long {
            return toMonths(date) / 365L
        }

        fun formatWordCount(wordCount: Int): String {
            return if (wordCount / 10000 > 0) {
                (wordCount / 10000f + 0.5).toInt().toString() + "万字"
            } else if (wordCount / 1000 > 0) {
                (wordCount / 1000f + 0.5).toInt().toString() + "千字"
            } else {
                wordCount.toString() + "字"
            }
        }
    }
}