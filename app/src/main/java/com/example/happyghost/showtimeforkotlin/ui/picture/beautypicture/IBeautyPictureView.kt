package com.example.happyghost.showtimeforkotlin.ui.picture.beautypicture

import com.example.happyghost.showtimeforkotlin.bean.picturedate.BeautyPicture
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/12/14.
 * @description
 */
interface IBeautyPictureView:IBaseView {
    fun loadBeautyData(beauty: BeautyPicture)
    fun loadMoreBeautyData(beauty: BeautyPicture)
}