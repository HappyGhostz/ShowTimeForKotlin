package com.example.happyghost.showtimeforkotlin.api

import com.example.happyghost.showtimeforkotlin.bean.NewsInfo
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
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
}