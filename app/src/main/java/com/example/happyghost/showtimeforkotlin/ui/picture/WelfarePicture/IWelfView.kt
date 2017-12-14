package com.example.happyghost.showtimeforkotlin.ui.picture.WelfarePicture

import com.example.happyghost.showtimeforkotlin.bean.picturedate.WelfarePhotoList
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/12/13.
 * @description
 */
interface IWelfView:IBaseView {
    fun showWelfDate(welfDate: WelfarePhotoList)
    fun showMoreWelfDate(welfDate: WelfarePhotoList)
}