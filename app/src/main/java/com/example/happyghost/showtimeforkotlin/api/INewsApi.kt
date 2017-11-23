package com.example.happyghost.showtimeforkotlin.api

import com.example.happyghost.showtimeforkotlin.bean.newsdate.NewsDetailInfo
import com.example.happyghost.showtimeforkotlin.bean.newsdate.NewsInfo
import com.example.happyghost.showtimeforkotlin.bean.newsdate.PhotoSetInfo
import com.example.happyghost.showtimeforkotlin.bean.newsdate.SpecialInfo
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService.Companion.CACHE_CONTROL_NETWORK
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService.Companion.AVOID_HTTP403_FORBIDDEN
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * @author Zhao Chenping
 * @creat 2017/9/5.
 * @description
 */
interface INewsApi {
    /**
     * 获取新闻列表
     * eg: http://c.m.163.com/nc/article/headline/T1348647909107/60-20.html
     * http://c.m.163.com/nc/article/list/T1348647909107/60-20.html
     *
     * @param type 新闻类型
     * @param id 新闻ID
     * @param startPage 起始页码
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK, AVOID_HTTP403_FORBIDDEN)
    @GET("nc/article/{type}/{id}/{startPage}-20.html")
     fun getNewsList(@Path("type") type: String, @Path("id") id: String,
                             @Path("startPage") startPage: Int): Observable<Map<String, MutableList<NewsInfo>>>

    /**
     * 获取专题
     * eg: http://c.3g.163.com/nc/special/S1451880983492.html
     *
     * @param specialIde 专题ID
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("nc/special/{specialId}.html")
    fun getSpecial(@Path("specialId") specialIde: String): Observable<Map<String, SpecialInfo>>

    /**
     * 获取新闻详情
     * eg: http://c.3g.163.com/nc/article/BV56RVG600011229/full.html
     *
     * @param newsId 专题ID
     * @return
     */
    @Headers(AVOID_HTTP403_FORBIDDEN)
    @GET("nc/article/{newsId}/full.html")
    abstract fun getNewsDetail(@Path("newsId") newsId: String): Observable<Map<String, NewsDetailInfo>>

    /**
     * 获取新闻中的图集详情
     * eg: http://c.3g.163.com/photo/api/set/0006/2136404.json
     *
     * @param photoId 图集ID
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("photo/api/set/{photoId}.json")
    fun getPhotoSet(@Path("photoId") photoId: String): Observable<PhotoSetInfo>
}