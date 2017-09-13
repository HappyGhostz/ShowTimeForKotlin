package com.example.happyghost.showtimeforkotlin.adapter

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import com.andexert.library.RippleView
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.SpecialItem
import com.example.happyghost.showtimeforkotlin.ui.news.normal.NewsNormalActivity
import com.example.happyghost.showtimeforkotlin.ui.news.photonews.PhotoSetNewsActivity
import com.example.happyghost.showtimeforkotlin.ui.news.special.NewsSpecialActivity
import com.example.happyghost.showtimeforkotlin.utils.DefIconFactory
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader
import com.example.happyghost.showtimeforkotlin.utils.NewsUtils
import com.example.happyghost.showtimeforkotlin.utils.StringUtils
import com.flyco.labelview.LabelView

/**
 * @author Zhao Chenping
 * @creat 2017/9/11.
 * @description
 */
class NewsSpecialAdapter: BaseSectionQuickAdapter<SpecialItem, BaseViewHolder> {


    constructor(layoutResId:Int, sectionHeadResId:Int, data: List<SpecialItem>?) :super(layoutResId,
            sectionHeadResId,data)
    constructor() : this(R.layout.adapter_news_special,R.layout.adapter_news_special_head,null) {

    }

    override fun convert(helper: BaseViewHolder?, item: SpecialItem?) {
        val imageView = helper!!.getView<ImageView>(R.id.iv_icon)
        ImageLoader.loadCenterCrop(mContext, item!!.t.getImgsrc()!!,imageView,DefIconFactory.provideIcon())
        helper.setText(R.id.tv_title,item.t.getTitle())
                .setText(R.id.tv_source,StringUtils.clipNewsSource(item.t.getSource()!!))
                .setText(R.id.tv_time,item.t.getPtime())
        val labelView = helper.getView<LabelView>(R.id.label_view)
        if(NewsUtils.isNewsSpecial(item.t.getSkipType())){
            labelView.visibility = View.VISIBLE
            labelView.bgColor = ContextCompat.getColor(mContext,R.color.colorAccent)
            labelView.text = "专题"
        }else if(NewsUtils.isNewsPhotoSet(item.t.getSkipType())){
            labelView.visibility = View.VISIBLE
            labelView.bgColor = ContextCompat.getColor(mContext,R.color.item_photo_set_bg)
            labelView.text= "图集"
        }else{
            labelView.visibility = View.GONE
        }
        val rippleView = helper.getView<RippleView>(R.id.item_ripple)
        rippleView.setOnRippleCompleteListener {
            if(NewsUtils.isNewsSpecial(item.t.getSkipType())){
                NewsSpecialActivity.lunch(mContext, item.t.getSpecialID()!!)
            }else if(NewsUtils.isNewsPhotoSet(item.t.getSkipType())){
                PhotoSetNewsActivity.launch(mContext, item.t.getPhotosetID()!!)
            }else{
                NewsNormalActivity.lunch(mContext, item.t.getPostid()!!)
            }
        }

    }

    override fun convertHead(helper: BaseViewHolder?, item: SpecialItem?) {
         helper?.setText(R.id.tv_head,item?.header)
    }

}