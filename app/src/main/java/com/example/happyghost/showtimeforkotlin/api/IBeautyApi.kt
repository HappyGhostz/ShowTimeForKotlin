package com.example.happyghost.showtimeforkotlin.api

import com.example.happyghost.showtimeforkotlin.bean.picturedate.BeautyPicture
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService.Companion.CACHE_CONTROL_NETWORK
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * @author Zhao Chenping
 * @creat 2017/12/14.
 * @description
 */
interface IBeautyApi {
    /**
     * 获取美女图片
     * API获取途径http://www.jb51.net/article/61266.htm
     * eg: http://image.baidu.com/data/imgs?sort=0&pn=0&rn=20&col=美女&tag=全部&tag3=&p=channel&from=1
     * 通过分析，推断并验证了其中字段的含义，col表示频道，tag表示的是全部的美女，也可以是其他Tag，pn表示从第几张图片开始，rn表示获取多少张
     * @param page 页码
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("imgs")
    fun getWelfarePhoto(@Query("sort") sort:Int,
                                 @Query("pn")startImage:Int,
                                 @Query("rn")size:Int,
                                 @Query("col")col:String,
                                 @Query("tag")tag:String,
                                 @Query("tag3")tag3:String,
                                 @Query("p")channel:String,
                                 @Query("from")from:Int): Observable<BeautyPicture>
}