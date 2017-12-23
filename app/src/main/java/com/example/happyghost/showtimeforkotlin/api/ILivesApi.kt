package com.example.happyghost.showtimeforkotlin.api

import com.example.happyghost.showtimeforkotlin.bean.videodata.LiveDetailBean
import com.example.happyghost.showtimeforkotlin.bean.videodata.LiveListBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Zhao Chenping
 * @creat 2017/12/16.
 * @description
 */
interface ILivesApi {
    //请求获取不同游戏的直播列表
    @GET("api/live/list/")
    fun getLiveList(
            @Query("offset") offset: Int,
            @Query("limit") limit: Int,
            @Query("live_type") live_type: String,
            @Query("game_type") game_type: String
    ): Observable<LiveListBean>

    //请求获取直播详情
    @GET("api/live/detail/")
    fun getLiveDetail(
            @Query("live_type") live_type: String,
            @Query("live_id") live_id: String,
            @Query("game_type") game_type: String
    ): Observable<LiveDetailBean>

}