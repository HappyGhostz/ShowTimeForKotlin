package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdata.DiscussionList
import com.example.happyghost.showtimeforkotlin.utils.*

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
class BookDetailDiscussionAdapter:BaseQuickAdapter<DiscussionList.PostsBean,BaseViewHolder>(R.layout.adapter_detail_discussion) {
    override fun convert(helper: BaseViewHolder?, item: DiscussionList.PostsBean?) {
        val imageView = helper!!.getView<ImageView>(R.id.ivBookCover)
        ImageLoader.loadCenterCropWithTransform(mContext,ConsTantUtils.IMG_BASE_URL + item?.author?.avatar,imageView, GlideCircleTransform(mContext),R.mipmap.avatar_default)

        helper.setText(R.id.tvBookTitle, item?.author?.nickname)
                .setText(R.id.tvBookType, String.format(mContext.getString(R.string.book_detail_user_lv), item?.author?.lv))
                .setText(R.id.tvTitle, item?.title)
                .setText(R.id.tvHelpfulYes, item?.commentCount.toString() + "")
                .setText(R.id.tvLikeCount, item?.likeCount.toString() + "")

        try {
            val textView = helper.getView<TextView>(R.id.tvHelpfulYes)
            if (item?.type.equals("vote")) {
                val drawable = ContextCompat.getDrawable(mContext, R.mipmap.ic_notif_vote)
                drawable.setBounds(0, 0, ScreenUtils.dpToPxInt(15f), ScreenUtils.dpToPxInt(15f))
                textView.setCompoundDrawables(drawable, null, null, null)
            } else {
                val drawable = ContextCompat.getDrawable(mContext, R.mipmap.ic_notif_post)
                drawable.setBounds(0, 0, ScreenUtils.dpToPxInt(15f), ScreenUtils.dpToPxInt(15f))
                textView.setCompoundDrawables(drawable, null, null, null)
            }

            if (TextUtils.equals(item?.state, "hot")) {
                helper.setVisible(R.id.tvHot, true)
                helper.setVisible(R.id.tvTime, false)
                helper.setVisible(R.id.tvDistillate, false)
            } else if (TextUtils.equals(item?.state, "distillate")) {
                helper.setVisible(R.id.tvDistillate, true)
                helper.setVisible(R.id.tvHot, false)
                helper.setVisible(R.id.tvTime, false)
            } else {
                helper.setVisible(R.id.tvTime, true)
                helper.setVisible(R.id.tvHot, false)
                helper.setVisible(R.id.tvDistillate, false)
                helper.setText(R.id.tvTime, StringUtils.getDescriptionTimeFromDateString(item?.created))
            }
        } catch (e: Exception) {
        }

    }
}