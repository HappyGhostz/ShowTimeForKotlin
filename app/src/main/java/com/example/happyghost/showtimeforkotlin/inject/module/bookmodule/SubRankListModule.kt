package com.example.happyghost.showtimeforkotlin.inject.module.bookmodule

import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.SubRankListAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.ui.book.rank.iteminfo.SubRankFragment
import com.example.happyghost.showtimeforkotlin.ui.book.rank.iteminfo.SubRankListPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/11/20.
 * @description
 */
@Module
class SubRankListModule(subRankFragment: SubRankFragment, mRankType: String) {
    var view= subRankFragment
    var type = mRankType
    @PerFragment
    @Provides
    fun providesPresenter(): SubRankListPresenter =SubRankListPresenter(view,type)
    @PerFragment
    @Provides
    fun providesSubRankListAdapter(): SubRankListAdapter = SubRankListAdapter()
}