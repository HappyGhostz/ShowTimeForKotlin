package com.example.happyghost.showtimeforkotlin.ui.music.listdetail

import com.example.happyghost.showtimeforkotlin.bean.musicdate.RankingListDetail
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongDetailInfo
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongInfo
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongListDetail
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/11/25.
 * @description
 */
interface IBaseMusicListView:IBaseView {
    fun loadMusicListDetial(list: SongListDetail)
    fun loadRankPlayList(detail: RankingListDetail)
    fun loadSongInfo(detail: SongDetailInfo)
}