package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R

/**
 * @author Zhao Chenping
 * @creat 2017/11/22.
 * @description
 */
class SearchHistoryAdapter:BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_book_search_history) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.tvTitle, item)
    }
}