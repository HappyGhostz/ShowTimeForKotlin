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
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

/**
 * Created by e445 on 2017/11/23.
 */
class MusicLocalListAdapter:BaseQuickAdapter<SongLocalBean,BaseViewHolder>(R.layout.adapter_music_local_item) {
    override fun convert(helper: BaseViewHolder?, item: SongLocalBean?) {
        val imageView = helper?.getView<ImageView>(R.id.iv_albumr)
        val artwork = getArtwork(mContext, item?._id, item?.albun_id)
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
        helper?.itemView?.setOnClickListener {
//            MusicPlay.lunch(mContext, item, holder.getPosition(), songs)
        }
    }

    private val sArtworkUri = Uri.parse("content://media/external/audio/albumart")
    private val sBitmapOptions = BitmapFactory.Options()
    private fun getArtwork(mContext: Context?, _id: Int?, albun_id: Int?): Bitmap? {
        if (albun_id != null) {
            if (albun_id < 0) {
                if (_id != null) {
                    if (_id >= 0) {
                        val bm = getArtworkFromFile(mContext!!, _id!!.toLong(), -1)
                        if (bm != null) {
                            return bm
                        }
                    }
                }
                return null
            }
        }
        val res = mContext?.contentResolver
        val uri = ContentUris.withAppendedId(sArtworkUri, albun_id!!.toLong())
        if (uri != null) {
            var `in`: InputStream? = null
            try {
                `in` = res?.openInputStream(uri)
                return BitmapFactory.decodeStream(`in`, null, sBitmapOptions)
            } catch (ex: FileNotFoundException) {
                var bm = getArtworkFromFile(mContext!!, _id!!.toLong(), albun_id!!.toLong())
                if (bm != null) {
                    if (bm!!.getConfig() == null) {
                        bm = bm!!.copy(Bitmap.Config.RGB_565, false)
                    }
                }
                return bm
            } finally {
                try {
                    if (`in` != null) {
                        `in`.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return null
    }

    private fun getArtworkFromFile(context: Context, songid: Long, albumid: Long): Bitmap? {
        var bm: Bitmap? = null
        if (albumid < 0 && songid < 0) {
            throw IllegalArgumentException("Must specify an album or a song id")
        }
        try {
            if (albumid < 0) {
                val uri = Uri.parse("content://media/external/audio/media/$songid/albumart")
                val pfd = context.contentResolver
                        .openFileDescriptor(uri, "r")
                if (pfd != null) {
                    val fd = pfd.fileDescriptor
                    bm = BitmapFactory.decodeFileDescriptor(fd)
                }
            } else {
                val uri = ContentUris.withAppendedId(sArtworkUri, albumid)
                val pfd = context.contentResolver
                        .openFileDescriptor(uri, "r")
                if (pfd != null) {
                    val fd = pfd.fileDescriptor
                    bm = BitmapFactory.decodeFileDescriptor(fd)
                }
            }
        } catch (ex: FileNotFoundException) {

        }

        return bm
    }
}