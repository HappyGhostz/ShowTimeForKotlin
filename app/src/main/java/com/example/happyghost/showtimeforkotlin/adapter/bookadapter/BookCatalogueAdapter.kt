package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookMixATocBean
import com.example.happyghost.showtimeforkotlin.bean.bookdata.RankingListBean
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.FileUtils
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader
import java.util.ArrayList

/**
 * @author Zhao Chenping
 * @creat 2017/9/25.
 * @description
 */
class BookCatalogueAdapter(catalogueList: ArrayList<BookMixATocBean.MixTocBean.ChaptersBean>, bookId:String, currentChapter:Int ) :
        BaseQuickAdapter<BookMixATocBean.MixTocBean.ChaptersBean,BaseViewHolder>(R.layout.adapter_book_catalogue_item,catalogueList) {
    var mCatalogues = catalogueList
    var mBookId = bookId
    var mCurrentChapter = currentChapter
    private var isEpub = false
    override fun convert(helper: BaseViewHolder?, item: BookMixATocBean.MixTocBean.ChaptersBean?) {
        val position = helper?.adapterPosition
        val tvTocItem = helper?.getView<TextView>(R.id.tvTocItem)
        tvTocItem?.text = item?.title
        val drawable: Drawable
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
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        tvTocItem?.setCompoundDrawables(drawable, null, null, null)
    }
}