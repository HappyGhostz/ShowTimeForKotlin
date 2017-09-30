package com.example.happyghost.showtimeforkotlin.api

import com.example.happyghost.showtimeforkotlin.bean.bookdata.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
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

    /**
     * 获取书荒区帖子列表
     * 全部、默认排序  http://api.zhuishushenqi.com/post/help?duration=all&sort=updated&start=0&limit=20&distillate=
     * 精品、默认排序  http://api.zhuishushenqi.com/post/help?duration=all&sort=updated&start=0&limit=20&distillate=true
     *
     * @param duration   all
     * @param sort       updated(默认排序)
     * created(最新发布)
     * comment-count(最多评论)
     * @param start      0
     * @param limit      20
     * @param distillate true(精品) 、空字符（全部）
     * @return
     */
    @GET("/post/help")
    fun getBookHelpList(@Query("duration") duration: String, @Query("sort") sort: String, @Query("start") start: String,
                                 @Query("limit") limit: String, @Query("distillate") distillate: String): Observable<BookHelpList>

    /**
     * 获取所有排行榜
     *
     * @return
     */
    @GET("/ranking/gender")
    fun getRanking(): Observable<RankingListBean>

    @GET("/mix-atoc/{bookId}")
    fun getBookMixAToc(@Path("bookId") bookId: String, @Query("view") view: String): Observable<BookMixATocBean>

    @GET("http://chapter2.zhuishushenqi.com/chapter/{url}")
    fun getChapterRead(@Path("url") url: String): Observable<ChapterReadBean>
}