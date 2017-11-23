package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdate.CommentList
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
class DisscussionBestAdapter:BaseQuickAdapter<CommentList.CommentsBean,BaseViewHolder>(R.layout.adapter_best_comment) {
    override fun convert(helper: BaseViewHolder?, item: CommentList.CommentsBean?) {
        val imageView = helper?.getView<ImageView>(R.id.ivBookCover)
        ImageLoader.loadCenterCrop(mContext,ConsTantUtils.IMG_BASE_URL + item?.author?.avatar,imageView!!, R.mipmap.avatar_default)
        helper.setText(R.id.tvBookTitle, item?.author?.nickname)
                .setText(R.id.tvContent, item?.content)
                .setText(R.id.tvBookType, String.format(mContext.getString(R.string.book_detail_user_lv), item?.author?.lv))
                .setText(R.id.tvFloor, String.format(mContext.getString(R.string.comment_floor), item?.floor))
                .setText(R.id.tvLikeCount, String.format(mContext.getString(R.string.comment_like_count), item?.likeCount))
    }
}