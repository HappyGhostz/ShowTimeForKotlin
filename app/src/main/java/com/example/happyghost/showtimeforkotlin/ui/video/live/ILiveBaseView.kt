package com.example.happyghost.showtimeforkotlin.ui.video.live

import com.example.happyghost.showtimeforkotlin.bean.videodata.LiveListBean
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/12/16.
 * @description
 */
interface ILiveBaseView:IBaseView {
    fun loadLiveDate(live:LiveListBean)
    fun loadMoreLiveDate(live: LiveListBean)
}