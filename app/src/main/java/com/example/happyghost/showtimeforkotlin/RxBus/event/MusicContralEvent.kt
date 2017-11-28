package com.example.happyghost.showtimeforkotlin.RxBus.event

import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongLocalBean

/**
 * @author Zhao Chenping
 * @creat 2017/11/28.
 * @description
 */
class MusicContralEvent {
    companion object {
        /**
         * 音乐播放器播放、暂停、上一首、下一首、播放顺序事件
         */
        val MUSIC_PLAY = 30
        val MUSIC_PUSE = 31
        val MUSIC_PRE = 32
        val MUSIC_NEXT = 33
    }
    var musicTypr= 0
    var musicLocal:SongLocalBean
    constructor(musiC_PLAY: Int, songLocalBean: SongLocalBean){
        this.musicTypr = musiC_PLAY
        this.musicLocal = songLocalBean
    }
}