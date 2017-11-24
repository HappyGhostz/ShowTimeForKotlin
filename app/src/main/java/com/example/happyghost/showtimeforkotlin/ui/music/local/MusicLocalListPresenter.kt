package com.example.happyghost.showtimeforkotlin.ui.music.local

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.text.format.Formatter
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongLocalBean
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast

/**
 * @author Zhao Chenping
 * @creat 2017/11/24.
 * @description
 */
class MusicLocalListPresenter(view: MusicLocalFragment, context: Context) :IBasePresenter {
    var mView = view
    var mContext =context
    override fun getData() {
        var songLocalBeanList = ArrayList<SongLocalBean>()
        Observable.create(ObservableOnSubscribe<List<SongLocalBean>> {
            var cursor: Cursor? = null
            try {
                val contentResolver = mContext.contentResolver
                cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        arrayOf(MediaStore.Audio.Media._ID,
                                MediaStore.Audio.Media.TITLE,
                                MediaStore.Audio.Media.DISPLAY_NAME,
                                MediaStore.Audio.Media.DURATION,
                                MediaStore.Audio.Media.ARTIST,
                                MediaStore.Audio.Media.DATA,
                                MediaStore.Audio.Media.ALBUM,
                                MediaStore.Audio.Media.ALBUM_ID,
                                MediaStore.Audio.Media.SIZE), null,
                        null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER)
                if (cursor == null) {
                    return@ObservableOnSubscribe
                }
                val count = cursor.count
                if (count <= 0) {
                    mContext.toast("无音乐文件!")
                    return@ObservableOnSubscribe
                }
                while (cursor.moveToNext()){
                    //歌曲编号
                    val id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    //歌曲标题
                    val tilte = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    //歌曲的专辑名：MediaStore.Audio.Media.ALBUM
                    val album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    //歌曲的歌手名： MediaStore.Audio.Media.ARTIST
                    val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    //歌曲文件的路径 ：MediaStore.Audio.Media.DATA
                    val url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    //歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
                    val duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    //歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
                    val size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE))
                    val albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
                    val albumArt = getAlbumArt(albumId)
                    if (size > 1024 * 800) {//大于800K
                        val formatFileSize = Formatter.formatFileSize(mContext, size)
                        val musicMedia = SongLocalBean(id, tilte, url, formatFileSize, duration, artist, albumId,album, albumArt)
                        songLocalBeanList.add(musicMedia)
                    }
                }

            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                cursor?.close()
            }
            it.onNext(songLocalBeanList)
            it.onComplete()
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    mView.showLoading()
                }
                .compose(mView.bindToLife())
                .subscribe(object :Observer<List<SongLocalBean>>{
                    override fun onComplete() {
                        mView.hideLoading()
                    }

                    override fun onError(e: Throwable) {
                        mView.showNetError(object :EmptyErrLayout.OnReTryListener{
                            override fun onReTry() {
                                getData()
                            }
                        })
                    }

                    override fun onNext(t: List<SongLocalBean>) {
                        if(t.isNotEmpty()){
                            mView.loadLocalMusicListInfo(t)
                        }else{
                            mContext.toast("程序出错!")
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                    }
                })
    }

    override fun getMoreData() {

    }

    private fun getAlbumArt(album_id: Int): String? {
        val mUriAlbums = "content://media/external/audio/albums"
        val projection = arrayOf("album_art")
        var cur = mContext.contentResolver.query(
                Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)),
                projection, null, null, null)
        var album_art: String? = null
        if (cur!!.count > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext()
            album_art = cur.getString(0)
        }
        cur.close()
        cur = null
        return album_art
    }
}