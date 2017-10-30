package com.example.happyghost.showtimeforkotlin.ui.book.read

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.BookCatalogueAdapter
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookMixATocBean
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import kotlinx.android.synthetic.main.activity_catalogue.*
import org.jetbrains.anko.startActivity
import java.util.ArrayList

/**
 * @author Zhao Chenping
 * @creat 2017/10/30.
 * @description
 */
class CatalogueListActivity : AppCompatActivity() {
    companion object {
        var BOOK_LIST = "booklist"
        var BOOK_ID = "bookid"
        var BOOK_CHAPTER = "bookChapter"
        fun open(mContext: Context?, catalogueList: ArrayList<BookMixATocBean.MixTocBean.ChaptersBean>,
                 mBookId: String, currentChapter: Int) {
            mContext?.startActivity<CatalogueListActivity>(BOOK_LIST to catalogueList, BOOK_ID to mBookId,
                    BOOK_CHAPTER to currentChapter)
            (mContext as Activity).overridePendingTransition(R.anim.slide_left_entry, R.anim.slide_left_exit)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_catalogue)
        val mCatalogues = intent.getParcelableArrayListExtra<BookMixATocBean.MixTocBean.ChaptersBean>(BOOK_LIST)
        val bookid = intent.getStringExtra(BOOK_ID)
        val currentChapter = intent.getIntExtra(BOOK_CHAPTER, 0)
        initData(mCatalogues,bookid,currentChapter)
    }

    private fun initData(mCatalogues: ArrayList<BookMixATocBean.MixTocBean.ChaptersBean>, bookid: String, currentChapter: Int) {
        var adapter = BookCatalogueAdapter(mCatalogues,bookid,currentChapter)
        RecyclerViewHelper.initRecycleViewV(this,catalogueList,adapter,true)
    }
}