package com.example.happyghost.showtimeforkotlin.ui.book.community.item

import android.app.Activity
import android.content.Context
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.DisscussionAdapter
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.DisscussionBestAdapter
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookHelp
import com.example.happyghost.showtimeforkotlin.bean.bookdata.CommentList
import com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent.DaggerBookHelpDetailComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookHelpDetailModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import com.example.happyghost.showtimeforkotlin.utils.StringUtils
import kotlinx.android.synthetic.main.activity_subject_detail.*
import kotlinx.android.synthetic.main.item_head_help_book.view.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/11/20.
 * @description
 */
class BookHelpDetailActivity: BaseActivity<BookHelpDetailPresenter>(),IBookHelperBaseView {
    @Inject lateinit var mAdapter: DisscussionAdapter
    @Inject lateinit var mHeadAdapter: DisscussionBestAdapter
    lateinit var headView:View
    override fun loadBookHelpDetail(data: BookHelp) {
        headView.tvBookTitle.text = data.help?.author?.nickname
        headView.tvTime.text = StringUtils.getDescriptionTimeFromDateString(data.help?.created)
        headView.tvTitle.text = data.help?.title
        headView.tvContent.text = data.help?.content
        headView.tvCommentCount.text = String.format(this.getString(R.string.comment_comment_count), data.help?.commentCount)
        ImageLoader.loadCenterCrop(this, ConsTantUtils.IMG_BASE_URL + data.help?.author?.avatar, headView.ivBookCover, R.mipmap.avatar_default)
        if (mAdapter.headerLayout != null) {
            mAdapter.removeAllHeaderView()
            mAdapter.addHeaderView(headView)
        } else {
            mAdapter.addHeaderView(headView)
        }
    }

    override fun loadBestComments(list: CommentList) {
        mHeadAdapter.replaceData(list.comments!!)
    }

    override fun loadBookHelpComments(list: CommentList) {
        mAdapter.replaceData(list.comments!!)
    }

    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView() {
        initActionBar(common_toolbar,"书荒互助区详情",true)
        RecyclerViewHelper.initRecycleViewV(this,rvDisscussionComments,mAdapter,true)
        headView = View.inflate(this, R.layout.item_head_help_book, null)
        RecyclerViewHelper.initRecycleViewV(this,headView.rvBestComments,mHeadAdapter,true)
    }
    override fun initInjector() {
        val mBookId = intent.getStringExtra(BOOK_HELP_ID)
        DaggerBookHelpDetailComponent.builder()
                .applicationComponent(getAppComponent())
                .bookHelpDetailModule(BookHelpDetailModule(this,mBookId))
                .build()
                .inject(this)
    }

    override fun getContentView(): Int =R.layout.activity_subject_detail
    companion object {
        var BOOK_HELP_ID = "bookhelpid"
        fun lunch(mContext: Context, _id: String) {
            mContext.startActivity<BookHelpDetailActivity>(BOOK_HELP_ID to _id)
            (mContext as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }
}