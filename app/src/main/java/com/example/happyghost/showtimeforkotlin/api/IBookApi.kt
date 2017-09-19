package com.example.happyghost.showtimeforkotlin.api

import com.example.happyghost.showtimeforkotlin.bean.bookdata.CategoryList
import com.example.happyghost.showtimeforkotlin.bean.bookdata.Recommend
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

    /**
     * 获取分类
     *
     * @return
     */
    @GET("/cats/lv2/statistics")
    fun getCategoryList(): Observable<CategoryList>
}