package com.example.happyghost.showtimeforkotlin.ui.book.read

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.BookCatalogueAdapter
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookMixATocBean
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_catalogue.*
import org.jetbrains.anko.startActivity
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
    companion object {
        var BOOK_ID = "bookid"
        var BOOK_CHAPTER = "bookChapter"
        var BOOK_NAME="bookname"
        fun open(mContext: Context?, mBookId: String, currentChapter: Int, title: String) {
            mContext?.startActivity<CatalogueListActivity>( BOOK_ID to mBookId,
                    BOOK_CHAPTER to currentChapter,BOOK_NAME to title)
            (mContext as Activity).overridePendingTransition(R.anim.slide_left_exit, R.anim.fade_exit)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_catalogue)
        val bookid = intent.getStringExtra(BOOK_ID)
        val bookName = intent.getStringExtra(BOOK_NAME)
        val currentChapter = intent.getIntExtra(BOOK_CHAPTER, 0)
        initData(bookid,currentChapter,bookName)
        initView()
    }

    private fun initView() {
        if(catalogues!=null){
            iv_sort.setOnClickListener {
                val rotationAnimator = ObjectAnimator.ofFloat(iv_sort, "rotation", 180f)
                rotationAnimator.duration=500
                rotationAnimator.start()
                rotationAnimator.addListener(object :Animator.AnimatorListener{
                    override fun onAnimationRepeat(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        Collections.reverse(catalogues)
                    }

                    override fun onAnimationCancel(animation: Animator?) {

                    }

                    override fun onAnimationStart(animation: Animator?) {

                    }
                })
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
                            catalogues!!.addAll(chapters)
                            adapter = BookCatalogueAdapter(catalogues!!, bookid, currentChapter)
                            RecyclerViewHelper.initRecycleViewV(AppApplication.instance.getContext(),catalogueList,
                                    adapter,true)
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
}