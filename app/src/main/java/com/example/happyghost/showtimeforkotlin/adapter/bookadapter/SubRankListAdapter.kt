package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import android.text.TextUtils
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdate.Rankings
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.BookDetailInfoActivity
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader

/**
 * @author Zhao Chenping
 * @creat 2017/11/20.
 * @description
 */
class SubRankListAdapter:BaseQuickAdapter<Rankings.RankingBean.BooksBean,BaseViewHolder>(R.layout.adapter_book_classify_detail_item) {
    override fun convert(helper: BaseViewHolder?, item: Rankings.RankingBean.BooksBean?) {
        val imageView = helper?.getView<ImageView>(R.id.ivSubCateCover)
        ImageLoader.loadCenterCrop(mContext, ConsTantUtils.IMG_BASE_URL + item?.cover, imageView!!, R.mipmap.avatar_default)
        helper.setText(R.id.tvSubCateTitle, item?.title)
                .setText(R.id.tvSubCateAuthor, (if (item?.author == null) "未知" else item?.author) + " | " + if (item?.cat == null) "未知" else item?.cat)
                .setText(R.id.tvSubCateShort, item?.shortIntro)
                .setText(R.id.tvSubCateMsg, String.format(mContext.resources.getString(R.string.category_book_msg),
                        item?.latelyFollower,
                        if (TextUtils.isEmpty(item?.retentionRatio)) "0" else item?.retentionRatio))
        helper.itemView.setOnClickListener { BookDetailInfoActivity.open(mContext, item?._id!!) }
    }
}