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

    /**
     * 按分类获取书籍列表
     *
     * @param gender male、female
     * @param type   hot(热门)、new(新书)、reputation(好评)、over(完结)
     * @param major  玄幻
     * @param minor  东方玄幻、异界大陆、异界争霸、远古神话
     * @param limit  50
     * @return
     */
    @GET("/book/by-categories")
    fun getBooksByCats(@Query("gender") gender: String, @Query("type") type: String, @Query("major") major: String, @Query("minor") minor: String, @Query("start") start: Int, @Query("limit") limit: Int): Observable<BooksByCats>

    /**
     * 获取书籍详情数据
     */
    @GET("/book/{bookId}")
    abstract fun getBookDetail(@Path("bookId") bookId: String): Observable<BookDetail>

    @GET("/post/review/best-by-book")
    abstract fun getHotReview(@Query("book") book: String): Observable<HotReview>

    @GET("/book-list/{bookId}/recommend")
    abstract fun getRecommendBookList(@Path("bookId") bookId: String, @Query("limit") limit: String): Observable<RecommendBookList>

    /**
     * 获取综合讨论区帖子详情
     *
     * @param disscussionId->_id
     * @return
     */
    @GET("/post/{disscussionId}")
    abstract fun getBookDisscussionDetail(@Path("disscussionId") disscussionId: String): Observable<Disscussion>

    /**
     * 获取神评论列表(综合讨论区、书评区、书荒区皆为同一接口)
     *
     * @param disscussionId->_id
     * @return
     */
    @GET("/post/{disscussionId}/comment/best")
    abstract fun getBestComments(@Path("disscussionId") disscussionId: String): Observable<CommentList>

    /**
     * 获取综合讨论区帖子详情内的评论列表
     *
     * @param disscussionId->_id
     * @param start              0
     * @param limit              30
     * @return
     */
    @GET("/post/{disscussionId}/comment")
    abstract fun getBookDisscussionComments(@Path("disscussionId") disscussionId: String, @Query("start") start: String, @Query("limit") limit: String): Observable<CommentList>

    /**
     * 获取书单详情
     *
     * @return
     */
    @GET("/book-list/{bookListId}")
    abstract fun getBookListDetail(@Path("bookListId") bookListId: String): Observable<BookListDetail>

    /**
     * 获取书籍详情讨论列表
     *
     * @param book  bookId
     * @param sort  updated(默认排序)
     * created(最新发布)
     * comment-count(最多评论)
     * @param type  normal
     * vote
     * @param start 0
     * @param limit 20
     * @return
     */
    @GET("/post/by-book")
    fun getBookDetailDisscussionList(@Query("book") book: String, @Query("sort") sort: String, @Query("type") type: String, @Query("start") start: String, @Query("limit") limit: String): Observable<DiscussionList>

    /**
     * 获取书籍详情书评列表
     *
     * @param book  bookId
     * @param sort  updated(默认排序)
     * created(最新发布)
     * helpful(最有用的)
     * comment-count(最多评论)
     * @param start 0
     * @param limit 20
     * @return
     */
    @GET("/post/review/by-book")
    fun getBookDetailReviewList(@Query("book") book: String, @Query("sort") sort: String, @Query("start") start: String, @Query("limit") limit: String): Observable<HotReview>

    /**
     * 获取书评区帖子详情
     *
     * @param bookReviewId->_id
     * @return
     */
    @GET("/post/review/{bookReviewId}")
    fun getBookReviewDetail(@Path("bookReviewId") bookReviewId: String): Observable<BookReview>

    /**
     * 获取书评区、书荒区帖子详情内的评论列表
     *
     * @param bookReviewId->_id
     * @param start             0
     * @param limit             30
     * @return
     */
    @GET("/post/review/{bookReviewId}/comment")
    fun getBookReviewComments(@Path("bookReviewId") bookReviewId: String, @Query("start") start: String, @Query("limit") limit: String): Observable<CommentList>

    /**
     * 获取书荒区帖子详情
     *
     * @param helpId->_id
     * @return
     */
    @GET("/post/help/{helpId}")
    fun getBookHelpDetail(@Path("helpId") helpId: String): Observable<BookHelp>
}