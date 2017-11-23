package com.example.happyghost.showtimeforkotlin.ui.news.photonews

import com.example.happyghost.showtimeforkotlin.bean.newsdate.PhotoSetInfo
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/9/13.
 * @description
 */
interface IPhotoSetView:IBaseView {
    /**
     * 显示数据
     * @param photoSetBean 图集
     */
     fun loadData(photoSetBean: PhotoSetInfo)
}