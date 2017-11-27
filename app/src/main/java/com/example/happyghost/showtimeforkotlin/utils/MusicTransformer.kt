package com.example.happyghost.showtimeforkotlin.utils

import com.example.happyghost.showtimeforkotlin.bean.musicdate.RankingListDetail
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongInfo
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongListDetail

/**
 * @author Zhao Chenping
 * @creat 2017/11/27.
 * @description
 */
class MusicTransformer {
    companion object {
        fun musicListTransformer(content: List<SongListDetail.SongDetail>?): ArrayList<SongInfo.Song> {
            var songs = ArrayList<SongInfo.Song>()
            var index =0
            while (index<content!!.size){
                val song = SongInfo.Song()
                val songDetail = content[index]
                song.song_id = songDetail.song_id
                song.title = songDetail.title
                song.album_id = songDetail.album_id
                song.album_title = songDetail.album_title
                song.all_artist_id = songDetail.all_artist_id
                song.all_rate = songDetail.all_rate
                song.author = songDetail.author
                song.bitrate_fee = songDetail.bitrate_fee
                song.charge = songDetail.charge
                song.copy_type=songDetail.copy_type
                song.del_status = songDetail.del_status
                song.distribution = songDetail.distribution
                song.has_mv = songDetail.has_mv
                song.has_mv_mobile = songDetail.has_mv_mobile
                song.havehigh = songDetail.havehigh
                song.high_rate = songDetail.high_rate
                song.is_charge = songDetail.is_charge
                song.is_first_publish = songDetail.is_first_publish
                song.is_ksong = songDetail.is_ksong
                song.korean_bb_song = songDetail.korean_bb_song
                song.learn = songDetail.learn
                song.mv_provider = songDetail.mv_provider
                song.piao_id = songDetail.piao_id
                song.relate_status = songDetail.relate_status
                song.resource_type = songDetail.resource_type
                song.resource_type_ext = songDetail.resource_type_ext
                song.share = songDetail.share
                song.song_source =  songDetail.song_source
                song.ting_uid = songDetail.ting_uid
                song.toneid = songDetail.toneid
                song.versions = songDetail.versions
                songs.add(song)
                index++
            }
            return songs
        }
        fun musicRankListTransformer(song_list: List<RankingListDetail.SongListBean>?): ArrayList<SongInfo.Song> {
            var songs = ArrayList<SongInfo.Song>()
            var index =0
            while (index<song_list!!.size){
                val song = SongInfo.Song()
                val songListBean = song_list[index]
                song.album_id = songListBean.album_id
                song.album_no = songListBean.album_no
                song.album_title = songListBean.album_title
                song.all_artist_id = songListBean.all_artist_id
                song.all_artist_ting_uid = songListBean.all_artist_ting_uid
                song.all_rate = songListBean.all_rate
                song.area = songListBean.area
                song.artist_id = songListBean.artist_id
                song.artist_name=songListBean.artist_name
                song.author = songListBean.author
                song.bitrate_fee = songListBean.bitrate_fee
                song.charge = songListBean.charge
                song.copy_type = songListBean.copy_type
                song.country = songListBean.country
                song.del_status = songListBean.del_status
                song.file_duration = songListBean.file_duration
                song.has_mv = songListBean.has_mv
                song.has_mv_mobile = songListBean.has_mv_mobile
                song.havehigh = songListBean.havehigh
                song.hot = songListBean.hot
                song.is_first_publish = songListBean.is_first_publish
                song.is_new  = songListBean.is_new
                song.korean_bb_song = songListBean.korean_bb_song
                song.language = songListBean.language
                song.learn = songListBean.learn
                song.lrclink = songListBean.lrclink
                song.mv_provider = songListBean.mv_provider
                song.piao_id = songListBean.piao_id
                song.pic_big = songListBean.pic_big
                song.pic_small = songListBean.pic_small
                song.publishtime = songListBean.publishtime
                song.rank = songListBean.rank
                song.rank_change = songListBean.rank_change
                song.relate_status = songListBean.relate_status
                song.resource_type = songListBean.resource_type
                song.resource_type_ext = songListBean.resource_type_ext
                song.song_id = songListBean.song_id
                song.song_source = songListBean.song_source
                song.style = songListBean.style
                song.ting_uid = songListBean.ting_uid
                song.title = songListBean.title
                song.toneid = songListBean.toneid
                songs.add(song)
                index++
            }
            return songs
        }
    }
}