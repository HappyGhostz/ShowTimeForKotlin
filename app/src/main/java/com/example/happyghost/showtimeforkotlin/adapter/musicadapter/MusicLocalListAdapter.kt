package com.example.happyghost.showtimeforkotlin.adapter.musicadapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongLocalBean

/**
 * Created by e445 on 2017/11/23.
 */
class MusicLocalListAdapter:BaseQuickAdapter<SongLocalBean,BaseViewHolder>(R.layout.adapter_music_local_item) {
    override fun convert(helper: BaseViewHolder?, item: SongLocalBean?) {

    }
}