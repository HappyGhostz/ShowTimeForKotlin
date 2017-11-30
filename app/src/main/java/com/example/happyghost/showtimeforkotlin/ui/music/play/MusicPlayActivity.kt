package com.example.happyghost.showtimeforkotlin.ui.music.play

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.SeekBar
import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.RxBus.event.MusicContralEvent
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongDetailInfo
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongLocalBean
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.*
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_music_play.*
import org.jetbrains.anko.startActivity


/**
 * Created by e445 on 2017/11/27.
 */
class MusicPlayActivity:BaseActivity<IBasePresenter>(), View.OnClickListener {
    var localSons: ArrayList<SongLocalBean>? = null
    var netMusics: ArrayList<SongDetailInfo>? = null
    var itemPosition = 0
    var isLocalMusic=true
    lateinit var mMusicBind: MusicPlayService.MusicBind
    lateinit var musicServiceConnect: MusicServiceConnect
    private val MUSIC_PLAYING_CURENT_DURATION = 100
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MUSIC_PLAYING_CURENT_DURATION -> upDataPlayTime()
            }
        }
    }



    override fun upDataView() {
        registerRxBus(MusicContralEvent::class.java,Consumer<MusicContralEvent> { t -> handleChannelMessage(t) })
        sb_play_seek.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBar?.progress = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val progress = seekBar?.progress
                mMusicBind.getMusicServiceInstance().seekTo(progress!!)
            }
        })
    }

    private fun handleChannelMessage(t: MusicContralEvent) {
        if(isLocalMusic){
            val songLocalBean = t.musicLocal
            if(songLocalBean!=null){
                getLocalSongInfos(songLocalBean)
            }
        }else{
            val musicNet = t.musicNet
            if(musicNet!=null){
                getNetSongInfos(musicNet)
            }
        }
    }

    private fun getNetSongInfos(musicNet: SongDetailInfo) {
        initActionBar(toolbar, musicNet.songinfo?.title!!, true)
        setPlayTime()
    }

    private fun getLocalSongInfos(songLocalBean: SongLocalBean) {
        initActionBar(toolbar, songLocalBean.title!!, true)
        setPlayTime()
    }


    override fun initView() {
        val musicIntent = Intent(this,MusicPlayService::class.java)
        musicIntent.putExtra(IS_LOCAL_MUSIC,isLocalMusic)
        musicIntent.putExtra(LOCAL_SONGS_POSITION,itemPosition)
        if(isLocalMusic){
            initActionBar(toolbar, localSons!![itemPosition].title!!,true)
            val musicIntent = Intent(this,MusicPlayService::class.java)
            musicIntent.putParcelableArrayListExtra(LOCAL_SONGS,localSons)
            setLocalBit()
        }else{
            initActionBar(toolbar, netMusics!![itemPosition].songinfo?.title!!,true)
            musicIntent.putParcelableArrayListExtra(NET_MUSICS,netMusics)
            setNetBit()
        }
        startService(musicIntent)
        musicServiceConnect = MusicServiceConnect()
        this.bindService(musicIntent,musicServiceConnect,BIND_AUTO_CREATE)
        iv_playing_play.setImageResource(R.mipmap.play_rdi_btn_pause)
        iv_needle.rotation = 0f
        initClick()


    }

    private fun setLocalBit() {
        val artwork = MusicTransformer.getArtwork(this, localSons!![itemPosition]._id, localSons!![itemPosition].albun_id)
        if(artwork!=null){
            MusicTransformer.applyBlur(artwork,iv_albumart)
            civ_photo.setImageBitmap(artwork)
        }else{
            val bitmap = BitmapFactory.decodeResource(this.resources, R.mipmap.music_local_default)
            MusicTransformer.applyBlur(bitmap,iv_albumart)
            civ_photo.setImageBitmap(bitmap)
        }
    }

    private fun setNetBit() {
        MusicTransformer.pictureDim(this as BaseActivity<IBasePresenter>,netMusics!![itemPosition].songinfo?.pic_small!!,iv_albumart)
        ImageLoader.loadCenterCrop(this,netMusics!![itemPosition].songinfo?.pic_small!!,civ_photo,R.mipmap.music_local_default)
    }

    private fun initClick() {
        iv_playing_mode.setOnClickListener(this)
        iv_playing_pre.setOnClickListener(this)
        iv_playing_play.setOnClickListener(this)
        iv_playing_next.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_playing_mode->{
                MusicPlayService.mCurrentMode = ++MusicPlayService.mCurrentMode % 3
                upDataMusicMode()
                SharedPreferencesUtil.putInt(ConsTantUtils.MUSIC_MODE, MusicPlayService.mCurrentMode)
            }
            R.id.iv_playing_pre->{
                mMusicBind.getMusicServiceInstance().preOrNext(false, isLocalMusic)
            }
            R.id.iv_playing_play-> upDataMusicState()
            R.id.iv_playing_next->{
                mMusicBind.getMusicServiceInstance().preOrNext(true, isLocalMusic)
            }
        }
    }

    private fun upDataMusicMode() {
        when (MusicPlayService.mCurrentMode) {
            ConsTantUtils.SINGO_MODE -> iv_playing_mode.setImageResource(R.mipmap.play_icn_one)
            ConsTantUtils.LIST_MODE -> iv_playing_mode.setImageResource(R.mipmap.play_icn_loop)
            ConsTantUtils.SHUFFLE_MODE -> iv_playing_mode.setImageResource(R.mipmap.play_icn_shuffle)
        }
    }

    private fun upDataMusicState() {
        mMusicBind.getMusicServiceInstance().hasPlay()
        val isPlaying = mMusicBind.getMusicServiceInstance().isPlaying()
        if (isPlaying) {
            iv_playing_play.setImageResource(R.mipmap.play_rdi_btn_pause)
            handler.sendEmptyMessageDelayed(MUSIC_PLAYING_CURENT_DURATION, 1000)
            iv_needle.rotation = 0f
        } else {
            iv_playing_play.setImageResource(R.mipmap.play_rdi_btn_play)
            handler.removeMessages(MUSIC_PLAYING_CURENT_DURATION)
            iv_needle.rotation = -30f
        }
    }

    override fun initInjector() {
        localSons = intent.getParcelableArrayListExtra<SongLocalBean>(LOCAL_SONGS)
        itemPosition = intent.getIntExtra(LOCAL_SONGS_POSITION,0)
        isLocalMusic = intent.getBooleanExtra(IS_LOCAL_MUSIC,true)
        netMusics = intent.getParcelableArrayListExtra<SongDetailInfo>(NET_MUSICS)
    }

    override fun getContentView(): Int = R.layout.activity_music_play
    companion object {
        var LOCAL_SONGS = "localsongs"
        var LOCAL_SONGS_POSITION = "position"
        var IS_LOCAL_MUSIC = "islocalmusic"
        var NET_MUSICS="netmusic"
        fun open(mContext: Context?, sons: ArrayList<SongLocalBean>, position: Int, isLocal: Boolean) {
            mContext?.startActivity<MusicPlayActivity>(LOCAL_SONGS to sons,
                    LOCAL_SONGS_POSITION to position,IS_LOCAL_MUSIC to isLocal)
            (mContext as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
        fun openNet(mContext: Context?, sons: ArrayList<SongDetailInfo>, position: Int, isLocal: Boolean){
            mContext?.startActivity<MusicPlayActivity>(NET_MUSICS to sons,LOCAL_SONGS_POSITION to position,IS_LOCAL_MUSIC to isLocal)
            (mContext as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(musicServiceConnect)
        //移除所有消息
        handler.removeCallbacksAndMessages(null)
        AppApplication.instance.mRxBus?.unSubscribe(this)
    }
    inner class MusicServiceConnect: ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mMusicBind = service as MusicPlayService.MusicBind
            setPlayTime()
        }

    }

    private fun setPlayTime() {
        val maxDuration = mMusicBind.getMusicServiceInstance().getDuration()
        val durationFormat = StringUtils.formatTime(maxDuration)
        tv_music_duration.text = durationFormat
        sb_play_seek.max = maxDuration
        upDataPlayTime()
        upDataMusicMode()
    }

    private fun upDataPlayTime() {
        val duration = mMusicBind.getMusicServiceInstance().getCurrentDuration()
        val durationPgFormat = StringUtils.formatTime(duration)
        tv_music_duration_played.text = durationPgFormat
        sb_play_seek.progress = duration
        handler.sendEmptyMessageDelayed(MUSIC_PLAYING_CURENT_DURATION, 1000)
    }

    fun <T> registerRxBus(eventType: Class<T>, action: Consumer<T>) {
        val disposable = AppApplication.instance.mRxBus?.doSubscribe(eventType, action, Consumer<Throwable> {
            throwable -> Log.e("NewsMainPresenter", throwable.toString()) })
        AppApplication.instance.mRxBus?.addSubscription(this, disposable!!)
    }

}