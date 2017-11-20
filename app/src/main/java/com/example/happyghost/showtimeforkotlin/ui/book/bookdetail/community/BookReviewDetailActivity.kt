package com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.community

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.DisscussionAdapter
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.DisscussionBestAdapter
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookReview
import com.example.happyghost.showtimeforkotlin.bean.bookdata.CommentList
import com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent.DaggerBookReviewDetailComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookReviewDetailModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.BookDetailInfoActivity
import com.example.happyghost.showtimeforkotlin.utils.*
import kotlinx.android.synthetic.main.activity_subject_detail.*
import kotlinx.android.synthetic.main.item_head_book_review_detail.view.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/11/20.
 * @description
 */
class BookReviewDetailActivity:BaseActivity<BookReviewDetailPresenter>(),IReviewBaseView {
    @Inject lateinit var mAdapter: DisscussionAdapter
    @Inject lateinit var mHeadAdapter: DisscussionBestAdapter
    lateinit var view: View
    override fun showBookReviewDetail(data: BookReview) {
        val imageView = view.find<ImageView>(R.id.ivAuthorAvatar)
        ImageLoader.loadCenterCropWithTransform(this,ConsTantUtils.IMG_BASE_URL+data.review?.author?.avatar,
                imageView,GlideCircleTransform(this),R.mipmap.avatar_default)
        view.tvBookAuthor.text = data.review?.author?.nickname
        view.tvTime.text = StringUtils.getDescriptionTimeFromDateString(data.review?.created)
        view.tvTitle.text = data.review?.title
        view.tvContent.text = data.review?.content
        val ivBookCover = view.find<ImageView>(R.id.ivBookCover)
        ImageLoader.loadCenterCrop(this,ConsTantUtils.IMG_BASE_URL+data.review?.book?.cover,
                ivBookCover,R.mipmap.cover_default)
        view.tvBookTitle.text = data.review?.book?.title
        view.tvHelpfullYesCount.text = data.review?.helpful?.yes.toString()
        view.tvHelpfullNoCount.text = data.review?.helpful?.no.toString()
        view.tvCommentCount.text = String.format(this.getString(R.string.comment_comment_count), data.review?.commentCount)
        view.rlBookInfo.setOnClickListener{ BookDetailInfoActivity.open(this@BookReviewDetailActivity, data.review?.book?._id!!) }
        view.rating.setCountSelected(data.review?.rating!!)


    }

    override fun showBestComments(list: CommentList) {
        if (list.comments!!.isEmpty()) {
            gone(view.tvBestComments, view.rvBestComments)
        } else {
            mHeadAdapter.replaceData(list.comments!!)
            visible(view.tvBestComments, view.rvBestComments)
        }
    }

    override fun showBookReviewComments(list: CommentList) {
        mAdapter.replaceData(list.comments!!)
    }

    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView() {
        initActionBar(common_toolbar,"书评详情",true)
        RecyclerViewHelper.initRecycleViewV(this,rvDisscussionComments,mAdapter,true)
        view = View.inflate(this, R.layout.item_head_book_review_detail, null)
        RecyclerViewHelper.initRecycleViewV(this,view.rvBestComments,mHeadAdapter,true)
        if (mAdapter.headerLayout != null) {
            mAdapter.removeAllHeaderView()
            mAdapter.addHeaderView(view)
        } else {
            mAdapter.addHeaderView(view)
        }

    }

    override fun initInjector() {
        val mBookId = intent.getStringExtra(REVIEW_BOOK_ID)
        DaggerBookReviewDetailComponent.builder()
                .applicationComponent(getAppComponent())
                .bookReviewDetailModule(BookReviewDetailModule(this,mBookId))
                .build()
                .inject(this)
    }

    override fun getContentView(): Int =R.layout.activity_subject_detail
    companion object {
        var REVIEW_BOOK_ID="reviewbookid"
        fun open(mContext: Context, _id: String) {
            mContext.startActivity<BookReviewDetailActivity>(REVIEW_BOOK_ID to _id)
            (mContext as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }
}