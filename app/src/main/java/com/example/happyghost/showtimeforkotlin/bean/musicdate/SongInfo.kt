package com.example.happyghost.showtimeforkotlin.bean.musicdate

/**
 * @author Zhao Chenping
 * @creat 2017/11/27.
 * @description
 */
class SongInfo {
    var error_code: Int = 0
    var listid: String? = null
    var title: String? = null
    var pic_300: String? = null
    var pic_500: String? = null
    var pic_w700: String? = null
    var width: String? = null
    var height: String? = null
    var listenum: String? = null
    var collectnum: String? = null
    var tag: String? = null
    var desc: String? = null
    var url: String? = null
    var content: List<Song>? = null

    var billboard: Billboar? = null
    class Song{
        var title: String? = null
        var del_status: String? = null
        var distribution: String? = null
        var song_id: String? = null
        var author: String? = null
        var album_id: String? = null
        var album_title: String? = null
        var relate_status: String? = null
        var all_rate: String? = null
        var high_rate: String? = null
        var all_artist_id: String? = null
        var copy_type: String? = null
        var has_mv: Int = 0
        var toneid: String? = null
        var resource_type: String? = null
        var is_ksong: String? = null
        var resource_type_ext: String? = null
        var versions: String? = null
        var bitrate_fee: String? = null
        var has_mv_mobile: Int = 0
        var ting_uid: String? = null
        var is_first_publish: Int = 0
        var havehigh: Int = 0
        var charge: Int = 0
        var learn: Int = 0
        var song_source: String? = null
        var piao_id: String? = null
        var korean_bb_song: String? = null
        var mv_provider: String? = null
        var share: String? = null
        var is_charge: String? = null
        var artist_id: String? = null
        var language: String? = null
        var pic_big: String? = null
        var pic_small: String? = null
        var country: String? = null
        var area: String? = null
        var publishtime: String? = null
        var album_no: String? = null
        var lrclink: String? = null
        var hot: String? = null
        var all_artist_ting_uid: String? = null
        var is_new: String? = null
        var rank_change: String? = null
        var rank: String? = null
        var style: String? = null
        var file_duration: Int = 0
        var artist_name: String? = null
    }
    class Billboar{
        var billboard_type: String? = null
        var billboard_no: String? = null
        var update_date: String? = null
        var billboard_songnum: String? = null
        var havemore: Int = 0
        var name: String? = null
        var comment: String? = null
        var pic_s640: String? = null
        var pic_s444: String? = null
        var pic_s260: String? = null
        var pic_s210: String? = null
        var web_url: String? = null
    }
}