package com.example.happyghost.showtimeforkotlin.api

import com.example.happyghost.showtimeforkotlin.bean.musicdate.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by e445 on 2017/11/24.
 */
interface IMusicsApi {


    //获取全部歌单
    @GET("ting")
    @Headers("user-agent:Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
   fun getSongListAll(@Query("format") format: String,
                                @Query("from") from: String,
                                @Query("method") method: String,
                                @Query("page_size") page_size: Int,
                                @Query("page_no") page_no: Int): Observable<WrapperSongListInfo>

    //获取全部榜单
    @GET("ting")
    @Headers("user-agent:Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
    fun getRankingListAll(@Query("format") format: String,
                                   @Query("from") from: String,
                                   @Query("method") method: String,
                                   @Query("kflag") kflag: Int): Observable<RankingListItem>

    //获取某个榜单中歌曲信息
    @GET("ting")
    @Headers("user-agent:Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
    fun getRankingListDetail(@Query("format") format: String,
                                      @Query("from") from: String,
                                      @Query("method") method: String,
                                      @Query("type") type: Int,
                                      @Query("offset") offset: Int,
                                      @Query("size") size: Int,
                                      @Query("fields") fields: String): Observable<RankingListDetail>

    //获取某个歌单中的信息
    @GET("ting")
    @Headers("user-agent:Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
    fun getSongListDetail(@Query("format") format: String,
                                   @Query("from") from: String,
                                   @Query("method") method: String,
                                   @Query("listid") listid: String): Observable<SongListDetail>

    //获取某个歌曲的信息
    @GET("ting")
    @Headers("user-agent:Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
    fun getSongDetail(@Query("from") from: String,
                               @Query("version") version: String,
                               @Query("format") format: String,
                               @Query("method") method: String,
                               @Query("songid") songid: String): Observable<SongDetailInfo>
}