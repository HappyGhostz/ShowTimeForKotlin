package com.example.happyghost.showtimeforkotlin.ui.book.read

import com.example.happyghost.showtimeforkotlin.bean.bookdate.BookMixATocBean
import com.example.happyghost.showtimeforkotlin.bean.bookdate.ChapterReadBean
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/9/28.
 * @description
 */
interface IReadView :IBaseView{
    fun loadBookToc(list: List<BookMixATocBean.MixTocBean.ChaptersBean>)

    fun loadChapterRead(data: ChapterReadBean.Chapter?, chapter: Int)
}