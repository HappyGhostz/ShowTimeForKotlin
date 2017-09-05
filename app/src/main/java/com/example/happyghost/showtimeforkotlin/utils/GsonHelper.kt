package com.example.happyghost.showtimeforkotlin.utils

import com.example.happyghost.showtimeforkotlin.loacaldao.NewsTypeInfo
import com.google.gson.Gson
import com.google.gson.JsonParser

/**
 * @author Zhao Chenping
 * @creat 2017/8/30.
 * @description
 */
class GsonHelper {

    /**
     * 将json数据转化为实体列表数据
     * @param jsonData json字符串
     * @param entityClass 类型
     * @return 实体列表
     */
    companion object {
        var jsonParse = JsonParser()
        var gson = Gson()
       @JvmStatic fun <T>convertEntities(jsonData:String, entityClass: Class<T>) : ArrayList<T>? {
            var entitys  = ArrayList<T>()
            try {
                val jsonArray = jsonParse.parse(jsonData).asJsonArray
                for (element in jsonArray){
                    entitys.add(gson.fromJson(element,entityClass))
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
            return entitys
        }
    }

}