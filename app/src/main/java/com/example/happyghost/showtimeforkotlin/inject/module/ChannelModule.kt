package com.example.happyghost.showtimeforkotlin.inject.module

import com.example.happyghost.showtimeforkotlin.RxBus.RxBus
import com.example.happyghost.showtimeforkotlin.adapter.ChannelAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.loacaldao.DaoSession
import com.example.happyghost.showtimeforkotlin.news.channel.ChannelActivity
import com.example.happyghost.showtimeforkotlin.news.channel.ChannelPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/9/4.
 * @description
 */
@Module
class ChannelModule(view: ChannelActivity) {
    var mView : ChannelActivity = view
    @Provides
    @PerActivity
    fun providesChannelPresent(daoSession: DaoSession,  rxBus: RxBus):ChannelPresenter{
        return ChannelPresenter(mView,daoSession.newsTypeInfoDao,rxBus)
    }
//    @Provides
//    @PerActivity
//    fun providesChannelAdapter(): ChannelAdapter {
//        return ChannelAdapter()
//    }
}