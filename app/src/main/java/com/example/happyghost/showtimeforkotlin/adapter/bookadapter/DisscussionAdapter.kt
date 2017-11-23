package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdate.CommentList
import com.example.happyghost.showtimeforkotlin.utils.*

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
class DisscussionAdapter:BaseQuickAdapter<CommentList.CommentsBean,BaseViewHolder>(R.layout.adapter_disscussion_comment) {
    override fun convert(helper: BaseViewHolder?, item: CommentList.CommentsBean?) {
        val imageView = helper?.getView<ImageView>(R.id.ivBookCover)
        ImageLoader.loadCenterCrop(mContext, ConsTantUtils.IMG_BASE_URL + item?.author?.avatar,imageView!!, R.mipmap.avatar_default)
        helper.setText(R.id.tvBookTitle, item?.author?.nickname)
                .setText(R.id.tvContent, item?.content)
                .setText(R.id.tvBookType, String.format(mContext.getString(R.string.book_detail_user_lv), item?.author?.lv))
                .setText(R.id.tvFloor, String.format(mContext.getString(R.string.comment_floor), item?.floor))
                .setText(R.id.tvTime, StringUtils.getDescriptionTimeFromDateString(item?.created))

        if (item?.replyTo == null) {
            helper.setVisible(R.id.tvReplyNickName, false)
            helper.setVisible(R.id.tvReplyFloor, false)
        } else {
            helper.setText(R.id.tvReplyNickName, String.format(mContext.getString(R.string.comment_reply_nickname), item.replyTo!!.author?.nickname))
                    .setText(R.id.tvReplyFloor, String.format(mContext.getString(R.string.comment_reply_floor), item.replyTo!!.floor))
            helper.setVisible(R.id.tvReplyNickName, true)
            helper.setVisible(R.id.tvReplyFloor, true)
        }
    }
}