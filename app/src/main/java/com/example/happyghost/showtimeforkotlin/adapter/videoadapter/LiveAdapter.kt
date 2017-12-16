package com.example.happyghost.showtimeforkotlin.adapter.videoadapter

import com.andexert.library.RippleView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.videodata.LiveListBean
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView

/**
 * @author Zhao Chenping
 * @creat 2017/12/16.
 * @description
 */
class LiveAdapter:BaseQuickAdapter<LiveListBean.ResultBean,BaseViewHolder>(R.layout.adapter_live_layout) {
    override fun convert(helper: BaseViewHolder?, item: LiveListBean.ResultBean?) {
        helper!!.setText(R.id.tv_roomname, item?.live_title)//房间名称
                .setText(R.id.tv_nickname, item?.live_nickname)//主播昵称
                .setText(R.id.tv_online, item?.live_online.toString())//在线人数
        val roomSrc = helper.getView<SimpleDraweeView>(R.id.iv_roomsrc)
        val acatarSrc = helper.getView<SimpleDraweeView>(R.id.iv_avatar)
//        ImageLoader.loadCenterCrop(mContext, item?.live_img!! , roomSrc, DefIconFactory.provideIcon())
        var  controller = Fresco.newDraweeControllerBuilder()
                .setUri(item?.live_img)
                .setAutoPlayAnimations(true)
                .setTapToRetryEnabled(true)
                .build()
        roomSrc.controller = controller

        acatarSrc.setImageURI(item?.live_userimg)
//        ImageLoader.loadCenterCrop(mContext, (item as LiveListItemBean).getLive_userimg(), acatarSrc, DefIconFactory.provideIcon())
        val rippleView = helper.getView<RippleView>(R.id.item_ripple)
        rippleView?.setOnRippleCompleteListener({
            rippleView: RippleView? ->
        })
    }
}