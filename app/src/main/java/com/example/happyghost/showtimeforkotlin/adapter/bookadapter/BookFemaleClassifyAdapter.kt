package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdata.CategoryList

/**
 * @author Zhao Chenping
 * @creat 2017/9/19.
 * @description
 */
class BookFemaleClassifyAdapter : BaseQuickAdapter<CategoryList.MaleBean, BaseViewHolder>(R.layout.adapter_book_classify_item) {
    override fun convert(helper: BaseViewHolder?, item: CategoryList.MaleBean?) {
        helper?.setText(R.id.tvName, item?.name)
                ?.setText(R.id.tvBookCount, String.format(mContext.getString(R.string
                        .category_book_count), item?.bookCount))
    }
}