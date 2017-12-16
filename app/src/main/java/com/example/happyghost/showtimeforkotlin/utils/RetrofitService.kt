package com.example.happyghost.showtimeforkotlin.utils

import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.api.*
import com.example.happyghost.showtimeforkotlin.bean.crossdate.CrossTalkDate
import com.example.happyghost.showtimeforkotlin.bean.bookdate.*
import com.example.happyghost.showtimeforkotlin.bean.crossdate.FunnyPictureDate
import com.example.happyghost.showtimeforkotlin.bean.musicdate.*
import com.example.happyghost.showtimeforkotlin.bean.newsdate.NewsDetailInfo
import com.example.happyghost.showtimeforkotlin.bean.newsdate.NewsInfo
import com.example.happyghost.showtimeforkotlin.bean.newsdate.PhotoSetInfo
import com.example.happyghost.showtimeforkotlin.bean.newsdate.SpecialInfo
import com.example.happyghost.showtimeforkotlin.bean.picturedate.BeautyPicture
import com.example.happyghost.showtimeforkotlin.bean.picturedate.WelfarePhotoList
import com.example.happyghost.showtimeforkotlin.bean.videodata.LiveListBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.URLDecoder
import java.util.concurrent.TimeUnit

/**
 * @author Zhao Chenping
 * @creat 2017/9/5.
 * @description
 */
class RetrofitService  {
    companion object {
        private val HEAD_LINE_NEWS = "T1348647909107"

        //设缓存有效期为1天
        internal val CACHE_STALE_SEC = (60 * 60 * 24 * 1).toLong()
        //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
        private val CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC
        //查询网络的Cache-Control设置
        //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
        internal const val CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=3600"
        // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
        internal const val AVOID_HTTP403_FORBIDDEN = "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11"
        val INCREASE_PAGE = 20
        private val NEWS_HOST = "http://c.3g.163.com/"
        private val BASE_BOOKE_URL = "http://api.zhuishushenqi.com/"
        private val BASE_MUSIC_URL = "http://tingapi.ting.baidu.com/v1/restserver/"
        private val BASE_CROSS_URL ="http://iu.snssdk.com/neihan/stream/"
        private val WELF_CROSS_URL ="http://gank.io/"
        private val BEAUTY_PICYURE_URL ="http://image.baidu.com/data/"
        private val LIVE_BASE_URL ="http://api.maxjia.com/"
        var iNewsApi: INewsApi? = null
        var iBookApi: IBookApi? =null
        var iMusicApi:IMusicsApi?=null
        var iCrossApi: ICrossApi?=null
        var iWelfApi: IWelfApi?=null
        var iBeautyApi: IBeautyApi?=null
        var iLiveApi: ILivesApi?=null


        fun init(){
            val cache = Cache(File(AppApplication.instance.getContext().cacheDir, "HttpCache"), 1024 * 1024 * 1024)
            val okHttpClient = OkHttpClient.Builder().cache(cache)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(sLoggingInterceptor)
                    .addInterceptor(sRewriteCacheControlInterceptor)
                    .addNetworkInterceptor(sRewriteCacheControlInterceptor)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build()
            //这个配置缓存会使read页面在使用一段时间后崩溃
            val bookokHttpClient = OkHttpClient.Builder().cache(cache)
                    .retryOnConnectionFailure(true)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build()
            val retrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(NEWS_HOST)
                    .build()
            iNewsApi = retrofit.create(INewsApi::class.java)

            var retrofitBook = Retrofit.Builder()
                    .client(bookokHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_BOOKE_URL)
                    .build()
            iBookApi = retrofitBook.create(IBookApi::class.java)
            var retrofitMusic = Retrofit.Builder()
                    .client(bookokHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_MUSIC_URL)
                    .build()
            iMusicApi = retrofitMusic.create(IMusicsApi::class.java)
            var retrofitCross = Retrofit.Builder()
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_CROSS_URL)
                    .build()
            iCrossApi = retrofitCross.create(ICrossApi::class.java)
            var retrofitWelf= Retrofit.Builder()
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(WELF_CROSS_URL)
                    .build()
            iWelfApi = retrofitWelf.create(IWelfApi::class.java)
            var retrofitBeauty= Retrofit.Builder()
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BEAUTY_PICYURE_URL)
                    .build()
            iBeautyApi = retrofitBeauty.create(IBeautyApi::class.java)
            var retrofitLive= Retrofit.Builder()
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(LIVE_BASE_URL)
                    .build()
            iLiveApi = retrofitLive.create(ILivesApi::class.java)

        }
        //云响应拦截器，用于配置缓存
        var sRewriteCacheControlInterceptor = Interceptor {
            chain ->
            val request = chain.request()
            if(!NetUtil.isNetworkAvailable(AppApplication.instance.getContext())){
                request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            }
            val response = chain.proceed(request)

            if(NetUtil.isNetworkAvailable(AppApplication.instance.getContext())){
                val cacheControl = request.cacheControl().toString()
                return@Interceptor response.newBuilder()
                        .header("Cache_Control",cacheControl)
                        .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11")
                        .removeHeader("Pragma")
                        .build()
            }else{
                return@Interceptor response.newBuilder()
                        .header("Cache-Control", "public, " + CACHE_CONTROL_CACHE)
                        .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11")
                        .removeHeader("Pragma")
                        .build()
            }

        }
        //打印返回json的拦截器
        var sLoggingInterceptor = Interceptor{
            chain->
            val request = chain.request()
            val buffer = Buffer()
            if(request==null){
//                Log.e("RetrofitService","request is null")
            }else{
                request.body()?.writeTo(buffer)
//                Log.d("request", request.url().toString()  + parseParams(request.body(), buffer))
            }
            val response = chain.proceed(request)
            return@Interceptor response
        }
        fun parseParams(requestBody: RequestBody?, buffer: Buffer): String? {
            if(requestBody?.contentType()!=null&&!requestBody.contentType().toString().contains("multipart")){
                return URLDecoder.decode(buffer.readUtf8(),"UTF-8")
            }
            return null
        }

        /**
         * 获取新闻列表
         * @return
         */
        fun getNewsList(newsId: String, page: Int): Observable<NewsInfo>? {
            val type: String
            if (newsId == HEAD_LINE_NEWS) {
                type = "headline"
            } else {
                type = "list"
            }

            if (iNewsApi != null) {
                return iNewsApi!!.getNewsList(type, newsId, page * INCREASE_PAGE)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(flatMapNews(newsId))
            }
           return null
        }
        //获取专题新闻
        fun getNewsSpeciaList(newsId:String):Observable<SpecialInfo>{
            return iNewsApi!!.getSpecial(newsId)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(flatMapSpecial(newsId))
        }
        //获取新闻详情
        fun getNewsDetial(newsId: String):Observable<NewsDetailInfo>{
            return iNewsApi!!.getNewsDetail(newsId)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap{
                        return@flatMap Observable.just(it.get(newsId))
                    }
        }
        //获取图集详情
        fun getPhotoSetNews(newsId: String):Observable<PhotoSetInfo>{
            return iNewsApi!!.getPhotoSet(StringUtils.clipPhotoSetId(newsId)!!)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
        //获取书籍推荐列表
        fun getBookRack(gender:String):Observable<Recommend>{
            return iBookApi!!.getRecomend(gender)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
        //获取书籍分类列表
        fun getBookClassifyInfo():Observable<CategoryList>{
            return iBookApi!!.getCategoryList()
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
        //获取书荒区帖子列表
        /**
         * 获取社区书荒区列表
         */
        fun getBookHelpListInfo(start: String, limit: String): Observable<BookHelpList> {
            return iBookApi!!.getBookHelpList("all", "updated", start, limit, "")
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
        /**
         * 获取排行榜列表
         */
        fun getBookRankList():Observable<RankingListBean>{
            return iBookApi!!.getRanking()
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
        /**
         * 获取正版源
         */
        fun getBookMixATocInfo(bookId: String, view: String): Observable<BookMixATocBean> {
            return iBookApi!!.getBookMixAToc(bookId, view)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
        }
        /**
         * 获取章节列表
         */
        fun getChapterBody(url: String):Observable<ChapterReadBean>{
            return iBookApi!!.getChapterRead(url)

        }

        /**
         * 获取分类列表
         * @param mSex
         * @param mType
         * @param mMajor
         * @param mMinor
         * @param start
         * @param limit
         * @return
         */
        fun getBooksByCatsInfo(mSex: String, mType: String, mMajor: String, mMinor: String, start: Int, limit: Int): Observable<BooksByCats> {
            return iBookApi!!.getBooksByCats(mSex, mType, mMajor, mMinor, start, limit)
        }

        /**
         * 获取书籍详情
         * @param mBookId
         * @return
         */
        fun getBookDetailInfo(mBookId: String): Observable<BookDetail> {
            return iBookApi!!.getBookDetail(mBookId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun getHotReview(mBookId: String): Observable<HotReview> {
            return iBookApi!!.getHotReview(mBookId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun getRecommendBookList(mBookId: String, limit: String): Observable<RecommendBookList> {
            return iBookApi!!.getRecommendBookList(mBookId, limit)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
        /**
         * 获取评论详情
         */
        fun getBookDisscussionDetail(disscussionId: String): Observable<Disscussion> {
            return iBookApi!!.getBookDisscussionDetail(disscussionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun getBestComments(disscussionId: String): Observable<CommentList> {
            return iBookApi!!.getBestComments(disscussionId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun getBookDisscussionComments(disscussionId: String, start: String, limit: String): Observable<CommentList> {
            return iBookApi!!.getBookDisscussionComments(disscussionId, start, limit)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun getBookListDetail(bookListId: String): Observable<BookListDetail> {
            return iBookApi!!.getBookListDetail(bookListId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun getBookDetailDisscussionList(book: String, sort: String, type: String, start: String, limit: String): Observable<DiscussionList> {
            return iBookApi!!.getBookDetailDisscussionList(book, sort, type, start, limit)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun getBookDetailReviewList(book: String, sort: String, start: String, limit: String): Observable<HotReview> {
            return iBookApi!!.getBookDetailReviewList(book, sort, start, limit)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun getBookReviewDetail(bookReviewId: String): Observable<BookReview> {
            return  iBookApi!!.getBookReviewDetail(bookReviewId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun getBookReviewComments(bookReviewId: String, start: String, limit: String): Observable<CommentList> {
            return  iBookApi!!.getBookReviewComments(bookReviewId, start, limit)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun getBookHelpDetail(mHelpBeanId: String): Observable<BookHelp> {
            return iBookApi!!.getBookHelpDetail(mHelpBeanId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun getRanking(mRankType: String): Observable<Rankings> {
            return iBookApi!!.getRanking(mRankType)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun getAutoComplete(query: String): Observable<AutoComplete> {
            return iBookApi!!.autoComplete(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun getHotWord(): Observable<HotWord> = iBookApi!!.getHotWord()
                                                  .subscribeOn(Schedulers.io())
                                                  .observeOn(AndroidSchedulers.mainThread())

        fun getSearchResult(query: String): Observable<SearchDetail> = iBookApi!!.searchBooks(query)
                                                  .subscribeOn(Schedulers.io())
                                                  .observeOn(AndroidSchedulers.mainThread())

        /**
         * 获得歌单全部列表
         * @param musicUrlFormat
         * @param musicUrlFrom
         * @param musicUrlMethodGedan
         * @param pageSize
         * @param startPage
         */
        fun getMusicListAll(musicUrlFormat: String, musicUrlFrom: String, musicUrlMethodGedan: String, pageSize: Int, startPage: Int): Observable<WrapperSongListInfo> {
            return iMusicApi!!.getSongListAll(musicUrlFormat, musicUrlFrom, musicUrlMethodGedan, pageSize, startPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun getRankMusicListAll(musicUrlFormat: String, musicUrlFrom: String, musicUrlMethodRankinglist: String, musicUrlRankinglistFlag: Int): Observable<RankingListItem> {
            return iMusicApi!!.getRankingListAll(musicUrlFormat, musicUrlFrom, musicUrlMethodRankinglist, musicUrlRankinglistFlag)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun getMusicListDetialAll(musicUrlFormat: String, musicUrlFrom: String, musicUrlMethodSonglistDetail: String, songListid: String): Observable<SongListDetail> {
            return iMusicApi!!.getSongListDetail(musicUrlFormat, musicUrlFrom, musicUrlMethodSonglistDetail, songListid)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun getRankPlayList(musicUrlFormat: String, musicUrlFrom: String, musicUrlMethodRankingDetail: String, mType: Int, offset: Int, size: Int, mFields: String): Observable<RankingListDetail> {
            return iMusicApi!!.getRankingListDetail(musicUrlFormat, musicUrlFrom, musicUrlMethodRankingDetail, mType, offset, size, mFields)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun getSongDetail(musicUrlFrom2: String, musicUrlVersion: String, musicUrlFormat: String, musicUrlMethodSongDetail: String, song_id: String): Observable<SongDetailInfo> {
            return iMusicApi!!.getSongDetail(musicUrlFrom2, musicUrlVersion, musicUrlFormat, musicUrlMethodSongDetail, song_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
        fun getSearchMusicList(method:String,query: String,pageSize: Int,page: Int,format:String):Observable<MusicSearchList>{
            return iMusicApi!!.getSearchSons(method,query,pageSize,page,format)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
        fun getCrossTalk(locTime:Int,minTime:Int,content_type:Int,pageSize: Int):Observable<CrossTalkDate>{
            return iCrossApi!!.getCrossTalk(1,1,locTime,minTime,content_type,-1,pageSize,"android")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
        fun getFunnyPicture(locTime:Int,minTime:Int,content_type:Int,pageSize: Int):Observable<FunnyPictureDate>{
            return iCrossApi!!.getFunnyPicture(1,1,locTime,minTime,content_type,-1,pageSize,"android")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        /**
         * 获取福利图片
         * @return
         */
        fun getWelfarePhoto(page: Int): Observable<WelfarePhotoList> {
            return iWelfApi!!.getWelfarePhoto(page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

        }
        /**
         * 获取美女图片
         * http://image.baidu.com/data/imgs?sort=0&pn=0&rn=20&col=美女&tag3=&p=channel&from=1
         */
        fun getBeautyPicture(startImage:Int,size: Int,col:String,tag:String):Observable<BeautyPicture>{
            return iBeautyApi!!.getWelfarePhoto(0,startImage,size,col,tag,"","channel",1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
        /**
         * 获取直播列表
         */
        fun getLiveListDate(offSet:Int,limit:Int,liveType:String,gameType:String):Observable<LiveListBean>{
            return iLiveApi!!.getLiveList(offSet,limit,liveType,gameType)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        /**
         * 类型转换
         * @param newsId 新闻类型
         * @return
         */
        fun flatMapNews(newsId:String):Function<Map<String, MutableList<NewsInfo>>, Observable<NewsInfo>>{
            return Function {
                return@Function Observable.fromIterable(it.get(newsId))
            }
        }
        fun flatMapSpecial(newsId: String):Function<Map<String, SpecialInfo>, Observable<SpecialInfo>>{
            return Function {
                return@Function Observable.just(it.get(newsId))
            }
        }
    }
}