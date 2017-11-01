package com.example.happyghost.showtimeforkotlin.RxBus.event

import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookMixATocBean

/**
 * @author Zhao Chenping
 * @creat 2017/11/1.
 * @description
 */
class ReadEvent {
    var mChapterBean:BookMixATocBean.MixTocBean.ChaptersBean?
    constructor(chaptersBean: BookMixATocBean.MixTocBean.ChaptersBean?){
        this.mChapterBean = chaptersBean
    }
}