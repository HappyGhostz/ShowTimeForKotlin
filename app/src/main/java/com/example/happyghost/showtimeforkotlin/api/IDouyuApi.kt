package com.example.happyghost.showtimeforkotlin.api

import com.example.happyghost.showtimeforkotlin.bean.videodata.LiveListBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Zhao Chenping
 * @creat 2017/12/22.
 * @description
 */
interface IDouyuApi {
    //请求斗鱼的不同游戏的直播列表
    @GET("live/{type}/")
    fun getDouyuLiveList(
            @Path("type") game_type: String,
            @Query("offset") offset: Int,
            @Query("limit") limit: Int,
            @Query("client_sys") client: String
    ): Observable<LiveListBean>
}