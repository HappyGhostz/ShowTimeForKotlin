package com.example.happyghost.showtimeforkotlin.ui.music.play

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.RxBus.event.MusicContralEvent
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongDetailInfo
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongLocalBean
import com.example.happyghost.showtimeforkotlin.utils.AudioFocusManager
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.SharedPreferencesUtil
import java.util.*

/**
 * @author Zhao Chenping
 * @creat 2017/11/28.
 * @description
 */
class MusicPlayService: Service(), MediaPlayer.OnPreparedListener {
    var localMusics: ArrayList<SongLocalBean>? = null
    var netMusics: ArrayList<SongDetailInfo>? = null
    var isLocal = false
    var itemPosition = 0
    var mCurrent = -1
    var mMediaPlayer: MediaPlayer? =  null
    var isPrepared = false
    lateinit var mFocusManager:AudioFocusManager
    companion object {
        var mCurrentMode = 0
    }
    override fun onBind(intent: Intent?): IBinder {
        return MusicBind()
    }

    override fun onCreate() {
        super.onCreate()
        mCurrentMode = SharedPreferencesUtil.getInt(ConsTantUtils.MUSIC_MODE, 0)
        mFocusManager = AudioFocusManager(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        localMusics = intent?.getParcelableArrayListExtra<SongLocalBean>(MusicPlayActivity.LOCAL_SONGS)
        netMusics = intent?.getParcelableArrayListExtra<SongDetailInfo>(MusicPlayActivity.NET_MUSICS)
        isLocal = intent?.getBooleanExtra(MusicPlayActivity.IS_LOCAL_MUSIC, true)!!
        itemPosition = intent.getIntExtra(MusicPlayActivity.LOCAL_SONGS_POSITION,0)
        mCurrent = itemPosition
        if(mCurrent<0){
            mCurrent=0
        }
        playMusic()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun playMusic() {
        if(mMediaPlayer==null){
            mMediaPlayer = MediaPlayer()
            mMediaPlayer!!.setOnPreparedListener(this)
            mMediaPlayer!!.setOnCompletionListener(MusicCompleteListener())
        }else {
            mMediaPlayer!!.reset()
        }
        try {
            if(isLocal){
                mMediaPlayer!!.setDataSource(localMusics!![mCurrent].path)
            }else{
                mMediaPlayer!!.setDataSource(netMusics!![mCurrent].bitrate?.file_link)
            }
            mMediaPlayer!!.prepareAsync()
        }catch (e:Exception){

        }

    }

    override fun onPrepared(mp: MediaPlayer?) {
        if(mFocusManager.requestAudioFocus()){
            mp?.start()
            isPrepared = true
            val rxBus = AppApplication.instance.mRxBus
            if (isLocal) {
                rxBus?.post(MusicContralEvent(MusicContralEvent.MUSIC_PLAY, localMusics!![mCurrent]))
            }
            else {
                rxBus?.post(MusicContralEvent(MusicContralEvent.MUSIC_PLAY, netMusics!![mCurrent]))
            }
        }

    }

    private inner class MusicCompleteListener : MediaPlayer.OnCompletionListener {
        override fun onCompletion(mp: MediaPlayer) {
            preOrNext(true,isLocal)
        }
    }
    fun preOrNext(isNext:Boolean,isLocal:Boolean){
        if(isLocal){
            when(mCurrentMode){
                ConsTantUtils.LIST_MODE->{
                    if (isNext) {
                        mCurrent = if (mCurrent == localMusics!!.size - 1) 0 else ++mCurrent
                    } else {
                        mCurrent = if (mCurrent == 0) localMusics!!.size - 1 else --mCurrent
                    }
                }
                ConsTantUtils.SHUFFLE_MODE->{
                    val random = Random()
                    var randomPosition = random.nextInt(localMusics!!.size)
                    while (randomPosition == mCurrent) {
                        randomPosition = random.nextInt(localMusics!!.size)
                    }
                    mCurrent = randomPosition
                }
                ConsTantUtils.SINGO_MODE->{
                    mCurrent = mCurrent
                }
            }
        }else{
            when(mCurrentMode){
                ConsTantUtils.LIST_MODE->{
                    if (isNext) {
                        mCurrent = if (mCurrent == netMusics!!.size - 1) 0 else ++mCurrent
                    } else {
                        mCurrent = if (mCurrent == 0) netMusics!!.size - 1 else --mCurrent
                    }
                }
                ConsTantUtils.SHUFFLE_MODE->{
                    val random = Random()
                    var randomPosition = random.nextInt(netMusics!!.size)
                    while (randomPosition == mCurrent) {
                        randomPosition = random.nextInt(netMusics!!.size)
                    }
                    mCurrent = randomPosition
                }
                ConsTantUtils.SINGO_MODE->{
                    mCurrent = mCurrent
                }
            }
        }
        playMusic()
    }
    fun isPlaying(): Boolean = mMediaPlayer!!.isPlaying
    fun seekTo(progress: Int) {
        mMediaPlayer!!.seekTo(progress)
    }

    fun getCurrentDuration(): Int= mMediaPlayer!!.currentPosition


    fun getDuration(): Int = mMediaPlayer!!.duration


    fun isPrepare():Boolean= isPrepared
    fun playStop(){
        mMediaPlayer!!.stop()
        mMediaPlayer!!.reset()
    }
    fun playPause(){
        if (!isPlaying()) {
            return
        }

        mMediaPlayer!!.pause()
        isPrepared = false
    }
    fun hasPlay() {
        if (mMediaPlayer!!.isPlaying()) {
            mMediaPlayer!!.pause()
            //            rxBus.post(new MusicContralEvent(MusicContralEvent.MUSIC_PUSE));
        } else {
            mMediaPlayer!!.start()
            //            rxBus.post(new MusicContralEvent(MusicContralEvent.MUSIC_PLAY));
        }
    }

    inner class MusicBind: Binder() {
        fun getMusicServiceInstance(): MusicPlayService {
            return this@MusicPlayService
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mFocusManager.abandonAudioFocus()
    }


}