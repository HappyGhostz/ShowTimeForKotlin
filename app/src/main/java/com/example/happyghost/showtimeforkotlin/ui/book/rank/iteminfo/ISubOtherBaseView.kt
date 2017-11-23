package com.example.happyghost.showtimeforkotlin.ui.book.rank.iteminfo

import com.example.happyghost.showtimeforkotlin.bean.bookdate.Rankings
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/11/20.
 * @description
 */
interface ISubOtherBaseView:IBaseView {
    fun loadRankList(data: Rankings.RankingBean)
}