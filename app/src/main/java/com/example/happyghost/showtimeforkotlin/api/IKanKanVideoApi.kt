package com.example.happyghost.showtimeforkotlin.api

import com.example.happyghost.showtimeforkotlin.bean.videodata.VideoListDate
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @author Zhao Chenping
 * @creat 2017/12/25.
 * @description
 */
interface IKanKanVideoApi {
//    http://app.pearvideo.com/clt/jsp/v2/home.jsp
//    http://app.pearvideo.com/clt/jsp/v2/getCategoryConts.jsp
//    http://app.pearvideo.com/clt/jsp/v2/content.jsp?contId=1064146
    /**
     * 头条
     */
    @GET("home.jsp")
    fun getKankanVideoList(
            @HeaderMap  headers:HashMap<String, String>,
            @Query("lastLikeIds") lastLikeIds: String
    ): Observable<VideoListDate>
    //这里我实际抓取了一下Get也可以
    @FormUrlEncoded
    @POST("getCategoryConts.jsp")
    fun getKankanVideoFromCate(@HeaderMap  headers:HashMap<String, String>,
                               @Field("hotPageidx")page:Int,
                               @Field("start")start:Int,
                               @Field("categoryId")categoryId:String):Observable<VideoListDate>
}