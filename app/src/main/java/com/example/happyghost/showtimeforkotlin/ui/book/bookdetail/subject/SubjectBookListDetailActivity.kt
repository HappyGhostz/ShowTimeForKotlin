package com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.subject


import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.SubjectBookListAdapter
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookListDetail
import com.example.happyghost.showtimeforkotlin.bean.bookdata.RecommendBookList
import com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent.DaggerSubjectBookListDetailComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.SubjectBookListDetailModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.GlideCircleTransform
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import kotlinx.android.synthetic.main.activity_subject_detail.*
import kotlinx.android.synthetic.main.head_item_subject_book_list.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
class SubjectBookListDetailActivity: BaseActivity<SubjectBookListDetailPresenter>(),IBaseSubjectView {
    @Inject lateinit var mBookListAdapter: SubjectBookListAdapter
    override fun showBookListDetail(data: BookListDetail) {
        val headView = View.inflate(this, R.layout.head_item_subject_book_list, null)
        val title = headView.find<TextView>(R.id.tvBookListTitle)
        title.text = data.getBookList()?.title
        val tvBookListDesc = headView.find<TextView>(R.id.tvBookListDesc)
        tvBookListDesc.text = data.getBookList()?.desc
        val tvBookListAuthor = headView.find<TextView>(R.id.tvBookListAuthor)
        tvBookListAuthor.text = data.getBookList()?.author?.nickname
        val ivAuthorAvatar = headView.find<ImageView>(R.id.ivAuthorAvatar)
        ImageLoader.loadCenterCropWithTransform(this,ConsTantUtils.IMG_BASE_URL + data.getBookList()?.author?.avatar,
                ivAuthorAvatar, GlideCircleTransform(this),R.mipmap.avatar_default)
        if (mBookListAdapter.headerLayout != null) {
            mBookListAdapter.removeAllHeaderView()
            mBookListAdapter.addHeaderView(headView)
        } else {
            mBookListAdapter.addHeaderView(headView)
        }
        mBookListAdapter.replaceData(data.getBookList()?.books!!)
    }

    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView() {
        initActionBar(common_toolbar,"书单详情",true)
        RecyclerViewHelper.initRecycleViewV(this,rvDisscussionComments,mBookListAdapter,false)
    }

    override fun initInjector() {
        val mBook = intent.getParcelableExtra<RecommendBookList.RecommendBook>(RECOMMEND_BOOK)
        val mBookId = mBook.id
        DaggerSubjectBookListDetailComponent.builder()
                .applicationComponent(getAppComponent())
                .subjectBookListDetailModule(SubjectBookListDetailModule(this,mBookId))
                .build()
                .inject(this)
    }

    override fun getContentView(): Int=R.layout.activity_subject_detail
    companion object {
        var RECOMMEND_BOOK="recommendbook"
        fun open(mContext: Context, item: RecommendBookList.RecommendBook) {
            mContext.startActivity<SubjectBookListDetailActivity>(RECOMMEND_BOOK to item)
            (mContext as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }
}