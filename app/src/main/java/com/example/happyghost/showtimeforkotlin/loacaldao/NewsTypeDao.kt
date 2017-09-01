package com.example.happyghost.showtimeforkotlin.loacaldao

import android.content.Context
import com.example.happyghost.showtimeforkotlin.utils.AssetsHelper
import com.example.happyghost.showtimeforkotlin.utils.GsonHelper
import com.google.gson.Gson
import java.io.InputStream

/**
 * @author Zhao Chenping
 * @creat 2017/8/30.
 * @description
 */
class NewsTypeDao {

    /**
     * 更新本地数据，如果数据库新闻列表栏目为 0 则添加头 3 个栏目
     * @param context
     * @param daoSession
     */
    companion object {
        var sAllChannels: MutableList<NewsTypeInfo>?=null
        fun updateLocalData(context: Context,daoSession: DaoSession) {
            sAllChannels = GsonHelper.convertEntities(
                    AssetsHelper.readData(context, "NewsChannel"), NewsTypeInfo::class.java)
            val size = sAllChannels?.size
            sAllChannels?.get(0)
            val newsTypeInfoDao = daoSession.newsTypeInfoDao
            if(newsTypeInfoDao.count().toInt()== 0){
                val mutableList = sAllChannels?.subList(0,4)
                newsTypeInfoDao.insertInTx(mutableList)
            }

        }
    }

}
