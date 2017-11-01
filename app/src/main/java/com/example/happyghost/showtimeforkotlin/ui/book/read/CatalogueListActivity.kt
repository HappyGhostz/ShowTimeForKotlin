package com.example.happyghost.showtimeforkotlin.ui.book.read

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.RxBus.RxBus
import com.example.happyghost.showtimeforkotlin.RxBus.event.ReadEvent
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.BookCatalogueAdapter
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookMixATocBean
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.utils.SettingManager
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_catalogue.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Zhao Chenping
 * @creat 2017/10/30.
 * @description
 */
class CatalogueListActivity : AppCompatActivity() {
    var catalogues: ArrayList<BookMixATocBean.MixTocBean.ChaptersBean>? = null
    lateinit var adapter:BookCatalogueAdapter
    var sortCatalogues = ArrayList<BookMixATocBean.MixTocBean.ChaptersBean>()
    var angle:Float=0f
    var isReversal = false
    var isSameBook = false
    /**
     * 目标项是否在最后一个可见项之后
     */
    private var mShouldScroll: Boolean = false
    /**
     * 记录目标项位置
     */
    private var mToPosition: Int = 0
    val hasBookid:String=""
    companion object {
        var BOOK_ID = "bookid"
        var BOOK_CHAPTER = "bookChapter"
        var BOOK_NAME="bookname"
        fun open(mContext: Context?, mBookId: String, currentChapter: Int, title: String) {
            mContext?.startActivity<CatalogueListActivity>( BOOK_ID to mBookId,
                    BOOK_CHAPTER to currentChapter,BOOK_NAME to title)
            (mContext as Activity).overridePendingTransition(R.anim.slide_left_entry, R.anim.slide_right_exit)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_catalogue)
        val bookid = intent.getStringExtra(BOOK_ID)
        val bookName = intent.getStringExtra(BOOK_NAME)
        val currentChapter = intent.getIntExtra(BOOK_CHAPTER, 0)
        initCatalogueSort(currentChapter,bookid)
        initData(bookid,currentChapter,bookName)
        initView(bookid,currentChapter)
        initGoBackRad()
    }

    private fun initCatalogueSort(currentChapter: Int, bookid: String) {
        val hasBookid = SettingManager.getInstance()?.getCataLoguesBookId(bookid)
        isSameBook=TextUtils.equals(hasBookid,bookid)
        val hasReversal = SettingManager.getInstance()?.getCatalogues()

        if(hasReversal!!&&isSameBook){
            iv_sort.animate().rotation(180f).setDuration(500).start()
            angle+=180f
            isReversal = hasReversal
        }
    }

    private fun initView(bookid: String, currentChapter: Int) {
        if(catalogues!=null){
            iv_sort.setOnClickListener {
                if(catalogues==null){
                    toast("数据正在初始化，请稍后再试!")
                }else{
                    sortCatalogues.clear()
                    val rotationAnimator = ObjectAnimator.ofFloat(iv_sort, "rotation", 180f+angle)
                    rotationAnimator.duration=500
                    rotationAnimator.start()
                    rotationAnimator.addListener(object :Animator.AnimatorListener{
                        override fun onAnimationRepeat(animation: Animator?) {

                        }

                        override fun onAnimationEnd(animation: Animator?) {
                            if(isReversal){
                                adapter.mIsReversal=true
                                adapter.mIsSameBook=true
                            }else{
                                adapter.mIsReversal=false
                                adapter.mIsSameBook=false
                            }
                            Collections.reverse(catalogues)
                            sortCatalogues.addAll(catalogues!!)
                            adapter.replaceData(sortCatalogues)
                            if(isReversal){
                                smoothMoveToPosition(catalogueList, sortCatalogues.size-currentChapter)
                            }else{
                                smoothMoveToPosition(catalogueList, currentChapter-1)
                            }

                        }

                        override fun onAnimationCancel(animation: Animator?) {

                        }

                        override fun onAnimationStart(animation: Animator?) {

                        }
                    })
                    angle+=180f
                    isReversal=!isReversal
                    SettingManager.getInstance()?.saveCatalogues(isReversal)
                    SettingManager.getInstance()?.saveCataLoguesBookId(bookid)
                }
            }
        }
    }

    private fun initData(bookid: String, currentChapter: Int,name:String) {
        bookName.text = name
        catalogues = ArrayList<BookMixATocBean.MixTocBean.ChaptersBean>()
        RetrofitService.getBookMixATocInfo(bookid,"chapters")
                .doOnSubscribe {
                    empty_loading.visibility= View.VISIBLE
                }
                .subscribe(object :Observer<BookMixATocBean>{
                    override fun onNext(t: BookMixATocBean) {
                        val chapters = t.getMixToc()?.chapters
                        if(chapters!=null){
                            if(isReversal&&isSameBook){
                                Collections.reverse(chapters)
                            }
                            catalogues!!.addAll(chapters)
                            adapter = BookCatalogueAdapter(catalogues!!, bookid, currentChapter,isReversal,isSameBook)
                            RecyclerViewHelper.initRecycleViewV(AppApplication.instance.getContext(),catalogueList,
                                    adapter,true)
                            if(isReversal&&isSameBook){
                                smoothMoveToPosition(catalogueList, catalogues!!.size - currentChapter)
                            }else{
                                smoothMoveToPosition(catalogueList, currentChapter-1)
                            }
                            adapter.setOnItemClickListener { adapter, view, position ->
                                val chaptersBean = adapter.getItem(position) as BookMixATocBean.MixTocBean.ChaptersBean
                                if(isReversal&&isSameBook){
                                    val chapterLog = catalogues!!.size - position
                                    RxBus.intanceBus?.post(ReadEvent(chaptersBean,bookid,chapterLog))
                                }else{
                                    RxBus.intanceBus?.post(ReadEvent(chaptersBean,bookid,position+1))
                                }
                                finish()
                                overridePendingTransition(R.anim.slide_right_entry,R.anim.slide_left_exit)
                            }
                        }
                    }

                    override fun onComplete() {
                        empty_loading.visibility= View.GONE
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }
                })

    }
    private fun initGoBackRad() {
        goBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.slide_right_entry,R.anim.slide_left_exit)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.slide_right_entry,R.anim.slide_left_exit)
    }




    /**
     * 滑动到指定位置
     *
     * @param RecyclerView?
     * @param position
     */
    private fun smoothMoveToPosition(catalogueList: RecyclerView?, position: Int) {
        // 第一个可见位置
        val fristItem = catalogueList?.getChildLayoutPosition(catalogueList?.getChildAt(0))
        // 最后一个可见位置
        val lastPosition = catalogueList?.getChildLayoutPosition(catalogueList?.getChildAt(catalogueList?.childCount))
        if(position<fristItem!!){
            // 如果跳转位置在第一个可见位置之前，就smoothScrollToPosition可以直接跳转
            catalogueList.scrollToPosition(position)
        }else if(position>lastPosition!!){
            // 如果要跳转的位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            catalogueList.scrollToPosition(position)
            mToPosition=position
            mShouldScroll=true
        }else{
            // 跳转位置在第一个可见项之后，最后一个可见项之前
            // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
            var movePosition = position - fristItem
            if(movePosition>=0&&movePosition<catalogueList.childCount){
                val top = catalogueList.getChildAt(movePosition).top
                catalogueList.scrollBy(0,top)
            }
        }
    }
}