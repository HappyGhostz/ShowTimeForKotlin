package com.example.happyghost.showtimeforkotlin.ui.book.rank.iteminfo

import com.example.happyghost.showtimeforkotlin.bean.bookdata.BooksByCats
import com.example.happyghost.showtimeforkotlin.bean.bookdata.Rankings
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/11/20.
 * @description
 */
interface ISubRankBaseView:IBaseView {
    fun loadRankList(data: Rankings.RankingBean)
}