package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdate.BookListDetail
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.BookDetailInfoActivity
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
class SubjectBookListAdapter:BaseQuickAdapter<BookListDetail.BookListBean.BooksBean,BaseViewHolder>(R.layout.adapter_subject_item) {
    override fun convert(helper: BaseViewHolder?, item: BookListDetail.BookListBean.BooksBean?) {
        val imageView = helper!!.getView<ImageView>(R.id.ivBookCover)
        ImageLoader.loadCenterCrop(mContext,ConsTantUtils.IMG_BASE_URL + item?.book?.cover,imageView,R.mipmap.cover_default)
        helper!!.setText(R.id.tvBookListTitle, item?.book?.title)
                .setText(R.id.tvBookAuthor, item?.book?.author)
                .setText(R.id.tvBookLatelyFollower, String.format(mContext.resources.getString(R.string.subject_book_list_detail_book_lately_follower),
                        item?.book?.latelyFollower))
                .setText(R.id.tvBookWordCount, String.format(mContext.resources.getString(R.string.subject_book_list_detail_book_word_count),
                        item?.book?.wordCount!! / 10000))
                .setText(R.id.tvBookDetail, item?.book?.longIntro)
        helper?.itemView.setOnClickListener {
            BookDetailInfoActivity.open(mContext, item.book!!._id!!)
        }
    }
}