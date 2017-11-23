package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdate.RankingListBean
import com.example.happyghost.showtimeforkotlin.ui.book.rank.iteminfo.SubOtherHomeRankActivity
import com.example.happyghost.showtimeforkotlin.ui.book.rank.iteminfo.SubRankActivity
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader

/**
 * @author Zhao Chenping
 * @creat 2017/9/25.
 * @description
 */
class BookFemaleRankAdapter(femalegroups: ArrayList<RankingListBean.MaleBean>, femalechildren: ArrayList<RankingListBean.MaleBean>) : BaseQuickAdapter<RankingListBean.MaleBean, BaseViewHolder>(R.layout.adapter_book_rank_item) {
    var group = femalegroups
    var child = femalechildren
    var isExpend = false
    override fun convert(helper: BaseViewHolder?, item: RankingListBean.MaleBean?) {
        if(helper?.position!=5){
            val imageView = helper?.getView<ImageView>(R.id.ivRankCover)
            ImageLoader.loadCenterCrop(mContext, ConsTantUtils.IMG_BASE_URL+item?.cover,imageView!!,R.mipmap.defaut_preview)
            val view = helper?.getView<ImageView>(R.id.ivRankArrow)
            view.visibility = View.GONE
            helper.setText(R.id.tvRankGroupName,item?.title)
            helper.itemView.setOnClickListener {
                if(helper.position<5){
                    SubRankActivity.startActivity(mContext, item?._id!!, item.monthRank!!, item.totalRank!!, item.title!!)
                }else if(helper.position>5){
                    SubOtherHomeRankActivity.startActivity(mContext, item?._id!!, item.title!!)
                }
            }
        }else{
            val view = helper.getView<ImageView>(R.id.ivRankCover)
            val ima = helper.getView<ImageView>(R.id.ivRankArrow)

            ImageLoader.loadImageFromRes(mContext,R.mipmap.ic_rank_collapse,view)
            helper.setText(R.id.tvRankGroupName,item?.title)
            if(!isExpend){
                ImageLoader.loadImageFromRes(mContext,R.mipmap.rank_arrow_down,ima)
            }else{
                ImageLoader.loadImageFromRes(mContext,R.mipmap.rank_arrow_up,ima)
            }
            helper.itemView.setOnClickListener {
                if(!isExpend){
                    addData(child)
                    notifyDataSetChanged()
                }else{
                    replaceData(group)
                    addData(RankingListBean.MaleBean("其他火热排行榜"))
                    notifyDataSetChanged()
                }
                isExpend=!isExpend

            }
        }
    }


}