package com.example.happyghost.showtimeforkotlin.ui.book.rank

import com.example.happyghost.showtimeforkotlin.bean.bookdate.RankingListBean
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/9/25.
 * @description
 */
interface IBookRankView :IBaseView{
    abstract fun LoadRankList(rankingList: RankingListBean)
}