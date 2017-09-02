package com.example.happyghost.showtimeforkotlin.inject.module

import android.content.Context
import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.RxBus.RxBus
import com.example.happyghost.showtimeforkotlin.loacaldao.DaoSession
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Zhao Chenping
 * @creat 2017/9/2.
 * @description
 */
@Module
class ApplicationModule(appApplication: AppApplication, daoSession: DaoSession, rxBus: RxBus?) {
     var mApplication: AppApplication
     var mDaoSession: DaoSession
     var mRxBus: RxBus
    init {
        mApplication = appApplication
        mDaoSession = daoSession
        mRxBus = rxBus!!
    }



    @Provides
    @Singleton
    internal fun provideApplicationContext(): Context {
        return mApplication.getContext()
    }

    @Provides
    @Singleton
    internal fun provideRxBus(): RxBus {
        return mRxBus
    }

    @Provides
    @Singleton
    internal fun provideDaoSession(): DaoSession {
        return mDaoSession
    }
}