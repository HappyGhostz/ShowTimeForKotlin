package com.example.happyghost.showtimeforkotlin.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.NewsMultiItem

/**
 * @author Zhao Chenping
 * @creat 2017/9/5.
 * @description
 */
class NewsListAdapter:BaseQuickAdapter<NewsMultiItem,BaseViewHolder>(R.layout.adaptr_news_list) {
    override fun convert(helper: BaseViewHolder?, item: NewsMultiItem?) {

    }
}