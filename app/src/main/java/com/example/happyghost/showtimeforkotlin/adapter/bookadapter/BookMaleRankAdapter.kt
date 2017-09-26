package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdata.RankingListBean
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader

/**
 * @author Zhao Chenping
 * @creat 2017/9/25.
 * @description
 */
class BookMaleRankAdapter(groups: ArrayList<RankingListBean.MaleBean>, children: ArrayList<RankingListBean.MaleBean>) : BaseQuickAdapter<RankingListBean.MaleBean,BaseViewHolder>(R.layout.adapter_book_rank_item) {
    var isExpend:Boolean = false
    var group =groups
    var child = children
    override fun convert(helper: BaseViewHolder?, item: RankingListBean.MaleBean?) {
        if(helper?.position!=5){
            val imageView = helper?.getView<ImageView>(R.id.ivRankCover)
            ImageLoader.loadCenterCrop(mContext,ConsTantUtils.IMG_BASE_URL+item?.cover,imageView!!,R.mipmap.defaut_preview)
            val view = helper?.getView<ImageView>(R.id.ivRankArrow)
            view.visibility = View.GONE
            helper.setText(R.id.tvRankGroupName,item?.title)
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
//                    ImageLoader.loadImageFromRes(mContext,R.mipmap.rank_arrow_up,ima)
                    addData(child)
                    notifyDataSetChanged()
                }else{
//                    ImageLoader.loadImageFromRes(mContext,R.mipmap.rank_arrow_down,ima)
                    replaceData(group)
                    addData(RankingListBean.MaleBean("其他火热排行榜"))
                    notifyDataSetChanged()
                }
                isExpend=!isExpend

            }
        }
    }


}