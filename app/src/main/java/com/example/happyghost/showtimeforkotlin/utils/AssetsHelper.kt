package com.example.happyghost.showtimeforkotlin.utils

import android.content.Context
import java.io.InputStream

/**
 * @author Zhao Chenping
 * @creat 2017/8/30.
 * @description
 */
class AssetsHelper {
    companion object {
        @JvmStatic fun readData(context: Context, fileName:String):String{
            var inPutStream: InputStream?=null
            var data :String?=null
            try {
                inPutStream = context.assets.open(fileName)

                var byte : ByteArray = kotlin.ByteArray(inPutStream!!.available())
                inPutStream.read(byte)
                inPutStream.close()
                data = String(byte)
            }catch (e:Exception){
                e.printStackTrace()
            }
            return data.toString()
        }
    }

}