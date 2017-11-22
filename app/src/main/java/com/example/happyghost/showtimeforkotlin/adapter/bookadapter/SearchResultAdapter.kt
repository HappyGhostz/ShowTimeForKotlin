package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import android.text.TextUtils
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdata.SearchDetail
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.BookDetailInfoActivity
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader
import com.example.happyghost.showtimeforkotlin.utils.NetUtil
import com.example.happyghost.showtimeforkotlin.utils.SharedPreferencesUtil

/**
 * @author Zhao Chenping
 * @creat 2017/11/22.
 * @description
 */
class SearchResultAdapter:BaseQuickAdapter<SearchDetail.SearchBooks,BaseViewHolder>(R.layout.item_book_seach_result) {
    override fun convert(helper: BaseViewHolder?, item: SearchDetail.SearchBooks?) {
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
                .setText(R.id.tvSubCateAuthor, (if (item?.author == null) "未知" else item.author) + " | " + (if (item?.cat == null) "未知" else item.cat))
                .setText(R.id.tvSubCateShort, item?.shortIntro)
                .setText(R.id.tvSubCateMsg, String.format(mContext.getResources().getString(R.string.category_book_msg),
                        item?.latelyFollower,
                        if (TextUtils.isEmpty(item?.retentionRatio)) "0" else item?.retentionRatio))
        helper.itemView.setOnClickListener {
            BookDetailInfoActivity.open(mContext, item?._id!!)
        }
    }
}