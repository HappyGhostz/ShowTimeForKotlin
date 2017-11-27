package com.example.happyghost.showtimeforkotlin.adapter.musicadapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongInfo

/**
 * @author Zhao Chenping
 * @creat 2017/11/27.
 * @description
 */
class MusicListDetialAdapter:BaseQuickAdapter<SongInfo.Song,BaseViewHolder>(R.layout.adapter_music_list_item) {
    override fun convert(helper: BaseViewHolder?, item: SongInfo.Song?) {
        helper?.setText(R.id.tv_trackNumber, helper.adapterPosition.toString())
                ?.setText(R.id.tv_song_title, item?.title)
                ?.setText(R.id.tv_song_artist, item?.author)
    }
}