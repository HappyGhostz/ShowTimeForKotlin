package com.example.happyghost.showtimeforkotlin.inject.module.bookmodule

import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.SubRankListAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.ui.book.rank.iteminfo.SubOtherHomeRankActivity
import com.example.happyghost.showtimeforkotlin.ui.book.rank.iteminfo.SubOtherRankListPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/11/20.
 * @description
 */
@Module
class SubOtherHomeRankModule(subOtherHomeRankActivity: SubOtherHomeRankActivity, mRankOtherId: String) {
    var view = subOtherHomeRankActivity
    var otherid  =mRankOtherId
    @PerActivity
    @Provides
    fun providesPresenter():SubOtherRankListPresenter= SubOtherRankListPresenter(view,otherid)
    @PerActivity
    @Provides
    fun providesAdapter():SubRankListAdapter= SubRankListAdapter()
}