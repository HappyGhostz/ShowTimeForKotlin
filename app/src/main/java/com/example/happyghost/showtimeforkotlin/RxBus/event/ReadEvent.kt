package com.example.happyghost.showtimeforkotlin.RxBus.event

import com.example.happyghost.showtimeforkotlin.bean.bookdate.BookMixATocBean
import com.example.happyghost.showtimeforkotlin.bean.bookdate.Recommend

/**
 * @author Zhao Chenping
 * @creat 2017/11/1.
 * @description
 */
class ReadEvent {
    var mChapterBean:BookMixATocBean.MixTocBean.ChaptersBean? = null
    lateinit var mBookId:String
    var mCurrentChapter:Int = 0
    var mIsInsert = false
    var mIsFromDetial = false
    lateinit var mBookBean :Recommend.RecommendBooks
    constructor(chaptersBean: BookMixATocBean.MixTocBean.ChaptersBean?){
        this.mChapterBean = chaptersBean
    }
    constructor(chaptersBean: BookMixATocBean.MixTocBean.ChaptersBean?,bookId:String,currentChapter:Int){
        this.mChapterBean = chaptersBean
        this.mBookId = bookId
        this.mCurrentChapter = currentChapter
    }
    constructor(isInsert:Boolean,bookBean: Recommend.RecommendBooks){
        this.mIsInsert = isInsert
        this.mBookBean = bookBean
    }
    constructor(isFromDetial:Boolean){
        this.mIsFromDetial = isFromDetial
    }
}