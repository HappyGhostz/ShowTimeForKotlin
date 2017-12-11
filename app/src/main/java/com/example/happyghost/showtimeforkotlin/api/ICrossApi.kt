package com.example.happyghost.showtimeforkotlin.api

import com.example.happyghost.showtimeforkotlin.bean.crossdate.CrossTalkDate
import com.example.happyghost.showtimeforkotlin.bean.crossdate.FunnyPictureDate
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Zhao Chenping
 * @creat 2017/12/8.
 * @description
 */
interface ICrossApi {
    // http://iu.snssdk.com/neihan/stream/mix/v1/?mpic=1&webp=1&content_type=-102&message_cursor=-1&count=30&device_platform=android
    @GET("mix/v1/")
    fun getCrossTalk(@Query("mpic") mpic: Int,
                    @Query("webp")webp:Int,
                     @Query("am_loc_time")locTime:Int,
                     @Query("min_time")minTime:Int,
                    @Query("content_type")content_type:Int,
                    @Query("message_cursor")message_cursor:Int,
                    @Query("count")count:Int,
                    @Query("device_platform")device_platform:String): Observable<CrossTalkDate>
    // http://iu.snssdk.com/neihan/stream/mix/v1/?mpic=1&webp=1&content_type=-102&message_cursor=-1&count=30&device_platform=android
    @GET("mix/v1/")
    fun getFunnyPicture(@Query("mpic") mpic: Int,
                     @Query("webp")webp:Int,
                     @Query("am_loc_time")locTime:Int,
                     @Query("min_time")minTime:Int,
                     @Query("content_type")content_type:Int,
                     @Query("message_cursor")message_cursor:Int,
                     @Query("count")count:Int,
                     @Query("device_platform")device_platform:String): Observable<FunnyPictureDate>
}