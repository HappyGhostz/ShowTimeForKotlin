package com.example.happyghost.showtimeforkotlin.api

import com.example.happyghost.showtimeforkotlin.bean.picturedate.WelfarePhotoList
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService.Companion.CACHE_CONTROL_NETWORK
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * @author Zhao Chenping
 * @creat 2017/12/12.
 * @description
 */
interface IWelfApi {
    /**
     * 获取福利图片
     * eg: http://gank.io/api/data/福利/10/1
     *
     * @param page 页码
     * @return
     */
    @Headers(CACHE_CONTROL_NETWORK)
    @GET("/api/data/福利/10/{page}")
    abstract fun getWelfarePhoto(@Path("page") page: Int): Observable<WelfarePhotoList>
}