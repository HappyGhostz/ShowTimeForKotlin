package com.example.happyghost.showtimeforkotlin.ui.video.play

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.media.AudioManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.ScreenUtils
import com.example.happyghost.showtimeforkotlin.utils.StringUtils
import io.vov.vitamio.MediaPlayer
import io.vov.vitamio.Vitamio
import io.vov.vitamio.utils.ScreenResolution
import io.vov.vitamio.widget.VideoView
import kotlinx.android.synthetic.main.activity_video_play.*
import kotlinx.android.synthetic.main.layout_live_control.*
import kotlinx.android.synthetic.main.layout_video_control.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * @author Zhao Chenping
 * @creat 2018/1/3.
 * @description
 */
class DefaultVideoPlayActivity:BaseActivity<IBasePresenter>(), MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnErrorListener {
    var mPicUrls =ArrayList<String>()
    var mVideoUrls =ArrayList<String>()
    var mTitles =ArrayList<String>()
    var mAudioManager: AudioManager? = null
    var mScreenWidth = 0//屏幕宽度
    var mScreenHeight = 0
    var mIsFullScreen = true//是否为全屏
    var mShowVolume: Int = 0//声音
    var mShowLightness: Int = 0//亮度
    var mMaxVolume: Int = 0//最大声音
    var mCurrentVideo = 0
    var mVideoHeight = 0
    var mVideoWidth = 0
    var statusBarHeight : Int =-1
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
    private val CURRENT_PLAY_TIME = 100
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
                SHOW_CENTER_CONTROL -> gone(video_control_center)
            /**
             * 更新播放的当前时间
             */
                CURRENT_PLAY_TIME -> upDataPlayTime()
            }
        }
    }

    private fun upDataPlayTime() {
        val currentPosition = vm_videoview.currentPosition.toInt()
        playTime.text = StringUtils.formatTime(currentPosition)
        sbPlay.progress = currentPosition
        mHandler.sendEmptyMessageDelayed(CURRENT_PLAY_TIME, 500)
    }

    private fun hideControlBar() {
        if (ll_bottompanel != null && video_toppanel != null) {
            ll_bottompanel.visibility = View.GONE
            video_toppanel.visibility = View.GONE
            //隐藏状态栏
            rlBookReadRoot.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }
    /**
     * 显示控制条
     */
    private fun showControlBar() {
        if (ll_bottompanel != null && video_toppanel != null) {
            ll_bottompanel.visibility = View.VISIBLE
            video_toppanel.visibility = View.VISIBLE
            //隐藏状态栏
            rlBookReadRoot.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }
    override fun upDataView() {
        val uri = Uri.parse(mVideoUrls[mCurrentVideo])
        if (tv_videotitle != null) {
            tv_videotitle.text = mPicUrls[mCurrentVideo]
        }

        if ( Vitamio.isInitialized(this)) {
            vm_videoview.setVideoURI(uri)
            vm_videoview.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH)
            vm_videoview.requestFocus()
            vm_videoview.setOnPreparedListener({ mediaPlayer ->
                // optional need Vitamio 4.0
                mediaPlayer.setPlaybackSpeed(1.0f)
                fl_loading.visibility = View.GONE
                iv_live_play.setImageResource(R.drawable.img_live_videopause)
                mHandler.sendEmptyMessageDelayed(HIDE_CONTROL_BAR, HIDE_TIME.toLong())
                mVideoWidth = mediaPlayer.videoWidth
                mVideoHeight = mediaPlayer.videoHeight
                initScreen(true)
                playDuration.text = StringUtils.formatTime(mediaPlayer.duration.toInt())
                sbPlay.max = mediaPlayer.duration.toInt()
                upDataPlayTime()
            })
        }
    }

    override fun initView() {
        gone(liveControl)
        visible(videoControl)
        initStatusBar()
        vm_videoview.keepScreenOn = true
//        svProgressHUD = new SVProgressHUD(this);
        //获取屏幕宽度
        val screenPair = ScreenResolution.getResolution(this)
        mScreenWidth = screenPair.first
        mScreenHeight = screenPair.second
        initVolumeWithLight()
        addTouchListener()
        vm_videoview.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH, 0f)
        vm_videoview.setOnInfoListener(this)
        vm_videoview.setOnBufferingUpdateListener(this)
        vm_videoview.setOnErrorListener(this)
        clickListener()
    }

    private fun initScreen(isForAuto:Boolean) {
        if(mIsFullScreen){
            vm_videoview.layoutParams.height = mScreenHeight
            vm_videoview.layoutParams.width = mScreenWidth
            ib_screensize.setImageResource(R.drawable.defaultscreen_selector)
        }else{
            if(mVideoHeight==0||mVideoWidth==0){
                vm_videoview.layoutParams.height =mScreenHeight*mVideoWidth/mScreenWidth
                vm_videoview.layoutParams.width = mScreenWidth*mVideoHeight/mScreenHeight
            }else{
                vm_videoview.layoutParams.height =mVideoHeight
                vm_videoview.layoutParams.width = mVideoWidth
            }
            ib_screensize.setImageResource(R.drawable.fullscreen_selector)
        }
        vm_videoview.requestLayout()
        if(!isForAuto){
            mIsFullScreen=!mIsFullScreen
        }
    }

    private fun nextVideo(isNext: Boolean) {
        if (isNext) {
            mCurrentVideo = if (mCurrentVideo == mVideoUrls.size - 1) 0 else ++mCurrentVideo
        } else {
            mCurrentVideo = if (mCurrentVideo == 0) mVideoUrls.size - 1 else --mCurrentVideo
        }
        upDataView()
    }

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

    private fun addTouchListener() {
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
                if (ll_bottompanel.visibility == View.VISIBLE) {
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
        tv_control_name_video.text = "音量"
        iv_control_video.setImageResource(R.mipmap.img_volume)
        tv_control_video.text = mShowVolume.toString() + "%"
        val tagVolume = mShowVolume * mMaxVolume / 100
        //tagVolume:音量绝对值
        mAudioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, tagVolume, 0)
        mHandler.removeMessages(SHOW_CENTER_CONTROL)
        video_control_center.visibility = View.VISIBLE
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
        tv_control_name_video.text = "亮度"
        iv_control_video.setImageResource(R.mipmap.img_light)
        tv_control_video.text = (mShowLightness * 100 / 255).toString() + "%"
        val lp = window.attributes
        lp.screenBrightness = mShowLightness / 255f
        window.attributes = lp

        mHandler.removeMessages(SHOW_CENTER_CONTROL)
        video_control_center.visibility = View.VISIBLE
        mHandler.sendEmptyMessageDelayed(SHOW_CENTER_CONTROL, SHOW_CONTROL_TIME.toLong())
    }

    private fun clickListener() {
        ib_back.setOnClickListener { finish() }
        ib_pre.setOnClickListener {
            nextVideo(false)
        }
        ib_next.setOnClickListener {
            nextVideo(true)
        }
        ib_playpause.setOnClickListener {
            if (vm_videoview.isPlaying) {
                vm_videoview.pause()
                ib_playpause.setImageResource(R.mipmap.play_rdi_btn_play)
                mHandler.removeMessages(HIDE_CONTROL_BAR)
                showControlBar()
            } else {
                vm_videoview.start()
                ib_playpause.setImageResource(R.mipmap.play_rdi_btn_pause)
                mHandler.sendEmptyMessageDelayed(HIDE_CONTROL_BAR, HIDE_TIME.toLong())
            }
        }
        ib_screensize.setOnClickListener {
            initScreen(false)
        }
        sbPlay.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when (seekBar?.id) {
                    R.id.sbPlay -> if (fromUser) {
                        vm_videoview.seekTo(progress.toLong())
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //再拖拽进度条时不隐藏上下面板
                mHandler.removeMessages(HIDE_CONTROL_BAR)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //拖拽结束3秒后隐藏上下面板
                mHandler.sendEmptyMessageDelayed(HIDE_CONTROL_BAR, 3000)
            }
        })
        vm_videoview.setOnCompletionListener {
            //当播放完成的时候会执行这个方法
            //把当前播放的时间设置为总时长
            mHandler.removeMessages(CURRENT_PLAY_TIME)
            playTime.text = StringUtils.formatTime(it.duration.toInt())
            //让seekbar 展示的进度移动到最后
            sbPlay.max = sbPlay.max
            nextVideo(true)
        }
    }
    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
        video_loading.visibility = View.VISIBLE
        if (vm_videoview.isPlaying)
            vm_videoview.pause()
        tv_loading_buffer_video.text = "视频已缓冲$percent%..."
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
            toast("未知错误~~")
        }
        return true
    }

    override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        when (what) {
            MediaPlayer.MEDIA_INFO_BUFFERING_START -> {
                if (vm_videoview.isPlaying) {
                    vm_videoview.pause()
                }
                ib_playpause.setImageResource(R.mipmap.play_rdi_btn_play)
                mHandler.removeMessages(HIDE_CONTROL_BAR)
                showControlBar()
            }
        //            完成缓冲
            MediaPlayer.MEDIA_INFO_BUFFERING_END -> {
                video_loading.visibility = View.GONE
                if (!vm_videoview.isPlaying)
                    vm_videoview.start()
                ib_playpause.setImageResource(R.mipmap.play_rdi_btn_pause)
                mHandler.sendEmptyMessageDelayed(HIDE_CONTROL_BAR, HIDE_TIME.toLong())
            }
            MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED -> {
            }
        }
        return true
    }


    override fun initInjector() {
        if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
            return
        //隐藏状态栏
        rlBookReadRoot.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE  //强制为横屏sensorPortrait
        mPicUrls.clear()
        mVideoUrls.clear()
        mTitles.clear()
//        mTitles = intent.getStringArrayListExtra(VIDEO_TITLES)
        val bundle = intent.getBundleExtra(VIDEO_BUNDLE)
        val keySet = bundle.keySet()
        keySet.forEach {
            mPicUrls.add(it)
            mVideoUrls.add(bundle.get(it) as String)
        }

    }

    override fun getContentView(): Int = R.layout.activity_video_play
    companion object {
        var VIDEO_BUNDLE = "videobundle"
//        var VIDEO_TITLES = "videotitles"
        fun open(context: Context, bundle: Bundle){
            context.startActivity<DefaultVideoPlayActivity>(VIDEO_BUNDLE  to bundle)
            (context as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
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
        mHandler.removeCallbacksAndMessages(null)
    }
    /**
     * 对顶部状态栏覆盖toolbar的情况做处理
     */
    fun initStatusBar(){
        val identifierId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if(identifierId>0){
            statusBarHeight =  resources.getDimensionPixelSize(identifierId)
        }
        status_hight.height = statusBarHeight
    }
}