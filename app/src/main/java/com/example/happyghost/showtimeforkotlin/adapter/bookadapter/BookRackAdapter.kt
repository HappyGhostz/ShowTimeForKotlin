package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.RxBus.event.LocalBookEvent
import com.example.happyghost.showtimeforkotlin.bean.bookdate.Recommend
import com.example.happyghost.showtimeforkotlin.loacaldao.LocalBookInfoDao
import com.example.happyghost.showtimeforkotlin.utils.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.io.File
import java.text.NumberFormat

/**
 * @author Zhao Chenping
 * @creat 2017/9/18.
 * @description
 */
class BookRackAdapter(): BaseQuickAdapter<Recommend.RecommendBooks, BaseViewHolder>(R.layout.adapter_book_rack_item) {
    var mIsLocal = false
    constructor(isLocal:Boolean):this(){
        this.mIsLocal = isLocal
    }
    override fun convert(helper: BaseViewHolder?, item: Recommend.RecommendBooks?) {
        var lastUpdata = ""
        if(!TextUtils.isEmpty(StringUtils.getDescriptionTimeFromDateString(item?.updated))){
            lastUpdata = StringUtils.getDescriptionTimeFromDateString(item?.updated)+":"
        }
        helper?.setText(R.id.tvRecommendTitle,item?.title)
                ?.setText(R.id.tvLatelyUpdate,lastUpdata)
                ?.setText(R.id.tvRecommendShort,item?.lastChapter)
                ?.setVisible(R.id.ivTopLabel, item?.isTop!!)
                ?.setVisible(R.id.ckBoxSelect,item.showCheckBox)
                ?.setVisible(R.id.ivUnReadDot, StringUtils.formatZhuiShuDateString(item.updated)?.compareTo(item.recentReadingTime)!! >0)
        val imageView = helper?.getView<ImageView>(R.id.ivRecommendCover)
        if(NetUtil.isWifiConnected(mContext)|| SharedPreferencesUtil.isShowImageAlways()){
            ImageLoader.loadCenterCrop(mContext,ConsTantUtils.IMG_BASE_URL+item?.cover, imageView!!,R.mipmap.cover_default)
        }else{
            helper?.setImageResource(R.id.ivRecommendCover,R.mipmap.cover_default)
        }
        if (item?.path != null && item.path.endsWith(ConsTantUtils.SUFFIX_PDF)) run {
            helper?.setImageResource(R.id.ivRecommendCover, R.mipmap.ic_shelf_pdf)
        } else if (item?.path != null && item.path.endsWith(ConsTantUtils.SUFFIX_EPUB)) run {
            helper?.setImageResource(R.id.ivRecommendCover, R.mipmap.ic_shelf_epub)
        } else if (item?.path != null && item.path.endsWith(ConsTantUtils.SUFFIX_CHM)) run {
            helper?.setImageResource(R.id.ivRecommendCover, R.mipmap.ic_shelf_chm) }
        else if (item!!.isFromSD) {
            helper?.setImageResource(R.id.ivRecommendCover, R.mipmap.ic_shelf_txt)
            val fileLen = FileUtils.getChapterFile(item._id!!, 1).length()
            if (fileLen > 10) {
                val progress = SettingManager.getInstance()!!.getReadProgress(item._id!!)[2] .toDouble() / fileLen
                val fmt = NumberFormat.getPercentInstance()
                fmt.maximumFractionDigits = 2
                helper?.setText(R.id.tvRecommendShort, "当前阅读进度：" + fmt.format(progress))
            }
        }
        if (mIsLocal) {
            helper?.itemView?.setOnClickListener{
                if (item.path.endsWith(ConsTantUtils.SUFFIX_TXT)) {
                    // TXT
                    AlertDialog.Builder(mContext)
                            .setTitle("提示")
                            .setMessage(String.format(mContext.getString(
                                    R.string.book_detail_is_joined_the_book_shelf), item.title))
                            .setPositiveButton("确定") { dialog, which ->
                                // 拷贝到缓存目录
                                FileUtils.fileChannelCopy(File(item.path),
                                        File(FileUtils.getChapterPath(item._id!!, 1)))
                                doAsync {
                                    var local=false
                                    val build = AppApplication.instance.daoSession.localBookInfoDao.queryBuilder()
                                            .where(LocalBookInfoDao.Properties.Url.eq(item._id)).build()
                                    val list = build.list()
                                    if(list.isEmpty()){
                                        insertBook(item)
                                    }else{
                                        local=true
                                    }
                                    uiThread {
                                        if(local){
                                            mContext.toast("书籍已存在")
                                        }else{
                                            AppApplication.instance.mRxBus?.post(LocalBookEvent(true))
                                            mContext.toast("书籍已加入")
                                        }
                                    }
                                }
                                dialog.dismiss()
                            }.setNegativeButton("取消") { dialog, which -> dialog.dismiss() }.show()
                }
            }
        }
    }

    private fun insertBook(item: Recommend.RecommendBooks) {
        val allBooks = AppApplication.instance.daoSession.localBookInfoDao.queryBuilder().list()
        val size = allBooks.size
        var id:Long=1
        val localBookInfo = BookTransformer.RecommendBooksConvertlocalBook(item)
        localBookInfo.id = size+id
        localBookInfo.isFromSD=true
        AppApplication.instance.daoSession.localBookInfoDao.insert(localBookInfo)
    }
}