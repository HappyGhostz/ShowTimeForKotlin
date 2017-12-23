package com.example.happyghost.showtimeforkotlin.adapter.videoadapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.videodata.LiveListBean
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView

/**
 * @author Zhao Chenping
 * @creat 2017/12/22.
 * @description
 */
class LiveDouyuAdapter:BaseQuickAdapter<LiveListBean.DataBean,BaseViewHolder>(R.layout.adapter_live_layout) {
    override fun convert(helper: BaseViewHolder?, item: LiveListBean.DataBean?) {
        helper!!.setText(R.id.tv_roomname, item?.room_name)//房间名称
                .setText(R.id.tv_nickname, item?.nickname)//主播昵称
                .setText(R.id.tv_online, item?.online.toString())//在线人数
        val roomSrc = helper.getView<SimpleDraweeView>(R.id.iv_roomsrc)
        val acatarSrc = helper.getView<SimpleDraweeView>(R.id.iv_avatar)
        var  controller = Fresco.newDraweeControllerBuilder()
                .setUri(item?.room_src)
                .setAutoPlayAnimations(true)
                .setTapToRetryEnabled(true)
                .build()
        roomSrc.controller = controller

        acatarSrc.setImageURI(item?.avatar_small)
    }
}