package com.example.happyghost.showtimeforkotlin.inject.component

import android.content.Context
import com.example.happyghost.showtimeforkotlin.RxBus.RxBus
import com.example.happyghost.showtimeforkotlin.inject.module.ApplicationModule
import com.example.happyghost.showtimeforkotlin.loacaldao.DaoSession
import dagger.Component
import javax.inject.Singleton

/**
 * @author Zhao Chenping
 * @creat 2017/9/2.
 * @description
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun getContext(): Context
    fun getRxBus(): RxBus
    fun getDaoSession(): DaoSession
}