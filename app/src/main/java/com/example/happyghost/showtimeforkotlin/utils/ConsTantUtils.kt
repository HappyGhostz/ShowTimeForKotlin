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
        //http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.search.common&query=%E9%81%87%E8%A7%81&page_size=30&page_no=1&format=xml
        val MUSIC_URL_METHOD_SONG_SEARCH="baidu.ting.search.common"
        val pageSize = 40
        val startPage = 0

        //播放的模式
        val LIST_MODE = 0
        val SINGO_MODE = 1
        val SHUFFLE_MODE = 2
        val MUSIC_MODE = "MUSICMODE"

        val CURRENT_MILL_TIME = "currentstime"
        val CURRENT_MILL_TIME_FUNNY = "currentstimefunny"
        //段子
        val CROSS_TYPE=-102
        //图片
        val CROSS_PICTURE_URL="http://pb9.pstatp.com/"
        var CROSS_PICTURE=-103

    }

}