package com.example.happyghost.showtimeforkotlin.ui.crosstalk.crossfragment

import com.example.happyghost.showtimeforkotlin.bean.crossdate.CrossTalkDate
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/12/8.
 * @description
 */
interface ICrossTalkView:IBaseView {
    fun showCrossTalk(data: CrossTalkDate)
}