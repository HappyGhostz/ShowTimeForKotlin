package com.example.happyghost.showtimeforkotlin.inject.module.crossmodule

import com.example.happyghost.showtimeforkotlin.adapter.crossadapter.CrossTalkAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.ui.crosstalk.crossfragment.CrossTalkFragment
import com.example.happyghost.showtimeforkotlin.ui.crosstalk.crossfragment.CrossTalkPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/12/8.
 * @description
 */
@Module
class CrossTalkModule(crossTalkFragment: CrossTalkFragment) {
    var view = crossTalkFragment
    @PerFragment
    @Provides
    fun providesPresenter(): CrossTalkPresenter =CrossTalkPresenter(view)
    @PerFragment
    @Provides
    fun providesAdapter(): CrossTalkAdapter = CrossTalkAdapter()
}