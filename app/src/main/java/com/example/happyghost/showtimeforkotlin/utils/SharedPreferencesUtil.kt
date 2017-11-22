package com.example.happyghost.showtimeforkotlin.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import java.io.*

/**
 * @author Zhao Chenping
 * @creat 2017/9/7.
 * @description
 */
class SharedPreferencesUtil {
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

        fun getInt(key:String,defValue:Int) = sharedPreferences.getInt(key,defValue)

        fun putInt(key:String,value:Int)= edit.putInt(key,value).commit()
        fun putString(key: String,value:String) = edit.putString(key,value).commit()
        fun getString(key: String,defValue: String) = sharedPreferences.getString(key,defValue)
        fun remove(key: String){
            edit.remove(key)
            edit.commit()
        }

        fun exists(key: String): Boolean {
            return sharedPreferences.contains(key)
        }
        fun <T> getObject(key: String, clazz: Class<T>): T? {
            if (sharedPreferences.contains(key)) {
                val objectVal = sharedPreferences.getString(key, null)
                val buffer = Base64.decode(objectVal, Base64.DEFAULT)
                val bais = ByteArrayInputStream(buffer)
                var ois: ObjectInputStream? = null
                try {
                    ois = ObjectInputStream(bais)
                    return ois.readObject() as T
                } catch (e: StreamCorruptedException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                } finally {
                    try {
                        bais?.close()
                        if (ois != null) {
                            ois.close()
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
            return null
        }

        fun putObject(key: String, `object`: Any?) {
            val baos = ByteArrayOutputStream()
            var out: ObjectOutputStream? = null
            try {
                out = ObjectOutputStream(baos)
                out.writeObject(`object`)
                val objectVal = String(Base64.encode(baos.toByteArray(), Base64.DEFAULT))
                edit.putString(key, objectVal)
                edit.commit()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    baos?.close()
                    if (out != null) {
                        out.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }
}