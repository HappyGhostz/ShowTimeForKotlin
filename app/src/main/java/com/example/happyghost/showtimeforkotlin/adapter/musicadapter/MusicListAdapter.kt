package com.example.happyghost.showtimeforkotlin.adapter.musicadapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.musicdate.WrapperSongListInfo
import com.example.happyghost.showtimeforkotlin.utils.DefIconFactory
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader

/**
 * @author Zhao Chenping
 * @creat 2017/11/24.
 * @description
 */
class MusicListAdapter:BaseQuickAdapter<WrapperSongListInfo.SongListInfo,BaseViewHolder>(R.layout.adapter_reomend_music_item) {
    override fun convert(helper: BaseViewHolder?, item: WrapperSongListInfo.SongListInfo?) {
        var count = Integer.parseInt(item?.listenum)
        if (count > 10000) {
            count /= 10000
            helper?.setText(R.id.tv_songlist_count, count.toString() + "万")
        } else {
            helper?.setText(R.id.tv_songlist_count, item?.listenum)
        }
        helper?.setText(R.id.tv_songlist_name, item?.title)
        val imageView = helper?.getView<ImageView>(R.id.iv_songlist_photo)
        ImageLoader.loadCenterCrop(mContext, item?.pic_300!!, imageView!!, DefIconFactory.provideIcon())

        helper?.itemView.setOnClickListener{
//            MusicListDetialActivity.lunch(mContext, item)
        }
    }
}