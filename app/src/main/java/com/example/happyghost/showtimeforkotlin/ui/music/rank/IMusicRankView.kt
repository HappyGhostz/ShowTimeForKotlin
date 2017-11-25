package com.example.happyghost.showtimeforkotlin.ui.music.rank

import com.example.happyghost.showtimeforkotlin.bean.musicdate.RankingListItem
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/11/25.
 * @description
 */
interface IMusicRankView:IBaseView {
    fun loadListMusic(details: List<RankingListItem.RangkingDetail>)
}