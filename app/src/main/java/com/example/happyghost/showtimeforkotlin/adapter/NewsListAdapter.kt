package com.example.happyghost.showtimeforkotlin.adapter

import android.view.View
import android.widget.ImageView
import com.andexert.library.RippleView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.NewsMultiItem
import com.example.happyghost.showtimeforkotlin.ui.news.special.NewsSpecialActivity
import com.example.happyghost.showtimeforkotlin.utils.*
import com.flyco.labelview.LabelView

/**
 * @author Zhao Chenping
 * @creat 2017/9/5.
 * @description
 */
class NewsListAdapter(data: MutableList<NewsMultiItem>?) : BaseMultiItemQuickAdapter<NewsMultiItem, BaseViewHolder>(data) {
   init {
       addItemType(NewsMultiItem.NEWS_INFO_NORMAL, R.layout.adaptr_news_list)
       addItemType(NewsMultiItem.NEWS_INFO_PHOTO_SET, R.layout.adapter_news_list_photo_set)
   }
    override fun convert(helper: BaseViewHolder?, item: NewsMultiItem?) {
        when(item?.getNewsTye()){
            NewsMultiItem.NEWS_INFO_NORMAL->
                    handlerNormal(helper,item)
            NewsMultiItem.NEWS_INFO_PHOTO_SET->
                    handlerPhotoSet(helper,item)
        }
    }

    /**
     * 处理普通新闻
     */
    fun handlerNormal(volder:BaseViewHolder?,item: NewsMultiItem?){
        val newsInfo = item?.getNewsInfo()
        val icon = volder?.getView<ImageView>(R.id.iv_icon)
        if(newsInfo?.imgsrc!=null){
            ImageLoader.loadCenterCrop(mContext, newsInfo?.imgsrc!!, icon!!, DefIconFactory.provideIcon())
        } else if(icon!=null){
            ImageLoader.loadImageFromRes(mContext,DefIconFactory.provideIcon(),icon)
        }

        volder?.setText(R.id.tv_title,newsInfo?.title)
                ?.setText(R.id.tv_time,newsInfo?.ptime)
                ?.setText(R.id.tv_source,StringUtils.clipNewsSource(newsInfo?.source!!))
        if(NewsUtils.isNewsSpecial(newsInfo?.skipType)){
            val labelView = volder?.getView<LabelView>(R.id.label_view)
            labelView?.visibility = View.VISIBLE
            labelView?.text = "专题"
        }else{
            volder?.setVisible(R.id.label_view,false)
        }
        val rippleView = volder?.getView<RippleView>(R.id.item_ripple)
        rippleView?.setOnRippleCompleteListener({
            rippleView: RippleView? ->
            if(NewsUtils.isNewsSpecial(newsInfo?.skipType)){
                NewsSpecialActivity.lunch(mContext, newsInfo?.specialID!!)
            }
        })

    }

    /**
     * 处理图集新闻
     */
    fun handlerPhotoSet(volder: BaseViewHolder?,item: NewsMultiItem?){
        val mNewsInfo = item?.mNewsInfo
        var images = ArrayList<ImageView>()
        images.add(volder?.getView(R.id.iv_icon_1)!!)
        images.add(volder?.getView(R.id.iv_icon_2)!!)
        images.add(volder?.getView(R.id.iv_icon_3)!!)
        volder?.setVisible(R.id.iv_icon_2,false)
                ?.setVisible(R.id.iv_icon_3,false)
        ImageLoader.loadCenterCrop(mContext, mNewsInfo?.imgsrc!!,images.get(0),DefIconFactory.provideIcon())
        if(!ListUtils.isEmpty(mNewsInfo.imgextra)){
            for (i in 0..Math.min(2, mNewsInfo.imgextra!!.size) - 1) {
                images.get(i + 1).setVisibility(View.VISIBLE)
                ImageLoader.loadCenterCrop(mContext, mNewsInfo.imgextra!!.get(i).imgsrc!!,
                        images.get(i + 1), DefIconFactory.provideIcon())
            }
        }
        volder?.setText(R.id.tv_title,mNewsInfo.title)
                ?.setText(R.id.tv_source,StringUtils.clipNewsSource(mNewsInfo.source!!))
                ?.setText(R.id.tv_time,mNewsInfo.ptime)
    }
}