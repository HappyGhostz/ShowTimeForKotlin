package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import android.text.TextUtils
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdata.Recommend
import com.example.happyghost.showtimeforkotlin.utils.*

/**
 * @author Zhao Chenping
 * @creat 2017/9/18.
 * @description
 */
class BookRackAdapter: BaseQuickAdapter<Recommend.RecommendBooks, BaseViewHolder>(R.layout.adapter_book_rack_item) {
    override fun convert(helper: BaseViewHolder?, item: Recommend.RecommendBooks?) {
        var lastUpdata = ""
        if(!TextUtils.isEmpty(StringUtils.getDescriptionTimeFromDateString(item?.updated))){
            lastUpdata = StringUtils.getDescriptionTimeFromDateString(item?.updated)+":"
        }
        helper?.setText(R.id.tvRecommendTitle,item?.title)
                ?.setText(R.id.tvLatelyUpdate,lastUpdata)
                ?.setText(R.id.tvRecommendShort,item?.lastChapter)
                ?.setVisible(R.id.ivTopLabel, item?.isTop!!)
                ?.setVisible(R.id.ckBoxSelect,item.showCheckBox)
                ?.setVisible(R.id.ivUnReadDot, StringUtils.formatZhuiShuDateString(item.updated)?.compareTo(item.recentReadingTime)!! >0)
        val imageView = helper?.getView<ImageView>(R.id.ivRecommendCover)
        if(NetUtil.isWifiConnected(mContext)||PreferencesUtils.isShowImageAlways()){
            ImageLoader.loadCenterCrop(mContext,ConsTantUtils.IMG_BASE_URL+item?.cover, imageView!!,R.mipmap.cover_default)
        }else{
            helper?.setImageResource(R.id.ivRecommendCover,R.mipmap.cover_default)
        }
    }
}