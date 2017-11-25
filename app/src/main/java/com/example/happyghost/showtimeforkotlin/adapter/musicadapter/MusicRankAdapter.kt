package com.example.happyghost.showtimeforkotlin.adapter.musicadapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.musicdate.RankingListItem
import com.example.happyghost.showtimeforkotlin.utils.DefIconFactory
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader

/**
 * @author Zhao Chenping
 * @creat 2017/11/25.
 * @description
 */
class MusicRankAdapter:BaseQuickAdapter<RankingListItem.RangkingDetail,BaseViewHolder>(R.layout.adapter_music_rank_item) {
    override fun convert(helper: BaseViewHolder?, item: RankingListItem.RangkingDetail?) {
        val imageView = helper!!.getView<ImageView>(R.id.iv_ranking_photo)
        ImageLoader.loadCenterCrop(mContext, item?.pic_s192!!, imageView, DefIconFactory.provideIcon())

        val content = item.content
        val info1 = content?.get(0)
        val title1 = info1?.title
        val author1 = info1?.author
        val info2 = content?.get(1)
        val title2 = info2?.title
        val author2 = info2?.author
        val info3 = content?.get(2)
        val title3 = info3?.title
        val author3 = info3?.author

        helper.setText(R.id.tv_rank_first, "1.$title1-$author1")
                .setText(R.id.tv_rank_name, item.name)
                .setText(R.id.tv_rank_second, "2.$title2-$author2")
                .setText(R.id.tv_rank_third, "3.$title3-$author3")

        helper.itemView.setOnClickListener{
//            val position = helper.getAdapterPosition()
//            MusicRankingListDetailActivity.lunch(mContext, position, item.getName())
        }
    }
}