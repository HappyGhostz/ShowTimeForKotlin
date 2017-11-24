package com.example.happyghost.showtimeforkotlin.ui.music.list

import com.example.happyghost.showtimeforkotlin.bean.musicdate.WrapperSongListInfo
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/11/24.
 * @description
 */
interface IListMusicView:IBaseView {
    fun loadListMusic(infos: List<WrapperSongListInfo.SongListInfo>)
    fun loadMoreListMusic(infos: List<WrapperSongListInfo.SongListInfo>)
    fun loadNoData()
}