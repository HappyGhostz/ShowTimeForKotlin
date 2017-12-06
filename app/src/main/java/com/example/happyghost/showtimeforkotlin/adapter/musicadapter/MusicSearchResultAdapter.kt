package com.example.happyghost.showtimeforkotlin.adapter.musicadapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.musicdate.MusicSearchList

/**
 * @author Zhao Chenping
 * @creat 2017/12/5.
 * @description
 */
class MusicSearchResultAdapter:BaseQuickAdapter<MusicSearchList.SongListBean,BaseViewHolder>(R.layout.adapter_music_search) {
    override fun convert(helper: BaseViewHolder?, item: MusicSearchList.SongListBean?) {
        helper?.setText(R.id.tv_song_title,item?.title)
                ?.setText(R.id.tv_song_artist,item?.author)
                ?.setText(R.id.tv_local_album,item?.album_title)
    }
}