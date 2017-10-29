package com.example.happyghost.showtimeforkotlin.ui.book.read

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Point
import android.support.v4.content.ContextCompat
import android.view.View

import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookMixATocBean
import com.example.happyghost.showtimeforkotlin.bean.bookdata.ChapterReadBean
import com.example.happyghost.showtimeforkotlin.bean.bookdata.Recommend
import com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent.DaggerReadComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.ReadModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.FileUtils
import com.example.happyghost.showtimeforkotlin.utils.SharedPreferencesUtil
import com.example.happyghost.showtimeforkotlin.utils.SettingManager
import com.example.happyghost.showtimeforkotlin.wegit.read.OnReadStateChangeListener
import com.example.happyghost.showtimeforkotlin.wegit.read.OverlappedWidget
import com.example.happyghost.showtimeforkotlin.wegit.read.PageWidget
import com.example.happyghost.showtimeforkotlin.wegit.read.ReadView
import com.nineoldandroids.animation.AnimatorSet
import com.nineoldandroids.animation.ObjectAnimator
import kotlinx.android.synthetic.main.activity_read.*
import kotlinx.android.synthetic.main.layout_read_aa_set.*
import kotlinx.android.synthetic.main.layout_read_mark.*
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*

class ReadActivity : BaseActivity<ReadPresenter>(),IReadView {

    private var statusBarColor: Int = 0
    lateinit var mBookId :String
    var currentChapter = 1
    var chapters = ArrayList<BookMixATocBean.MixTocBean.ChaptersBean>()
    lateinit var mPageWidget: ReadView
    var statusBarHeight : Int =-1
    /**
     * 是否开始阅读章节
     */
    private var startRead = false
    var curTheme: Int = 0
    private val sdf = SimpleDateFormat("HH:mm")
    private var receiver = Receiver()
    private val intentFilter = IntentFilter()


    override fun loadBookToc(list: List<BookMixATocBean.MixTocBean.ChaptersBean>) {
        chapters.clear()
        chapters.addAll(list)
        loadCurrentChapter()
    }

    /**
     * 获取当前章节。章节文件存在则直接阅读，不存在则请求
     */
    fun loadCurrentChapter(){
        if(FileUtils.hasChapterFile(mBookId,currentChapter)!=null){
            loadChapterRead(null,currentChapter)
        }else{
            mPresenter.getChapterRead(chapters.get(currentChapter-1).link!!,currentChapter)
        }

    }

    override fun loadChapterRead(data: ChapterReadBean.Chapter?, chapter: Int) {
        if(data!=null){
            FileUtils.saveChapterFile(mBookId,chapter,data)
        }
        if(!startRead){
            startRead=true
            currentChapter = chapter
            if(mPageWidget.isPrepared){
                mPageWidget.init(curTheme)
            }else{
                mPageWidget.jumpToChapter(currentChapter)
            }
        }
    }

    override fun upDataView() {
        //显示状态栏
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        mPresenter. getBookMixAToc(mBookId, "chapters")
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


    override fun initView() {
        //隐藏状态栏
        rlBookReadRoot.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        initPagerWidget()
        curTheme = SettingManager.getInstance()!!.getReadTheme()
    }
    fun initPagerWidget(){
        if(SharedPreferencesUtil.getInt(ConsTantUtils.FLIP_STYLE,0)==0){
            mPageWidget = PageWidget(this, mBookId, chapters, ReadListener())
        }else{
            mPageWidget = OverlappedWidget(this, mBookId, chapters, ReadListener())
        }
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED)
        intentFilter.addAction(Intent.ACTION_TIME_TICK)
        registerReceiver(receiver, intentFilter)
        if (SharedPreferencesUtil.getBoolean(ConsTantUtils.ISNIGHT, false)) {
            mPageWidget.setTextColor(ContextCompat.getColor(this, R.color.chapter_content_night),
                    ContextCompat.getColor(this, R.color.chapter_title_night))
        }
        flReadWidget.removeAllViews()
        flReadWidget.addView(mPageWidget)
    }

    override fun initInjector() {
        initStatusBar()
        val bookBean = intent.getSerializableExtra(BOOK_BEAN)as Recommend.RecommendBooks
        mBookId = bookBean._id!!
        DaggerReadComponent.builder()
                .applicationComponent(getAppComponent())
                .readModule(ReadModule(this))
                .build()
                .inject(this)
    }

    override fun getContentView(): Int {
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        statusBarColor = ContextCompat.getColor(this, R.color.reader_menu_bg_color)
        return R.layout.activity_read
    }
    companion object {
        var BOOK_BEAN = "bookbean"
        fun open(mContext: Context?, recommendBooks: Recommend.RecommendBooks) {
            mContext?.startActivity<ReadActivity>(BOOK_BEAN to recommendBooks)
            (mContext as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }
    inner class ReadListener : OnReadStateChangeListener {
        override fun onChapterChanged(chapter: Int) {
            currentChapter = chapter
            // 加载前一节 与 后三节
            var i = chapter - 1
            while (i <= chapter + 3 && i <= chapters.size) {
                if (i > 0 && i != chapter
                        && FileUtils.hasChapterFile(mBookId, i) == null) {
                    mPresenter.getChapterRead(chapters.get(i - 1).link!!, i)
                }
                i++
            }
        }

        override fun onPageChanged(chapter: Int, page: Int) {

        }

        override fun onLoadChapterFailure(chapter: Int) {
            startRead=false
            if(FileUtils.hasChapterFile(mBookId,chapter)==null){
                mPresenter.getChapterRead(chapters.get(chapter - 1).link!!, chapter)
            }
        }

        override fun onCenterClick() {
            toggleReadBar()
        }

        override fun onFlip() {
            hideReadBar()
        }

    }
     //电量时间广播
    inner class Receiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (mPageWidget != null) {
                if (Intent.ACTION_BATTERY_CHANGED == intent.action) {
                    val level = intent.getIntExtra("level", 0)
                    mPageWidget.setBattery(100 - level)
                } else if (Intent.ACTION_TIME_TICK == intent.action) {
                    mPageWidget.setTime(sdf.format(Date()))
                }
            }
        }
    }

    @Synchronized
    private fun toggleReadBar() {
        if (llBookReadTop.visibility == View.VISIBLE) {
            hideReadBar()
        } else {
            showReadBar()
        }
    }
    @Synchronized
    private fun hideReadBar(){
        getMeasureHeight(llBookReadBottom)
        val measuredHeight = llBookReadBottom.measuredHeight.toFloat()
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        val height = point.y.toFloat()
        val llBookReadBottomGone = ValueAnimator.ofFloat(measuredHeight)
        llBookReadBottomGone.addUpdateListener {
            val animatedValue = it.animatedValue as Float
            llBookReadBottom.translationY=animatedValue
        }
        llBookReadBottomGone.duration = 500
        llBookReadBottomGone.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                gone(llBookReadTop,llBookReadBottom,tvDownloadProgress,
                        rlReadAaSet,rlReadMark,status_hight)
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        llBookReadBottomGone.start()


        rlBookReadRoot.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }
    @Synchronized
    private fun showReadBar(){
        getMeasureHeight(llBookReadBottom)
        getMeasureHeight(status_hight)
        getMeasureHeight(llBookReadTop1)
        val measuredHeight = llBookReadBottom.measuredHeight.toFloat()
        val bookReadTopHeight1 = llBookReadTop1.measuredHeight.toFloat()
        val status_hight = status_hight.measuredHeight.toFloat()
        val bookTopHeight = bookReadTopHeight1 + status_hight
        val animatorYReadBottom = ObjectAnimator.ofFloat(llBookReadBottom, "translationY",
                measuredHeight,0f)
        animatorYReadBottom.duration=500
        animatorYReadBottom.start()

        val animatorYReadTop = ObjectAnimator.ofFloat(llBookReadTop, "translationY",
                -bookTopHeight,0f)
        animatorYReadTop.duration=500
        animatorYReadTop.start()


//        llBookReadBottom.animate().alpha(-llBookReadBottom.height.toFloat()).setDuration(500).start()
        visible(llBookReadBottom,llBookReadTop)
        //显示状态栏
        rlBookReadRoot.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    private fun getMeasureHeight(view:View) {
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(w, h)
    }

}
