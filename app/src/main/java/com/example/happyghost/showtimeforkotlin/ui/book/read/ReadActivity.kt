package com.example.happyghost.showtimeforkotlin.ui.book.read

import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.WindowManager

import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookMixATocBean
import com.example.happyghost.showtimeforkotlin.bean.bookdata.ChapterReadBean
import com.example.happyghost.showtimeforkotlin.bean.bookdata.Recommend
import com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent.DaggerReadComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.ReadModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.FileUtils
import com.example.happyghost.showtimeforkotlin.utils.PreferencesUtils
import com.example.happyghost.showtimeforkotlin.wegit.read.PageWidget
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class ReadActivity : BaseActivity<ReadPresenter>(),IReadView {
    private var statusBarColor: Int = 0
    lateinit var mBookId :String
     var currentChapter = 1
    var chapters = ArrayList<BookMixATocBean.MixTocBean.ChaptersBean>()
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

    }

    override fun upDataView() {
        //显示状态栏
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        //隐藏状态栏
        window.decorView.systemUiVisibility = View.INVISIBLE
        mPresenter. getBookMixAToc(mBookId, "chapters")
    }


    /**
     * 对顶部状态栏覆盖toolbar的情况做处理
     */
    fun initStatusBar(){
        var statusBarHeight : Int =-1
        val identifierId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if(identifierId>0){
            statusBarHeight =  resources.getDimensionPixelSize(identifierId)
        }
        status_hight.height = statusBarHeight
    }

    override fun initView() {
        initPagerWidget()
    }
    fun initPagerWidget(){
        if(PreferencesUtils.getInt(ConsTantUtils.FLIP_STYLE,0)==0){
//            mPageWidget = PageWidget(this, mBookId, chapters, ReadListener())
        }
    }

    override fun initInjector() {
//        initStatusBar()
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

}
