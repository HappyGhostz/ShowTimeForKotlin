package com.example.happyghost.showtimeforkotlin.inject.module

import com.example.happyghost.showtimeforkotlin.RxBus.RxBus
import com.example.happyghost.showtimeforkotlin.adapter.ViewPagerAdapter
import com.example.happyghost.showtimeforkotlin.base.IRxBusPresenter
import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.loacaldao.DaoSession
import com.example.happyghost.showtimeforkotlin.news.NewsMainFragment
import com.example.happyghost.showtimeforkotlin.news.main.NewsMainPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/9/2.
 * @description
 */
@Module
class NewsMainModule {
    private lateinit var mView: NewsMainFragment

    constructor(view:NewsMainFragment)  {
        mView = view
    }
//    //出现kapt
    @Provides
    @PerFragment
    fun proidesViewPagerAdapter():ViewPagerAdapter{
        return ViewPagerAdapter(mView.childFragmentManager)
    }

    //这里返回的只能是NewsMainPresenter对象不能是它实现的接口
    //但是在java语言里可以
    @Provides
    @PerFragment
    fun providesNewsMainPresenter(daoSession: DaoSession,rxBus: RxBus): NewsMainPresenter {
        return NewsMainPresenter(mView, daoSession.getNewsTypeInfoDao(), rxBus)
    }
}