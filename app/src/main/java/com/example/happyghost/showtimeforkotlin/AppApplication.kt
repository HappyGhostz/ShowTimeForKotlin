package com.example.happyghost.showtimeforkotlin

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.util.Log
import com.example.happyghost.showtimeforkotlin.RxBus.RxBus
import com.example.happyghost.showtimeforkotlin.delegation.AppDelegation
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.component.DaggerApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.ApplicationModule
import com.example.happyghost.showtimeforkotlin.loacaldao.DaoMaster
import com.example.happyghost.showtimeforkotlin.loacaldao.DaoSession
import com.example.happyghost.showtimeforkotlin.loacaldao.MySQLiteOpenHelper
import com.example.happyghost.showtimeforkotlin.loacaldao.NewsTypeDao
import com.example.happyghost.showtimeforkotlin.utils.SharedPreferencesUtil
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import com.example.happyghost.showtimeforkotlin.utils.ThemeManager
import com.facebook.drawee.backends.pipeline.Fresco
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.TbsListener
import kotlin.properties.ReadWriteProperty

/**
 * @author Zhao Chenping
 * @creat 2017/8/24.
 * @description
 */
class AppApplication : Application() {
    lateinit var daoSession : DaoSession
    lateinit var sAppComponent: ApplicationComponent
    var mRxBus = RxBus.intanceBus
    object DelegatesExt{
        fun <T> notNullSingleValue():
                ReadWriteProperty<Any?,T> = AppDelegation()
    }
    lateinit var mContext : Context
    companion object {
//        //单列委托
//        var instance : AppApplication by DelegatesExt.notNullSingleValue()

//        @Volatile private var mRxBus: RxBus? = null
//        //单列模式
//        var instance: AppApplication? = null
//            get() {
//                if (instance == null) {
//                    synchronized(AppApplication::class.java) {
//                        if (instance == null) {
//                            instance = AppApplication()
//                        }
//                    }
//                }
//                return instance
//            }
           lateinit var instance: AppApplication
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        mContext = applicationContext
        init()
    }
    fun init(){
        //初始化本地数据库
        initDao()
        //初始化dagger
        initInject()
        //初始化SharedPreferences
        SharedPreferencesUtil.init(mContext,"setting", Context.MODE_PRIVATE)
        //网络初始化
        RetrofitService.init()
        //初始化lru缓存
        ThemeManager.setLruCache()
        Fresco.initialize(this)
        initTecentWebSdk()
    }

    private fun initTecentWebSdk() {
        val cb = object : QbSdk.PreInitCallback {

            override fun onViewInitFinished(arg0: Boolean) {
                Log.e("app", " onViewInitFinished is " + arg0)
            }

            override fun onCoreInitFinished() {

            }
        }
        QbSdk.setTbsListener(object : TbsListener {
            override fun onDownloadFinish(i: Int) {
                Log.d("app", "onDownloadFinish is " + i)
            }

            override fun onInstallFinish(i: Int) {
                Log.d("app", "onInstallFinish is " + i)
            }

            override fun onDownloadProgress(i: Int) {
                Log.d("app", "onDownloadProgress:" + i)
            }
        })
        QbSdk.initX5Environment(applicationContext, cb)
    }

    fun initDao(){
        val helper = MySQLiteOpenHelper(this, "showTime-db", null)
        val db = helper.writableDb
        daoSession = DaoMaster(db).newSession()
        NewsTypeDao.updateLocalData(mContext,daoSession)
    }
    fun initInject(){
        // 这里不做注入操作，只提供一些全局单例数据
        sAppComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this@AppApplication, daoSession, mRxBus))
                .build()

    }
    fun getApplication(): AppApplication? {
        return instance
    }
    fun getContext() :Context{
        return  mContext
    }
    fun getAppComponent() : ApplicationComponent{
        return sAppComponent
    }

    //http://www.cnblogs.com/dd-dd/p/5711816.html
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


}