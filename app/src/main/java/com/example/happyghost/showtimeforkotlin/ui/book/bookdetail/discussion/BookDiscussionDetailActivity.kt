package com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.discussion

import android.app.Activity
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.DisscussionAdapter
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.DisscussionBestAdapter
import com.example.happyghost.showtimeforkotlin.bean.bookdata.CommentList
import com.example.happyghost.showtimeforkotlin.bean.bookdata.Disscussion
import com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent.DaggerBookDiscussionDetailComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookDiscussionDetailModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.utils.*
import kotlinx.android.synthetic.main.activity_book_detial_discussion.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
class BookDiscussionDetailActivity : BaseActivity<BookDiscussionDetailPresenter>(),IBaseDiscussionView {
    @Inject lateinit var mBestAdapter: DisscussionBestAdapter
    @Inject lateinit var mDisscussionAdapter: DisscussionAdapter
    override fun showBookDisscussionDetail(disscussion: Disscussion) {
        ImageLoader.loadCenterCropWithTransform(this,ConsTantUtils.IMG_BASE_URL + disscussion.post?.author?.avatar,
                ivBookCover, GlideCircleTransform(this), R.mipmap.avatar_default)

        tvBookTitle.text = disscussion.post?.author?.nickname
        tvTime.text = StringUtils.getDescriptionTimeFromDateString(disscussion.post?.created)
        tvTitle.text = disscussion.post?.title
        tvContent.text = disscussion.post?.content
        tvCommentCount.text = String.format(this.getString(R.string.comment_comment_count), disscussion.post?.commentCount)
    }

    override fun showBestComments(list: CommentList) {
        if (list.comments!!.isEmpty()) {
            gone(tvBestComments, rvBestComments)
        } else {
            mBestAdapter.replaceData(list.comments!!)
            visible(tvBestComments,rvBestComments)
        }
    }

    override fun showBookDisscussionComments(list: CommentList) {
        mDisscussionAdapter.replaceData(list.comments!!)
        visible(rvDisscussionComments)
    }

    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView() {
        initActionBar(common_toolbar,"详情",true)
        RecyclerViewHelper.initRecycleViewV(this,rvBestComments,mBestAdapter,true)
        RecyclerViewHelper.initRecycleViewV(this,rvDisscussionComments,mDisscussionAdapter,true)

    }

    override fun initInjector() {
        val mCommendId = intent.getStringExtra(COMMENT_ID)
        DaggerBookDiscussionDetailComponent.builder()
                .applicationComponent(getAppComponent())
                .bookDiscussionDetailModule(BookDiscussionDetailModule(this,mCommendId))
                .build()
                .inject(this)
    }

    override fun getContentView(): Int = R.layout.activity_book_detial_discussion
    companion object {
        var COMMENT_ID = "commentid"
        fun open(mContext: Context, _id: String) {
            mContext.startActivity<BookDiscussionDetailActivity>(COMMENT_ID to _id)
            (mContext as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }
}