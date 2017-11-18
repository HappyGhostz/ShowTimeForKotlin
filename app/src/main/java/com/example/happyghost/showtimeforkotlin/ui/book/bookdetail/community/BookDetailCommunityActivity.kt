package com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.community

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.ViewPagerAdapter
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import kotlinx.android.synthetic.main.activity_book_detail_community.*
import org.jetbrains.anko.startActivity
import java.util.*

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
class BookDetailCommunityActivity: BaseActivity<IBasePresenter>() {
    lateinit var bookId:String
    lateinit var title:String
    var index:Int = 0
    lateinit var adapter:ViewPagerAdapter
    override fun upDataView() {
        initDate()
    }

    override fun initView() {
//        var mDatas = Arrays.asList("讨论","评论")
        initActionBar(common_toolbar,title,true)
        adapter = ViewPagerAdapter(supportFragmentManager)
//        indicatorSubRank.setTabItemTitles(mDatas)
//        viewpagerSubRank.offscreenPageLimit = 2
        viewpagerSubRank.adapter=adapter
        viewpagerSubRank.currentItem = index
        tab_book_layout.setupWithViewPager(viewpagerSubRank)
//        indicatorSubRank.setViewPager(viewpagerSubRank,index)
    }

    private fun initDate() {
        var titles = ArrayList<String>()
        titles.add("讨论")
        titles.add("评论")
        var mTabContents =ArrayList<Fragment>()
        mTabContents.add(BookDetailDiscussionFragment.newInstance(bookId))
        mTabContents.add(BookDetailReviewFragment.newInstance(bookId))
        adapter.setItems(mTabContents,titles)
    }

    override fun initInjector() {
        bookId = intent.getStringExtra(BOOK_ID)
        title = intent.getStringExtra(BOOK_TITLE)
        index = intent.getIntExtra(PAGE, 0)
    }

    override fun getContentView(): Int = R.layout.activity_book_detail_community
    companion object {
        var BOOK_ID = "bookid"
        var BOOK_TITLE = "booktitle"
        var PAGE = "bookpage"
        fun startActivity(context: Context, mBookId: String, title: String, page: Int) {
            context.startActivity<BookDetailCommunityActivity>(BOOK_ID to mBookId,BOOK_TITLE to title, PAGE to page)
            (context as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }
}