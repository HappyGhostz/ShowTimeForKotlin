package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdate.BookMark
import java.util.ArrayList

/**
 * @author Zhao Chenping
 * @creat 2017/11/6.
 * @description
 */
class BookMarkAdapter(arrayList: ArrayList<BookMark>) :BaseQuickAdapter<BookMark,BaseViewHolder>(R.layout.item_book_mark,arrayList){
    override fun convert(helper: BaseViewHolder?, item: BookMark?) {
        val textView = helper?.getView<TextView>(R.id.tvMarkItem)
        val position = helper?.adapterPosition
        val spanText = SpannableString(position.toString() + 1.toString() + ". " + item?.title + ": ")
        spanText.setSpan(ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.light_coffee)),
                0, spanText.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        helper?.setText(R.id.tvMarkItem,spanText)
        if (item?.desc != null) {
            textView?.append(item.desc
                    .replace("　", "")
                    .replace(" ", "")
                    .replace("\n", ""))
        }
    }
}