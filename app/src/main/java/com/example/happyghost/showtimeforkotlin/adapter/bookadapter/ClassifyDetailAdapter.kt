package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import android.text.TextUtils
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BooksByCats
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.BookDetailActivity
import com.example.happyghost.showtimeforkotlin.utils.*

/**
 * @author Zhao Chenping
 * @creat 2017/11/15.
 * @description
 */
class ClassifyDetailAdapter:BaseQuickAdapter<BooksByCats.BooksBean,BaseViewHolder>(R.layout.adapter_book_classify_detail_item) {
    override fun convert(helper: BaseViewHolder?, item: BooksByCats.BooksBean?) {
        val imageView = helper?.getView<ImageView>(R.id.ivSubCateCover)
        if (NetUtil.isWifiConnected(mContext)|| SharedPreferencesUtil.isShowImageAlways())
        {
            ImageLoader.loadCenterCrop(mContext, ConsTantUtils.IMG_BASE_URL + item?.cover, imageView!!, R.mipmap.cover_default)
        }
        else
        {
            imageView?.setImageResource(R.mipmap.cover_default)
        }

        helper!!.setText(R.id.tvSubCateTitle, item?.title)
                .setText(R.id.tvSubCateAuthor, (if (item?.author == null) "未知" else item.author) + " | " + (if (item?.majorCate == null) "未知" else item.majorCate))
                .setText(R.id.tvSubCateShort, item?.shortIntro)
                .setText(R.id.tvSubCateMsg, String.format(mContext.getResources().getString(R.string.category_book_msg),
                        item?.latelyFollower,
                        if (TextUtils.isEmpty(item?.retentionRatio)) "0" else item?.retentionRatio))
        helper.itemView.setOnClickListener {
            BookDetailActivity.lunch(mContext, item?._id)
        }
    }
}