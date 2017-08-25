package com.example.happyghost.showtimeforkotlin

import android.app.Application
import android.content.Context
import com.example.happyghost.showtimeforkotlin.delegation.AppDelegation
import kotlin.properties.ReadWriteProperty

/**
 * @author Zhao Chenping
 * @creat 2017/8/24.
 * @description
 */
class AppApplication : Application() {
    object DelegatesExt{
        fun <T> notNullSingleValue():
                ReadWriteProperty<Any?,T> = AppDelegation()
    }
    companion object {
        //单列委托
        var instance : Context by DelegatesExt.notNullSingleValue()
    }
    override fun onCreate() {
        super.onCreate()
        instance = applicationContext
    }

}