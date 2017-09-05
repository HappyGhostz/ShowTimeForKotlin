package com.example.happyghost.showtimeforkotlin.adapter

import com.chad.library.adapter.base.BaseItemDraggableAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.loacaldao.NewsTypeInfo

/**
 * @author Zhao Chenping
 * @creat 2017/9/4.
 * @description
 */
class ChannelAdapter(dataList: List<NewsTypeInfo>) : BaseItemDraggableAdapter<NewsTypeInfo, BaseViewHolder>(R.layout.adapter_channel_layout,dataList) {
    override fun convert(helper: BaseViewHolder?, item: NewsTypeInfo?) {
         helper?.setText(R.id.tv_channel_name,item?.name)
    }
}