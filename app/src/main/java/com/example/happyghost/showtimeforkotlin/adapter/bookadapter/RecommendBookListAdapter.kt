package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdata.RecommendBookList
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader
import com.example.happyghost.showtimeforkotlin.utils.NetUtil
import com.example.happyghost.showtimeforkotlin.utils.SharedPreferencesUtil

/**
 * @author Zhao Chenping
 * @creat 2017/11/16.
 * @description
 */
class RecommendBookListAdapter:BaseQuickAdapter<RecommendBookList.RecommendBook,BaseViewHolder>(R.layout.adapter_recommend_list) {
    override fun convert(helper: BaseViewHolder?, item: RecommendBookList.RecommendBook?) {
        val imageView = helper?.getView<ImageView>(R.id.ivBookListCover)
        if (NetUtil.isWifiConnected(mContext)||SharedPreferencesUtil.isShowImageAlways()) {
            ImageLoader.loadCenterCrop(mContext, ConsTantUtils.IMG_BASE_URL + item?.cover, imageView!!, R.mipmap.avatar_default)
        }

        helper?.setText(R.id.tvBookListTitle, item?.title)
                ?.setText(R.id.tvBookAuthor, item?.author)
                ?.setText(R.id.tvBookListTitle, item?.title)
                ?.setText(R.id.tvBookListDesc, item?.desc)
                ?.setText(R.id.tvBookCount, String.format(mContext.getString(R.string
                        .book_detail_recommend_book_list_book_count), item?.bookCount))
                ?.setText(R.id.tvCollectorCount, String.format(mContext.getString(R.string
                        .book_detail_recommend_book_list_collector_count), item?.collectorCount))
    }
}