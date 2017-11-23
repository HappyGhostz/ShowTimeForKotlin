package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdate.BookMixATocBean
import com.example.happyghost.showtimeforkotlin.utils.FileUtils
import kotlin.collections.ArrayList

/**
 * @author Zhao Chenping
 * @creat 2017/9/25.
 * @description
 */
class BookCatalogueAdapter(catalogueList: ArrayList<BookMixATocBean.MixTocBean.ChaptersBean>, bookId: String,
                           currentChapter: Int, reversal: Boolean, equals: Boolean) :
        BaseQuickAdapter<BookMixATocBean.MixTocBean.ChaptersBean,BaseViewHolder>(R.layout.adapter_book_catalogue_item,catalogueList) {
    var mCatalogues = catalogueList
    var mBookId = bookId
    var mCurrentChapter = currentChapter
    var mIsReversal  =reversal
    var mIsSameBook = equals
    private var isEpub = false
    override fun convert(helper: BaseViewHolder?, item: BookMixATocBean.MixTocBean.ChaptersBean?) {
        val position = helper?.adapterPosition
        val tvTocItem = helper?.getView<TextView>(R.id.tvTocItem)
        tvTocItem?.text = item?.title
        val drawable: Drawable
        if(mIsReversal&&mIsSameBook){
            var mReversalChapter = mCatalogues.size-mCurrentChapter
            var mReversalPosition = mCatalogues.size-position!!
            if (mReversalChapter == position!!) {
                tvTocItem?.setTextColor(ContextCompat.getColor(mContext, R.color.blackColor))
                val textPaint = tvTocItem?.paint
                textPaint?.isFakeBoldText=true
                drawable = ContextCompat.getDrawable(mContext, R.mipmap.ic_toc_item_activated)
            } else if (isEpub || FileUtils.getChapterFile(mBookId, mReversalPosition - 1).length() > 10) {
                tvTocItem?.setTextColor(ContextCompat.getColor(mContext, R.color.blackColor))
                drawable = ContextCompat.getDrawable(mContext, R.mipmap.ic_toc_item_download)
            } else {
                tvTocItem?.setTextColor(ContextCompat.getColor(mContext, R.color.grayColor))
                drawable = ContextCompat.getDrawable(mContext, R.mipmap.ic_toc_item_normal)
            }
        }else{
            if (mCurrentChapter == position!! + 1) {
                tvTocItem?.setTextColor(ContextCompat.getColor(mContext, R.color.blackColor))
                val textPaint = tvTocItem?.paint
                textPaint?.isFakeBoldText=true
                drawable = ContextCompat.getDrawable(mContext, R.mipmap.ic_toc_item_activated)
            } else if (isEpub || FileUtils.getChapterFile(mBookId, position + 1).length() > 10) {
                tvTocItem?.setTextColor(ContextCompat.getColor(mContext, R.color.blackColor))
                drawable = ContextCompat.getDrawable(mContext, R.mipmap.ic_toc_item_download)
            } else {
                tvTocItem?.setTextColor(ContextCompat.getColor(mContext, R.color.grayColor))
                drawable = ContextCompat.getDrawable(mContext, R.mipmap.ic_toc_item_normal)
            }
        }

        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        tvTocItem?.setCompoundDrawables(drawable, null, null, null)
    }
}