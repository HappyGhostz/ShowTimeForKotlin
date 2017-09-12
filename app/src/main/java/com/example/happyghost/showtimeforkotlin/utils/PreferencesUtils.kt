package com.example.happyghost.showtimeforkotlin.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * @author Zhao Chenping
 * @creat 2017/9/7.
 * @description
 */
class PreferencesUtils {
    companion object {

        lateinit var sharedPreferences:SharedPreferences
        lateinit var edit:SharedPreferences.Editor
        fun init(context: Context,name:String,mode:Int){
             sharedPreferences = context.getSharedPreferences(name, mode)
             edit = sharedPreferences.edit()
        }
        fun isShowImageAlways():Boolean{
            return getBoolean(ConsTantUtils.showImageKey, false)
        }


        fun putIsShowImageAlways(key:String,vaule:Boolean){
            putBoolean(key, vaule)
        }

       fun putBoolean(key: String, vaule: Boolean) {
            edit.putBoolean(key, vaule)
            edit.commit()
        }

        fun getBoolean(key:String,value:Boolean) = sharedPreferences.getBoolean(key,value)
    }
}