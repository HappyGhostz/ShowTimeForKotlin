package com.example.happyghost.showtimeforkotlin.ui.video.kankan

import com.example.happyghost.showtimeforkotlin.bean.videodata.VideoListDate
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/12/25.
 * @description
 */
interface IBaseViedoListView :IBaseView {
    fun loadVideoListDate(date: VideoListDate)
    fun loadVideoForCategoryidDate(date: VideoListDate)
    fun loadMoreVideoForCategoryidDate(date: VideoListDate)
}