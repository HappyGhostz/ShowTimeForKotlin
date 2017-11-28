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
        fun formatChapterBody(body:String):String{
            var cont=body
            cont = cont.replace("[ ]*".toRegex(), "")//替换来自服务器上的，特殊空格
            cont = cont.replace("[ ]*".toRegex(), "")//
            cont = cont.replace("\n\n", "\n")
            cont = cont.replace("\n", "\n" + getTwoSpaces())
            cont = getTwoSpaces() + cont
//        str = convertToSBC(str);
            return cont
        }

        /**
         * Return a String that only has two spaces.
         *
         * @return
         */
        fun getTwoSpaces(): String {
            return "\u3000\u3000"
        }

        /**
         * 获取当前日期的指定格式的字符串
         *
         * @param format 指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:mm:ss.SSS"
         * @return
         */
        fun getCurrentTimeString(format: String?): String {
            if (format == null || format.trim { it <= ' ' } == "") {
                mTimeFormat.applyPattern(FORMAT_DATE_TIME)
            } else {
                mTimeFormat.applyPattern(format)
            }
            return mTimeFormat.format(Date())
        }

        /**
         * 把毫秒值格式化成 hh:mm:ss格式
         * @param millisec 毫秒值
         * @return
         */
        fun formatTime(millisec: Int): String? {
            var result: String? = null
            val hour = 1000 * 60 * 60
            val minute = 1000 * 60
            val second = 1000

            //算出 小时h 分钟 min 秒 s
            val h = millisec / hour
            val min = millisec % hour / minute
            val s = millisec % minute / second

            if (h > 0) {
                //%d 整数的占位符 如果前面加上02说明要显示两位数 如果不足两位用0补齐
                //后面是可变参数 有一个%d对应几个变量 按照顺序依次对应
                result = String.format("%02d:%02d:%02d", h, min, s)
            } else {
                result = String.format("%02d:%02d", min, s)
            }
            return result
        }

    }
}