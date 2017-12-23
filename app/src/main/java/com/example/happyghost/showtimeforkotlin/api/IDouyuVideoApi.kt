package com.example.happyghost.showtimeforkotlin.api

import com.example.happyghost.showtimeforkotlin.bean.videodata.DouyuVideoInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Zhao Chenping
 * @creat 2017/12/22.
 * @description
 */
interface IDouyuVideoApi {
    //请求斗鱼的直播房间
//    @GET("{roomId}")
//    fun getDouyuLiveList(
//            @Path("roomId") roomId: String,
//            @Query("rate") rate: Int,
//            @HeaderMap headers:HashMap<String, String>
//    ): Observable<DouyuVideoInfo>
    //请求斗鱼的直播房间
    @GET("live")
    fun getDouyuLiveList(
            @Query("roomId") roomId: String
    ): Observable<DouyuVideoInfo>
}