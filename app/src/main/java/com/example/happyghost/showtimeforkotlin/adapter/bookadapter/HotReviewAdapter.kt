package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdate.HotReview
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.discussion.BookDiscussionDetailActivity
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.GlideCircleTransform
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader
import com.example.happyghost.showtimeforkotlin.wegit.XLHRatingBar

/**
 * @author Zhao Chenping
 * @creat 2017/11/16.
 * @description
 */
class HotReviewAdapter:BaseQuickAdapter<HotReview.Reviews,BaseViewHolder>(R.layout.adapter_hot_review) {
    override fun convert(helper: BaseViewHolder?, item: HotReview.Reviews?) {
        val imageView = helper?.getView<ImageView>(R.id.ivBookCover)
        ImageLoader.loadCenterCropWithTransform(mContext, ConsTantUtils.IMG_BASE_URL + item?.author?.avatar, imageView!!,GlideCircleTransform(mContext), R.mipmap.avatar_default)
        helper!!.setText(R.id.tvBookTitle, item?.author?.nickname)
                .setText(R.id.tvBookType, String.format(mContext.getString(R.string
                        .book_detail_user_lv), item?.author?.lv))
                .setText(R.id.tvTitle, item?.title)
                .setText(R.id.tvContent, item?.content)
                .setText(R.id.tvHelpfulYes, item?.helpful?.yes!!.toString())
        val ratingBar = helper.getView<XLHRatingBar>(R.id.rating)
        ratingBar.setCountSelected(item!!.rating)
        helper.itemView.setOnClickListener {
            BookDiscussionDetailActivity.open(mContext,item._id!!)
        }
    }
}