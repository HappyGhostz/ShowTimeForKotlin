package com.example.happyghost.showtimeforkotlin.bean.musicdate

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by e445 on 2017/11/24.
 */
class SongDetailInfo() :Parcelable {
    /**
     * songinfo : {"special_type":0,"pic_huge":"","resource_type":"0","pic_premium":"http://musicdata.baidu.com/data2/pic/f8f2bac1a7444d6385d9e16f57e8afb5/261991690/261991690.jpg","havehigh":2,"author":"杨钰莹","toneid":"600902000007964201","has_mv":1,"song_id":"2121687","piao_id":"0","artist_id":"999","lrclink":"http://musicdata.baidu.com/data2/lrc/242441098/242441098.lrc","relate_status":"0","learn":1,"pic_big":"http://musicdata.baidu.com/data2/pic/f8f2bac1a7444d6385d9e16f57e8afb5/261991690/261991690.jpg@s_0,w_150","play_type":0,"album_id":"2121650","album_title":"也是情歌精选","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","song_source":"web","all_artist_id":"999","all_artist_ting_uid":"1451","all_rate":"31,64,128,192,256,320,flac","charge":0,"copy_type":"0","is_first_publish":0,"korean_bb_song":"0","pic_radio":"http://musicdata.baidu.com/data2/pic/f8f2bac1a7444d6385d9e16f57e8afb5/261991690/261991690.jpg@s_0,w_300","has_mv_mobile":0,"title":"轻轻地告诉你","pic_small":"http://musicdata.baidu.com/data2/pic/f8f2bac1a7444d6385d9e16f57e8afb5/261991690/261991690.jpg@s_0,w_90","album_no":"19","resource_type_ext":"0","ting_uid":"1451"}
     * error_code : 22000
     * bitrate : {"show_link":"http://zhangmenshiting.baidu.com/data2/music/134347684/134347684.mp3?xcode=294ea175cde8d4e6c6826868de970894","free":1,"song_file_id":66185015,"file_size":2244272,"file_extension":"mp3","file_duration":280,"file_bitrate":64,"file_link":"http://yinyueshiting.baidu.com/data2/music/134347684/134347684.mp3?xcode=294ea175cde8d4e6c6826868de970894","hash":"1203b61337d84097ba8f5b5591d107ec2b13c01e"}
     */

    var songinfo: SonginfoBean? = null
    var error_code: Int = 0
    var bitrate: BitrateBean? = null
    var isOnClick = false

    constructor(parcel: Parcel) : this() {
        songinfo = parcel.readParcelable(SonginfoBean::class.java.classLoader)
        error_code = parcel.readInt()
        bitrate = parcel.readParcelable(BitrateBean::class.java.classLoader)
        isOnClick = parcel.readByte() != 0.toByte()
    }


    class SonginfoBean() : Parcelable {
        /**
         * special_type : 0
         * pic_huge :
         * resource_type : 0
         * pic_premium : http://musicdata.baidu.com/data2/pic/f8f2bac1a7444d6385d9e16f57e8afb5/261991690/261991690.jpg
         * havehigh : 2
         * author : 杨钰莹
         * toneid : 600902000007964201
         * has_mv : 1
         * song_id : 2121687
         * piao_id : 0
         * artist_id : 999
         * lrclink : http://musicdata.baidu.com/data2/lrc/242441098/242441098.lrc
         * relate_status : 0
         * learn : 1
         * pic_big : http://musicdata.baidu.com/data2/pic/f8f2bac1a7444d6385d9e16f57e8afb5/261991690/261991690.jpg@s_0,w_150
         * play_type : 0
         * album_id : 2121650
         * album_title : 也是情歌精选
         * bitrate_fee : {"0":"0|0","1":"0|0"}
         * song_source : web
         * all_artist_id : 999
         * all_artist_ting_uid : 1451
         * all_rate : 31,64,128,192,256,320,flac
         * charge : 0
         * copy_type : 0
         * is_first_publish : 0
         * korean_bb_song : 0
         * pic_radio : http://musicdata.baidu.com/data2/pic/f8f2bac1a7444d6385d9e16f57e8afb5/261991690/261991690.jpg@s_0,w_300
         * has_mv_mobile : 0
         * title : 轻轻地告诉你
         * pic_small : http://musicdata.baidu.com/data2/pic/f8f2bac1a7444d6385d9e16f57e8afb5/261991690/261991690.jpg@s_0,w_90
         * album_no : 19
         * resource_type_ext : 0
         * ting_uid : 1451
         */

        var special_type: Int = 0
        var pic_huge: String? = null
        var resource_type: String? = null
        var pic_premium: String? = null
        var havehigh: Int = 0
        var author: String? = null
        var toneid: String? = null
        var has_mv: Int = 0
        var song_id: String? = null
        var piao_id: String? = null
        var artist_id: String? = null
        var lrclink: String? = null
        var relate_status: String? = null
        var learn: Int = 0
        var pic_big: String? = null
        var play_type: Int = 0
        var album_id: String? = null
        var album_title: String? = null
        var bitrate_fee: String? = null
        var song_source: String? = null
        var all_artist_id: String? = null
        var all_artist_ting_uid: String? = null
        var all_rate: String? = null
        var charge: Int = 0
        var copy_type: String? = null
        var is_first_publish: Int = 0
        var korean_bb_song: String? = null
        var pic_radio: String? = null
        var has_mv_mobile: Int = 0
        var title: String? = null
        var pic_small: String? = null
        var album_no: String? = null
        var resource_type_ext: String? = null
        var ting_uid: String? = null

        constructor(parcel: Parcel) : this() {
            special_type = parcel.readInt()
            pic_huge = parcel.readString()
            resource_type = parcel.readString()
            pic_premium = parcel.readString()
            havehigh = parcel.readInt()
            author = parcel.readString()
            toneid = parcel.readString()
            has_mv = parcel.readInt()
            song_id = parcel.readString()
            piao_id = parcel.readString()
            artist_id = parcel.readString()
            lrclink = parcel.readString()
            relate_status = parcel.readString()
            learn = parcel.readInt()
            pic_big = parcel.readString()
            play_type = parcel.readInt()
            album_id = parcel.readString()
            album_title = parcel.readString()
            bitrate_fee = parcel.readString()
            song_source = parcel.readString()
            all_artist_id = parcel.readString()
            all_artist_ting_uid = parcel.readString()
            all_rate = parcel.readString()
            charge = parcel.readInt()
            copy_type = parcel.readString()
            is_first_publish = parcel.readInt()
            korean_bb_song = parcel.readString()
            pic_radio = parcel.readString()
            has_mv_mobile = parcel.readInt()
            title = parcel.readString()
            pic_small = parcel.readString()
            album_no = parcel.readString()
            resource_type_ext = parcel.readString()
            ting_uid = parcel.readString()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(special_type)
            parcel.writeString(pic_huge)
            parcel.writeString(resource_type)
            parcel.writeString(pic_premium)
            parcel.writeInt(havehigh)
            parcel.writeString(author)
            parcel.writeString(toneid)
            parcel.writeInt(has_mv)
            parcel.writeString(song_id)
            parcel.writeString(piao_id)
            parcel.writeString(artist_id)
            parcel.writeString(lrclink)
            parcel.writeString(relate_status)
            parcel.writeInt(learn)
            parcel.writeString(pic_big)
            parcel.writeInt(play_type)
            parcel.writeString(album_id)
            parcel.writeString(album_title)
            parcel.writeString(bitrate_fee)
            parcel.writeString(song_source)
            parcel.writeString(all_artist_id)
            parcel.writeString(all_artist_ting_uid)
            parcel.writeString(all_rate)
            parcel.writeInt(charge)
            parcel.writeString(copy_type)
            parcel.writeInt(is_first_publish)
            parcel.writeString(korean_bb_song)
            parcel.writeString(pic_radio)
            parcel.writeInt(has_mv_mobile)
            parcel.writeString(title)
            parcel.writeString(pic_small)
            parcel.writeString(album_no)
            parcel.writeString(resource_type_ext)
            parcel.writeString(ting_uid)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<SonginfoBean> {
            override fun createFromParcel(parcel: Parcel): SonginfoBean {
                return SonginfoBean(parcel)
            }

            override fun newArray(size: Int): Array<SonginfoBean?> {
                return arrayOfNulls(size)
            }
        }
    }

    class BitrateBean() : Parcelable {
        /**
         * show_link : http://zhangmenshiting.baidu.com/data2/music/134347684/134347684.mp3?xcode=294ea175cde8d4e6c6826868de970894
         * free : 1
         * song_file_id : 66185015
         * file_size : 2244272
         * file_extension : mp3
         * file_duration : 280
         * file_bitrate : 64
         * file_link : http://yinyueshiting.baidu.com/data2/music/134347684/134347684.mp3?xcode=294ea175cde8d4e6c6826868de970894
         * hash : 1203b61337d84097ba8f5b5591d107ec2b13c01e
         */

        var show_link: String? = null
        var free: Int = 0
        var song_file_id: Int = 0
        var file_size: Int = 0
        var file_extension: String? = null
        var file_duration: Int = 0
        var file_bitrate: Int = 0
        var file_link: String? = null
        var hash: String? = null

        constructor(parcel: Parcel) : this() {
            show_link = parcel.readString()
            free = parcel.readInt()
            song_file_id = parcel.readInt()
            file_size = parcel.readInt()
            file_extension = parcel.readString()
            file_duration = parcel.readInt()
            file_bitrate = parcel.readInt()
            file_link = parcel.readString()
            hash = parcel.readString()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(show_link)
            parcel.writeInt(free)
            parcel.writeInt(song_file_id)
            parcel.writeInt(file_size)
            parcel.writeString(file_extension)
            parcel.writeInt(file_duration)
            parcel.writeInt(file_bitrate)
            parcel.writeString(file_link)
            parcel.writeString(hash)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<BitrateBean> {
            override fun createFromParcel(parcel: Parcel): BitrateBean {
                return BitrateBean(parcel)
            }

            override fun newArray(size: Int): Array<BitrateBean?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(songinfo, flags)
        parcel.writeInt(error_code)
        parcel.writeParcelable(bitrate, flags)
        parcel.writeByte(if (isOnClick) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SongDetailInfo> {
        override fun createFromParcel(parcel: Parcel): SongDetailInfo {
            return SongDetailInfo(parcel)
        }

        override fun newArray(size: Int): Array<SongDetailInfo?> {
            return arrayOfNulls(size)
        }
    }
}