package com.example.happyghost.showtimeforkotlin.ui.book.bookdetail

import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.RxBus.event.ReadEvent
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.HotReviewAdapter
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.RecommendBookListAdapter
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookDetail
import com.example.happyghost.showtimeforkotlin.bean.bookdata.HotReview
import com.example.happyghost.showtimeforkotlin.bean.bookdata.Recommend
import com.example.happyghost.showtimeforkotlin.bean.bookdata.RecommendBookList
import com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent.DaggerBookDetailInfoComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookDetailInfoModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.book.read.ReadActivity
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import com.example.happyghost.showtimeforkotlin.utils.StringUtils
import com.example.happyghost.showtimeforkotlin.wegit.TagColor
import kotlinx.android.synthetic.main.activity_book_detail_info.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.util.ArrayList
import javax.inject.Inject
import kotlin.text.Typography.times

/**
 * @author Zhao Chenping
 * @creat 2017/11/15.
 * @description
 */
class BookDetailInfoActivity: BaseActivity<BookDetailPresent>(),IBookDetailBaseView {
    @Inject lateinit var mHotAdapter:HotReviewAdapter
    @Inject lateinit var mRecommendtAdapter:RecommendBookListAdapter
    private val tagList = ArrayList<String>()
    private var times = 0
    lateinit var recommendBooks:Recommend.RecommendBooks
    private var isJoinedCollections = false
    override fun loadBookDetail(data: BookDetail) {
        Glide.with(this)
                .load(ConsTantUtils.IMG_BASE_URL + data.cover)
                .placeholder(R.mipmap.cover_default)
                .into(ivBookCover)
        tvBookListTitle.text = data.title
        tvBookListAuthor.text = String.format(getString(R.string.book_detail_author), data.author)
        tvCatgory.text = String.format(getString(R.string.book_detail_category), data.cat)
        tvWordCount.text = StringUtils.formatWordCount(data.wordCount)
        tvLatelyUpdate.text = StringUtils.getDescriptionTimeFromDateString(data.updated)
        tvLatelyFollower.text = data.latelyFollower.toString()
        tvRetentionRatio.text = if (TextUtils.isEmpty(data.retentionRatio))
            "-"
        else
            String.format(getString(R.string.book_detail_retention_ratio),
                    data.retentionRatio)
        tvSerializeWordCount.text = if (data.serializeWordCount < 0)
            "-"
        else
            data.serializeWordCount.toString()

        tagList.clear()
        tagList.addAll(data.tags!!)
        times = 0
        showHotWord()

        tvlongIntro.text = data.longIntro
        tvCommunity.text = String.format(getString(R.string.book_detail_community), data.title)
        tvHelpfulYes.text = String.format(getString(R.string.book_detail_post_count), data.postCount)

        recommendBooks = Recommend.RecommendBooks()
        recommendBooks.title = data.title
        recommendBooks._id = data._id
        recommendBooks.cover = data.cover
        recommendBooks.lastChapter = data.lastChapter
        recommendBooks.updated = data.updated!!
        refreshCollectionIcon()

    }

    private fun showHotWord() {
        val start: Int
        val end: Int
        if (times < tagList.size && times + 8 <= tagList.size) {
            start = times
            end = times + 8
        } else if (times < tagList.size - 1 && times + 8 > tagList.size) {
            start = times
            end = tagList.size - 1
        } else {
            start = 0
            end = if (tagList.size > 8) 8 else tagList.size
        }
        times = end
        if (end - start > 0) {
            val batch = tagList.subList(start, end)
            val colors = TagColor.getRandomColors(batch.size)
            tag_group.setTags(colors, *batch.toTypedArray())
        }
    }

    private fun refreshCollectionIcon() {
        if (mPresenter.queryBook(recommendBooks._id!!)) {
            initCollection(false)
        } else {
            initCollection(true)
        }
    }

    private fun initCollection(isInBookRack: Boolean) {
        if (isInBookRack) {
            btnJoinCollection.setText(R.string.book_detail_join_collection)
            val drawable = ContextCompat.getDrawable(this, R.mipmap.book_detail_info_add_img)
            drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            btnJoinCollection.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.shape_common_btn_solid_normal))
            btnJoinCollection.setCompoundDrawables(drawable, null, null, null)
            btnJoinCollection.postInvalidate()
            isJoinedCollections = false
        } else {
            btnJoinCollection.setText(R.string.book_detail_remove_collection)
            val drawable = ContextCompat.getDrawable(this, R.mipmap.book_detail_info_del_img)
            drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            btnJoinCollection.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.btn_join_collection_pressed))
            btnJoinCollection.setCompoundDrawables(drawable, null, null, null)
            btnJoinCollection.postInvalidate()
            isJoinedCollections = true
        }
    }

    override fun loadHotReview(list: List<HotReview.Reviews>) {
        mHotAdapter.replaceData(list)
    }

    override fun loadRecommendBookList(list: List<RecommendBookList.RecommendBook>) {
        mRecommendtAdapter.replaceData(list)
    }

    override fun upDataView() {
        mPresenter.getData()

    }

    override fun initView() {
        initActionBar(common_toolbar, "书籍详情",true)
        RecyclerViewHelper.initRecycleViewV(this,rvHotReview,mHotAdapter,true)
        RecyclerViewHelper.initRecycleViewV(this,rvRecommendBoookList,mRecommendtAdapter,true)
        initClickListener()
    }

    private fun initClickListener() {
        btnJoinCollection.setOnClickListener{
            if (!isJoinedCollections) {
                doAsync {
                    mPresenter.insertBook(recommendBooks)
                    uiThread {
                        AppApplication.instance.mRxBus?.post(ReadEvent(true))
                        toast(String.format(getString(
                                R.string.book_detail_has_joined_the_book_shelf), recommendBooks.title))
                        initCollection(false)
                    }
                }
            } else {
                doAsync {
                    mPresenter.deleteBook(recommendBooks)
                    uiThread {
                        AppApplication.instance.mRxBus?.post(ReadEvent(false))
                        toast(String.format(getString(
                                R.string.book_detail_has_remove_the_book_shelf), recommendBooks.title))
                        initCollection(true)
                    }
                }
            }
        }
        btnRead.setOnClickListener{
            ReadActivity.open(this, recommendBooks)
            overridePendingTransition(R.anim.fade_entry, R.anim.hold)
        }
    }

    override fun initInjector() {
        val mBookId = intent.getStringExtra(BOOK_INFO_ID)
         DaggerBookDetailInfoComponent.builder()
                 .applicationComponent(getAppComponent())
                 .bookDetailInfoModule(BookDetailInfoModule(this,mBookId))
                 .build()
                 .inject(this)
    }

    override fun getContentView():  Int {

        return R.layout.activity_book_detail_info
    }
    companion object {
        var BOOK_INFO_ID:String = "bookid"
        fun open(context: Context, id: String) {
            //context.startActivity<BookDetailInfoActivity>(BOOK_INFO_ID to id)括号里的参数（id）不能为空的参数
            //即不能为open(context: Context, id: String?)这样的形式，必须把‘？’号去掉才可以使用下面的startActivity
            context.startActivity<BookDetailInfoActivity>(BOOK_INFO_ID to id)
            (context as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }
}