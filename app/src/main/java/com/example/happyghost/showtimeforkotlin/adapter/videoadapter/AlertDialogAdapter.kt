package com.example.happyghost.showtimeforkotlin.adapter.videoadapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R

/**
 * @author Zhao Chenping
 * @creat 2017/12/21.
 * @description
 */
class AlertDialogAdapter:BaseQuickAdapter<String,BaseViewHolder>(R.layout.adapter_dialog_item) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.tv_platform, item)//昵称
        helper?.setTextColor(R.id.tv_platform, ContextCompat.getColor(mContext, R.color.common_h2))
    }
}