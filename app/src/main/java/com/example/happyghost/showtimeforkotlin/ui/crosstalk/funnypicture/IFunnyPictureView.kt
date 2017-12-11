package com.example.happyghost.showtimeforkotlin.ui.crosstalk.funnypicture

import com.example.happyghost.showtimeforkotlin.bean.crossdate.FunnyPictureDate
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/12/9.
 * @description
 */
interface IFunnyPictureView:IBaseView {
    fun loadFunnyPictureDate(date: FunnyPictureDate)
}