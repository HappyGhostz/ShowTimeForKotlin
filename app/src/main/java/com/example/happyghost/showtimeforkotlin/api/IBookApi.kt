package com.example.happyghost.showtimeforkotlin.api

import com.example.happyghost.showtimeforkotlin.bean.Recommend
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Zhao Chenping
 * @creat 2017/9/18.
 * @description
 */
interface IBookApi {
    @GET("book/recommend")
    fun getRecomend(@Query("gender") gender: String): Observable<Recommend>
}