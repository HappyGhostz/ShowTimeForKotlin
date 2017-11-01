package com.example.happyghost.showtimeforkotlin.RxBus.event

import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookMixATocBean

/**
 * @author Zhao Chenping
 * @creat 2017/11/1.
 * @description
 */
class ReadEvent {
    var mChapterBean:BookMixATocBean.MixTocBean.ChaptersBean?
    lateinit var mBookId:String
    var mCurrentChapter:Int = 0
    constructor(chaptersBean: BookMixATocBean.MixTocBean.ChaptersBean?){
        this.mChapterBean = chaptersBean
    }
    constructor(chaptersBean: BookMixATocBean.MixTocBean.ChaptersBean?,bookId:String,currentChapter:Int){
        this.mChapterBean = chaptersBean
        this.mBookId = bookId
        this.mCurrentChapter = currentChapter
    }
}