package com.example.happyghost.showtimeforkotlin.ui.music.local

import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongLocalBean
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/11/24.
 * @description
 */
interface IBaseLocalMusicView:IBaseView {
    abstract fun loadLocalMusicListInfo(localBeanList: List<SongLocalBean>)
}