package com.example.happyghost.showtimeforkotlin.ui.book.read

import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookMixATocBean
import com.example.happyghost.showtimeforkotlin.bean.bookdata.ChapterReadBean
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/9/28.
 * @description
 */
interface IReadView :IBaseView{
    abstract fun loadBookToc(list: List<BookMixATocBean.MixTocBean.ChaptersBean>)

    abstract fun loadChapterRead(data: ChapterReadBean.Chapter?, chapter: Int)
}