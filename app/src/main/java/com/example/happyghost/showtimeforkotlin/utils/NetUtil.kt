package com.example.happyghost.showtimeforkotlin.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager
import android.text.TextUtils

/**
 * @author Zhao Chenping
 * @creat 2017/9/5.
 * @description
 */
class NetUtil {
    companion object {
        val NETWORK_TYPE_WIFI = "wifi"
        val NETWORK_TYPE_3G = "eg"
        val NETWORK_TYPE_2G = "2g"
        val NETWORN_TYPE_4G = "4g"
        val NETWORK_TYPE_WAP = "wap"
        val NETWORK_TYPE_UNKNOWN = "unknown"
        val NETWORK_TYPE_DISCONNECT = "disconnect"
        /**
         * 网络类型
         */
        fun getNetWorkType(context: Context): Int{
            val service = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if(service!=null){
                val networkInfo = service.activeNetworkInfo
                val type = networkInfo.type
                return type
            }
            return -1
        }

        /**
         * 网络类型名
         */
        fun getNetWorkTypeName(context: Context): String? {
            val service = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = service.activeNetworkInfo
            var type = NETWORK_TYPE_DISCONNECT
            if(service==null&&networkInfo == null){
                return null
            }
            if(networkInfo.isConnected){
                val typeName = networkInfo.typeName
                if("WIFI".equals(typeName,false)){
                    type = NETWORK_TYPE_WIFI
                }else if("MOBILE".equals(typeName,false)){
                    val proxyHost = android.net.Proxy.getDefaultHost()
                    type = if (TextUtils.isEmpty(proxyHost))
                        if (isFastMobileNetwork(context)) NETWORN_TYPE_4G else NETWORK_TYPE_2G
                    else
                        NETWORK_TYPE_WAP
                } else run { type = NETWORK_TYPE_UNKNOWN }
            }
            return type
        }

        /**
         * Whether is fast mobile network
         *
         * @param context
         * @return
         */
        private fun isFastMobileNetwork(context: Context): Boolean {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager ?: return false

            when (telephonyManager.networkType) {
                TelephonyManager.NETWORK_TYPE_1xRTT -> return false
                TelephonyManager.NETWORK_TYPE_CDMA -> return false
                TelephonyManager.NETWORK_TYPE_EDGE -> return false
                TelephonyManager.NETWORK_TYPE_EVDO_0 -> return true
                TelephonyManager.NETWORK_TYPE_EVDO_A -> return true
                TelephonyManager.NETWORK_TYPE_GPRS -> return false
                TelephonyManager.NETWORK_TYPE_HSDPA -> return true
                TelephonyManager.NETWORK_TYPE_HSPA -> return true
                TelephonyManager.NETWORK_TYPE_HSUPA -> return true
                TelephonyManager.NETWORK_TYPE_UMTS -> return true
                TelephonyManager.NETWORK_TYPE_EHRPD -> return true
                TelephonyManager.NETWORK_TYPE_EVDO_B -> return true
                TelephonyManager.NETWORK_TYPE_HSPAP -> return true
                TelephonyManager.NETWORK_TYPE_IDEN -> return false
                TelephonyManager.NETWORK_TYPE_LTE -> return true
                TelephonyManager.NETWORK_TYPE_UNKNOWN -> return false
                else -> return false
            }
        }

        /**
         * 判断网络是否可用
         */
        fun isNetworkAvailable(context: Context):Boolean{
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = manager.activeNetworkInfo
            if(info!=null&&info.isConnected){
                return  true
            }
            return false
        }

        /**
         *mobile网络是否可用
         */
        fun isMobileConnected(context: Context):Boolean{
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            if(networkInfo!=null&&networkInfo.isAvailable){
                return  networkInfo.isConnected
            }
            return false
        }

        /**
         * wifi网络是否可用
         */
        fun isWifiConnected(context: Context):Boolean{
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            if(wifiInfo!=null&&wifiInfo.isAvailable){
                return wifiInfo.isConnected
            }
            return false
        }
    }
}