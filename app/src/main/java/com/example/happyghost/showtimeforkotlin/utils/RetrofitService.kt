package com.example.happyghost.showtimeforkotlin.utils

import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.api.IBookApi
import com.example.happyghost.showtimeforkotlin.api.INewsApi
import com.example.happyghost.showtimeforkotlin.bean.bookdata.*
import com.example.happyghost.showtimeforkotlin.bean.newsdata.NewsDetailInfo
import com.example.happyghost.showtimeforkotlin.bean.newsdata.NewsInfo
import com.example.happyghost.showtimeforkotlin.bean.newsdata.PhotoSetInfo
import com.example.happyghost.showtimeforkotlin.bean.newsdata.SpecialInfo
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
        var iNewsApi: INewsApi? = null
        var iBookApi: IBookApi? =null


        fun init(){
            val cache = Cache(File(AppApplication.instance.getContext().cacheDir, "HttpCache"), 1024 * 1024 * 1024)
            val okHttpClient = OkHttpClient.Builder().cache(cache)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(sLoggingInterceptor)
                    .addInterceptor(sRewriteCacheControlInterceptor)
                    .addNetworkInterceptor(sRewriteCacheControlInterceptor)
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
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_BOOKE_URL)
                    .build()
            iBookApi = retrofitBook.create(IBookApi::class.java)

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