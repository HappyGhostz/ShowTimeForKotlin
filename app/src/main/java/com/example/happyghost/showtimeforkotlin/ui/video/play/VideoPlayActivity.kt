package com.example.happyghost.showtimeforkotlin.ui.video.play

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.media.AudioManager
import android.net.Uri
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.ScreenUtils
import io.vov.vitamio.MediaPlayer
import io.vov.vitamio.Vitamio
import io.vov.vitamio.utils.ScreenResolution
import io.vov.vitamio.widget.VideoView
import kotlinx.android.synthetic.main.activity_video_play.*
import kotlinx.android.synthetic.main.layout_live_control.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * @author Zhao Chenping
 * @creat 2017/12/18.
 * @description
 */
class VideoPlayActivity: BaseActivity<IBasePresenter>(), MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnErrorListener {
    var mVideoTitle:String? = null
    var mVideoUrl:String? = null
    var mAudioManager: AudioManager? = null
    var mScreenWidth = 0//屏幕宽度
    val mIsFullScreen = true//是否为全屏
    var mShowVolume: Int = 0//声音
    var mShowLightness: Int = 0//亮度
    var mMaxVolume: Int = 0//最大声音
    /**
     * 声音
     */
    val ADD_FLAG = 1
    /**
     * 亮度
     */
    val SUB_FLAG = -1
    val HIDE_CONTROL_BAR = 0x02//隐藏控制条
    val HIDE_TIME = 5000//隐藏控制条时间
    val SHOW_CENTER_CONTROL = 0x03//显示中间控制
    val SHOW_CONTROL_TIME = 1000
    var mSimpleOnGestureListener: GestureDetector.SimpleOnGestureListener? = null
    private var mGestureDetector: GestureDetector? = null
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
            /**
             * 隐藏top ,bottom
             */
                HIDE_CONTROL_BAR -> hideControlBar()
            /**
             * 隐藏center控件
             */
                SHOW_CENTER_CONTROL -> if (control_center != null) {
                    control_center.visibility = View.GONE
                }
            }
        }
    }
    override fun upDataView() {
        val uri = Uri.parse(mVideoUrl)
        if (tv_live_nickname != null) {
            tv_live_nickname.text = mVideoTitle
        }

        if ( Vitamio.isInitialized(this)) {
            //            vmVideoview.setSubShown(true);
            vm_videoview.setVideoURI(uri)
//            vm_videoview.setBufferSize(10240); //设置视频缓冲大小。默认1024KB，单位byte
            vm_videoview.setBufferSize(1024 * 1024 * 2)
            vm_videoview.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH)
            vm_videoview.requestFocus()
            vm_videoview.setOnPreparedListener({ mediaPlayer ->
                // optional need Vitamio 4.0
                mediaPlayer.setPlaybackSpeed(1.0f)
                fl_loading.visibility = View.GONE
                iv_live_play.setImageResource(R.drawable.img_live_videopause)
                mHandler.sendEmptyMessageDelayed(HIDE_CONTROL_BAR, HIDE_TIME.toLong())
            })
        }
    }

    override fun initView() {
        vm_videoview.keepScreenOn = true
//        svProgressHUD = new SVProgressHUD(this);
        //获取屏幕宽度
        val screenPair = ScreenResolution.getResolution(this)
        mScreenWidth = screenPair.first
        initVolumeWithLight()
        addTouchListener()
        vm_videoview.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0f)
        vm_videoview.setOnInfoListener(this)
        vm_videoview.setOnBufferingUpdateListener(this)
        vm_videoview.setOnErrorListener(this)
        clickListener()
    }
    fun clickListener(){
        iv_live_refresh.setOnClickListener {
            upDataView()
        }
        iv_live_play.setOnClickListener {
            if (vm_videoview.isPlaying()) {
                vm_videoview.pause()
                iv_live_play.setImageResource(R.drawable.img_live_videoplay)
                mHandler.removeMessages(HIDE_CONTROL_BAR)
                showControlBar()
            } else {
                vm_videoview.start()
                iv_live_play.setImageResource(R.drawable.img_live_videopause)
                mHandler.sendEmptyMessageDelayed(HIDE_CONTROL_BAR, HIDE_TIME.toLong())
            }
        }
        iv_back.setOnClickListener {
            finish()
        }
    }

    /**
     * 初始化声音和亮度
     */
    private fun initVolumeWithLight() {
        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        mMaxVolume = mAudioManager!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        mShowVolume = mAudioManager!!.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / mMaxVolume
        mShowLightness = ScreenUtils.getVideoScreenBrightness(this)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (mGestureDetector != null)
            mGestureDetector!!.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
    fun addTouchListener(){
        mSimpleOnGestureListener =object :GestureDetector.SimpleOnGestureListener(){
            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                if(!mIsFullScreen){
                   return false
                }
                var x = e1?.x
                val absDistanceX = Math.abs(distanceX)//distanceX<0 从左到右
                val absDistanceY = Math.abs(distanceY)//distanceY<0 从上到下
                if(absDistanceX<absDistanceY){//右滑距离小于下滑距离,即上下滑动
                    if(distanceY>0){//向上滑动
                        if(x!! >= mScreenWidth*0.65){//右边调节声音
                            changeVolume(ADD_FLAG)
                        }else{//调节亮度
                            changeLightness(ADD_FLAG)
                        }
                    } else{ //向下滑动
                        if (x!! >= mScreenWidth * 0.65) {
                            changeVolume(SUB_FLAG)
                        } else {
                            changeLightness(SUB_FLAG)
                        }
                    }
                } else {
                    // X方向的距离比Y方向的大，即 左右 滑动
                    //调节普通视频进度
                }
                return false
            }
            //单击事件
            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                if (control_bottom.visibility == View.VISIBLE) {
                    mHandler.removeMessages(HIDE_CONTROL_BAR)
                    hideControlBar()
                } else {
                    showControlBar()
                    mHandler.sendEmptyMessageDelayed(HIDE_CONTROL_BAR, HIDE_TIME.toLong())
                }
                return true
            }
            //双击事件，有的视频播放器支持双击播放暂停，可从这实现
            override fun onDoubleTap(e: MotionEvent?): Boolean {
                return super.onDoubleTap(e)
            }
        }
        mGestureDetector = GestureDetector(this, mSimpleOnGestureListener);
    }

    /**
     * 改变声音
     */
    private fun changeVolume(flag: Int) {
        mShowVolume += flag
        if (mShowVolume > 100) {
            mShowVolume = 100
        } else if (mShowVolume < 0) {
            mShowVolume = 0
        }
        tv_control_name.text = "音量"
        iv_control_img.setImageResource(R.mipmap.img_volume)
        tv_control.text = mShowVolume.toString() + "%"
        val tagVolume = mShowVolume * mMaxVolume / 100
        //tagVolume:音量绝对值
        mAudioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, tagVolume, 0)
        mHandler.removeMessages(SHOW_CENTER_CONTROL)
        control_center.visibility = View.VISIBLE
        mHandler.sendEmptyMessageDelayed(SHOW_CENTER_CONTROL, SHOW_CONTROL_TIME.toLong())
    }

    /**
     * 改变亮度
     */
    private fun changeLightness(flag: Int) {
        mShowLightness += flag
        if (mShowLightness > 255) {
            mShowLightness = 255
        } else if (mShowLightness <= 0) {
            mShowLightness = 0
        }
        tv_control_name.text = "亮度"
        iv_control_img.setImageResource(R.mipmap.img_light)
        tv_control.text = (mShowLightness * 100 / 255).toString() + "%"
        val lp = window.attributes
        lp.screenBrightness = mShowLightness / 255f
        window.attributes = lp

        mHandler.removeMessages(SHOW_CENTER_CONTROL)
        control_center.visibility = View.VISIBLE
        mHandler.sendEmptyMessageDelayed(SHOW_CENTER_CONTROL, SHOW_CONTROL_TIME.toLong())
    }

    /**
     * 隐藏控制条
     */
    private fun hideControlBar() {
        if (control_bottom != null && control_top != null) {
            control_bottom.visibility = View.GONE
            control_top.visibility = View.GONE
        }
    }

    /**
     * 显示控制条
     */
    private fun showControlBar() {
        if (control_bottom != null && control_top != null) {
            control_bottom.visibility = View.VISIBLE
            control_top.visibility = View.VISIBLE
        }
    }
    override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        when (what) {
            MediaPlayer.MEDIA_INFO_BUFFERING_START -> {
                if (vm_videoview.isPlaying) {
                    vm_videoview.pause()
                }
                iv_live_play.setImageResource(R.drawable.img_live_videoplay)
                mHandler.removeMessages(HIDE_CONTROL_BAR)
                showControlBar()
            }
        //            完成缓冲
            MediaPlayer.MEDIA_INFO_BUFFERING_END -> {
                fl_loading.visibility = View.GONE
                if (!vm_videoview.isPlaying)
                    vm_videoview.start()
                iv_live_play.setImageResource(R.drawable.img_live_videopause)
                mHandler.sendEmptyMessageDelayed(HIDE_CONTROL_BAR, HIDE_TIME.toLong())
            }
            MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED -> {
//                tv_loading_buffer.text = "直播已缓冲$percent%..."
            }
        }
        return true
    }
    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
        fl_loading.visibility = View.VISIBLE
        if (vm_videoview.isPlaying)
            vm_videoview.pause()
        tv_loading_buffer.text = "直播已缓冲$percent%..."
    }
    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
            toast("主播还在赶来的路上~~")
        }
        return true
    }

    override fun initInjector() {
        if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
            return
        //隐藏状态栏
        rlBookReadRoot.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        mVideoTitle = intent.getStringExtra(VIDEO_TITLE)
        mVideoUrl = intent.getStringExtra(VIDEO_URL)
        var screenOrientation = intent.getStringExtra(SCREEN_ORIENTATION)
        if(TextUtils.equals(screenOrientation,"sensorPortrait")){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT  //强制为竖屏sensorPortrait
            iv_live_setting.visibility = View.INVISIBLE
            iv_live_gift.visibility = View.INVISIBLE
            iv_live_share.visibility = View.INVISIBLE
            iv_live_follow.visibility = View.INVISIBLE
        }else{
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE //强制为横屏sensorLandscape
        }
    }

    override fun getContentView(): Int =  R.layout.activity_video_play
    companion object {
        var VIDEO_TITLE= "videotitle"
        var VIDEO_URL = "videourl"
        var SCREEN_ORIENTATION = "orientation"
        fun open(context: Context, item: String, liveUrl: String, screenOrientation: String){
            context.startActivity<VideoPlayActivity>(VIDEO_TITLE  to item,VIDEO_URL to liveUrl,SCREEN_ORIENTATION to screenOrientation)
            (context as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }

    override fun onRestart() {
        super.onRestart()
        //        mPresenter.getPresenterPcLiveVideoInfo(Room_id);
        mPresenter.getData()
        if (vm_videoview != null) {
            vm_videoview.start()

        }
    }

    override fun onPause() {
        super.onPause()
        if (vm_videoview != null) {
            vm_videoview.pause()
        }
    }

    override fun onDestroy() {
        if (vm_videoview != null) {
            //        释放资源
            vm_videoview.stopPlayback()
        }
        super.onDestroy()
    }

}