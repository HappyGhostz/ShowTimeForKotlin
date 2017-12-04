package com.example.happyghost.showtimeforkotlin.utils

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View
import com.bumptech.glide.Glide
import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.musicdate.RankingListDetail
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongInfo
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongListDetail
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.ui.music.listdetail.MusicListDetialActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

/**
 * @author Zhao Chenping
 * @creat 2017/11/27.
 * @description
 */
class MusicTransformer {
    companion object {
        private val radius = 25
        fun musicListTransformer(content: List<SongListDetail.SongDetail>?): ArrayList<SongInfo.Song> {
            var songs = ArrayList<SongInfo.Song>()
            var index =0
            while (index<content!!.size){
                val song = SongInfo.Song()
                val songDetail = content[index]
                song.song_id = songDetail.song_id
                song.title = songDetail.title
                song.album_id = songDetail.album_id
                song.album_title = songDetail.album_title
                song.all_artist_id = songDetail.all_artist_id
                song.all_rate = songDetail.all_rate
                song.author = songDetail.author
                song.bitrate_fee = songDetail.bitrate_fee
                song.charge = songDetail.charge
                song.copy_type=songDetail.copy_type
                song.del_status = songDetail.del_status
                song.distribution = songDetail.distribution
                song.has_mv = songDetail.has_mv
                song.has_mv_mobile = songDetail.has_mv_mobile
                song.havehigh = songDetail.havehigh
                song.high_rate = songDetail.high_rate
                song.is_charge = songDetail.is_charge
                song.is_first_publish = songDetail.is_first_publish
                song.is_ksong = songDetail.is_ksong
                song.korean_bb_song = songDetail.korean_bb_song
                song.learn = songDetail.learn
                song.mv_provider = songDetail.mv_provider
                song.piao_id = songDetail.piao_id
                song.relate_status = songDetail.relate_status
                song.resource_type = songDetail.resource_type
                song.resource_type_ext = songDetail.resource_type_ext
                song.share = songDetail.share
                song.song_source =  songDetail.song_source
                song.ting_uid = songDetail.ting_uid
                song.toneid = songDetail.toneid
                song.versions = songDetail.versions
                songs.add(song)
                index++
            }
            return songs
        }
        fun musicRankListTransformer(song_list: List<RankingListDetail.SongListBean>?): ArrayList<SongInfo.Song> {
            var songs = ArrayList<SongInfo.Song>()
            var index =0
            while (index<song_list!!.size){
                val song = SongInfo.Song()
                val songListBean = song_list[index]
                song.album_id = songListBean.album_id
                song.album_no = songListBean.album_no
                song.album_title = songListBean.album_title
                song.all_artist_id = songListBean.all_artist_id
                song.all_artist_ting_uid = songListBean.all_artist_ting_uid
                song.all_rate = songListBean.all_rate
                song.area = songListBean.area
                song.artist_id = songListBean.artist_id
                song.artist_name=songListBean.artist_name
                song.author = songListBean.author
                song.bitrate_fee = songListBean.bitrate_fee
                song.charge = songListBean.charge
                song.copy_type = songListBean.copy_type
                song.country = songListBean.country
                song.del_status = songListBean.del_status
                song.file_duration = songListBean.file_duration
                song.has_mv = songListBean.has_mv
                song.has_mv_mobile = songListBean.has_mv_mobile
                song.havehigh = songListBean.havehigh
                song.hot = songListBean.hot
                song.is_first_publish = songListBean.is_first_publish
                song.is_new  = songListBean.is_new
                song.korean_bb_song = songListBean.korean_bb_song
                song.language = songListBean.language
                song.learn = songListBean.learn
                song.lrclink = songListBean.lrclink
                song.mv_provider = songListBean.mv_provider
                song.piao_id = songListBean.piao_id
                song.pic_big = songListBean.pic_big
                song.pic_small = songListBean.pic_small
                song.publishtime = songListBean.publishtime
                song.rank = songListBean.rank
                song.rank_change = songListBean.rank_change
                song.relate_status = songListBean.relate_status
                song.resource_type = songListBean.resource_type
                song.resource_type_ext = songListBean.resource_type_ext
                song.song_id = songListBean.song_id
                song.song_source = songListBean.song_source
                song.style = songListBean.style
                song.ting_uid = songListBean.ting_uid
                song.title = songListBean.title
                song.toneid = songListBean.toneid
                songs.add(song)
                index++
            }
            return songs
        }
        //模糊图片
        fun pictureDim(baseActivity: BaseActivity<IBasePresenter>, path:String, view:View) {
//            baseActivity as
            Observable.just(path)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map(Function<String?, Bitmap>{
                        return@Function getDimBitmap(it)
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(baseActivity.bindToLife())
                    .subscribe{
                        applyBlur(it,view)
                    }
        }

        private fun getDimBitmap(url: String): Bitmap {
            val fileFutureTarget = Glide.with(AppApplication.instance.getContext()).load(url).downloadOnly(100, 100)
            val mPath: String
            var mIs: FileInputStream? = null
            try {
                val file = fileFutureTarget.get()
                mPath = file.absolutePath
                mIs = FileInputStream(mPath)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if(mIs==null){
                return BitmapFactory.decodeResource(AppApplication.instance.getContext().resources, R.mipmap.music_local_default)
            }
            return BitmapFactory.decodeStream(mIs)
        }
        //这里需要api17，但也可以用FastBlur来对图片进行模糊处理
        fun applyBlur(bitmap: Bitmap, view: View){
            //处理得到模糊效果的图
            val renderScript = RenderScript.create(AppApplication.instance.getContext())
            // Allocate memory for Renderscript to work with
            val input = Allocation.createFromBitmap(renderScript, bitmap)
            val output = Allocation.createTyped(renderScript, input.type)
            // Load up an instance of the specific script that we want to use.
            val scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
            scriptIntrinsicBlur.setInput(input)
            // Set the blur radius
            scriptIntrinsicBlur.setRadius(radius.toFloat())
            // Start the ScriptIntrinisicBlur
            scriptIntrinsicBlur.forEach(output)
            // Copy the output to the blurred bitmap
            output.copyTo(bitmap)
            renderScript.destroy()
            val bitmapDrawable = BitmapDrawable(AppApplication.instance.getContext().resources, bitmap)
            view.background = bitmapDrawable
        }
        //取出本地音乐专辑图片
        private val sArtworkUri = Uri.parse("content://media/external/audio/albumart")
        private val sBitmapOptions = BitmapFactory.Options()
        fun getArtwork(mContext: Context?, _id: Int?, albun_id: Int?): Bitmap? {
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
}