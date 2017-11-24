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
        val SUFFIX_TXT = ".txt"
        val SUFFIX_PDF = ".pdf"
        val SUFFIX_EPUB = ".epub"
        val SUFFIX_ZIP = ".zip"
        val SUFFIX_CHM = ".chm"

        val MUSIC_URL_FROM = "webapp_music"
        val MUSIC_URL_FORMAT = "json"
        val MUSIC_URL_METHOD_GEDAN = "baidu.ting.diy.gedan"
        val MUSIC_URL_METHOD_RANKINGLIST = "baidu.ting.billboard.billCategory"
        val MUSIC_URL_RANKINGLIST_FLAG = 1
        val MUSIC_URL_METHOD_SONGLIST_DETAIL = "baidu.ting.diy.gedanInfo"
        val MUSIC_URL_METHOD_RANKING_DETAIL = "baidu.ting.billboard.billList"
        val MUSIC_URL_FROM_2 = "android"
        val MUSIC_URL_VERSION = "5.6.5.6"
        val MUSIC_URL_METHOD_SONG_DETAIL = "baidu.ting.song.play"
        val pageSize = 40
        val startPage = 0
    }

}