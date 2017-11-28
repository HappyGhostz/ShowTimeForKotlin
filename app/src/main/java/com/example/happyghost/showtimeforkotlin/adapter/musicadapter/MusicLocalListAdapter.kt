package com.example.happyghost.showtimeforkotlin.adapter.musicadapter

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongLocalBean
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader
import com.example.happyghost.showtimeforkotlin.utils.MusicTransformer
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

/**
 * Created by e445 on 2017/11/23.
 */
class MusicLocalListAdapter:BaseQuickAdapter<SongLocalBean,BaseViewHolder>(R.layout.adapter_music_local_item) {
    override fun convert(helper: BaseViewHolder?, item: SongLocalBean?) {
        val imageView = helper?.getView<ImageView>(R.id.iv_albumr)
        val artwork = MusicTransformer.getArtwork(mContext, item?._id, item?.albun_id)
        if(artwork!=null){
            imageView?.setImageBitmap(artwork)
        }else{
            imageView?.setImageResource(R.mipmap.music_detail_play)
        }
//        if (item?.picurl != null) {
//            ImageLoader.loadCenterCrop(mContext, item.picurl!!, imageView!!, R.mipmap.music_detail_play)
//        } else if (item?.picurl == null && artwork == null) {
//            imageView?.setImageResource(R.mipmap.music_detail_play)
//        }

        helper?.setText(R.id.tv_song_title, item?.title)
                ?.setText(R.id.tv_song_artist, item?.artist)
                ?.setText(R.id.tv_local_album, item?.album)
    }


}