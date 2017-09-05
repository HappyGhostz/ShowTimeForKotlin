package com.example.happyghost.showtimeforkotlin.bean

/**
 * @author Zhao Chenping
 * @creat 2017/9/5.
 * @description
 */
class NewsMultiItem() {
    companion object {
        var NEWS_INFO_NORMAL:Int = 1
        var NEWS_INFO_PHOTO_SET :Int = 2
    }
    var mNewsType : Int = 0
    lateinit var mNewsInfo :NewsInfo
    constructor(newsInfo: NewsInfo,newsType:Int) : this(){
        this.mNewsInfo = newsInfo
        this.mNewsType = newsType
    }
    fun  getNewsInfo() :NewsInfo{
        return mNewsInfo
    }
    fun getNewsType():Int{
        return mNewsType
    }
}