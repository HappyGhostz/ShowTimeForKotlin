package com.example.happyghost.showtimeforkotlin.ui.music.search

import com.example.happyghost.showtimeforkotlin.bean.musicdate.MusicSearchList
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongDetailInfo
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/12/5.
 * @description
 */
interface IMusicSearchView:IBaseView {
    fun loadSearchMusicResult(results:List<MusicSearchList.SongListBean>)
    fun loadSearchMusicInfo(results: SongDetailInfo)
}