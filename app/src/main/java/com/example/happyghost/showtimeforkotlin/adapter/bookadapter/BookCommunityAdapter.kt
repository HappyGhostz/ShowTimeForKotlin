package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdate.BookHelpList
import com.example.happyghost.showtimeforkotlin.ui.book.community.item.BookHelpDetailActivity
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader
import com.example.happyghost.showtimeforkotlin.utils.StringUtils
import de.hdodenhof.circleimageview.CircleImageView

/**
 * @author Zhao Chenping
 * @creat 2017/9/19.
 * @description
 */
class BookCommunityAdapter:BaseQuickAdapter<BookHelpList.HelpsBean,BaseViewHolder>(R.layout.adapter_book_community_item) {
    override fun convert(helper: BaseViewHolder?, item: BookHelpList.HelpsBean?) {
        var latelyUpdate = ""
        if (!TextUtils.isEmpty(StringUtils.getDescriptionTimeFromDateString(item?.updated))) {
            latelyUpdate = StringUtils.getDescriptionTimeFromDateString(item?.updated) + ":"
        }
        helper?.setText(R.id.tvBookTitle, item?.author?.nickname)
                ?.setText(R.id.tvBookType, "lv." + item?.author?.lv + "")
                ?.setText(R.id.tvTitle, item?.title)
                ?.setText(R.id.tvHelpfulYes, item?.commentCount.toString() + "")
        if (TextUtils.equals(item?.state, "distillate")) {
            val tvDistillate = helper?.getView<TextView>(R.id.tvDistillate)
            tvDistillate?.visibility = View.VISIBLE
        } else if (TextUtils.equals(item?.state, "hot")) {
            val tvHot = helper?.getView<TextView>(R.id.tvHot)
            tvHot?.visibility = View.VISIBLE
        } else if (TextUtils.equals(item?.state, "normal")) {
            val tvTime = helper?.getView<TextView>(R.id.tvTime)
            tvTime?.visibility = View.VISIBLE
            tvTime?.text = latelyUpdate
        }
        val imageView = helper?.getView<CircleImageView>(R.id.ivBookCover)
        ImageLoader.loadCenterCrop(mContext, ConsTantUtils.IMG_BASE_URL + item?.author?.avatar, imageView as ImageView, R.mipmap.avatar_default)
        helper.itemView.setOnClickListener { BookHelpDetailActivity.lunch(mContext, item?._id!!) }
    }
}