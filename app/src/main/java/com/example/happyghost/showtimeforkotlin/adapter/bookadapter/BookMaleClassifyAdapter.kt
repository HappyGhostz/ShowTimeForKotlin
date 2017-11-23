package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdate.CategoryList
import com.example.happyghost.showtimeforkotlin.ui.book.classify.classifydetail.ClassifyDetailActivity

/**
 * @author Zhao Chenping
 * @creat 2017/9/19.
 * @description
 */
class BookMaleClassifyAdapter:BaseQuickAdapter<CategoryList.MaleBean,BaseViewHolder>(R.layout.adapter_book_classify_item) {
    override fun convert(helper: BaseViewHolder?, item: CategoryList.MaleBean?) {
        helper?.setText(R.id.tvName, item?.name)
                ?.setText(R.id.tvBookCount, String.format(mContext.getString(R.string
                        .category_book_count), item?.bookCount))
        helper?.itemView?.setOnClickListener {
            ClassifyDetailActivity.start(mContext!!,item?.name!!,"male")
        }
    }
}