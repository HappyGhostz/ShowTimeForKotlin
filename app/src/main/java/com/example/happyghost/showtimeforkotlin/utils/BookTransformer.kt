package com.example.happyghost.showtimeforkotlin.utils

import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookMixATocBean
import com.example.happyghost.showtimeforkotlin.bean.bookdata.Recommend
import com.example.happyghost.showtimeforkotlin.loacaldao.BookChapterInfo
import com.example.happyghost.showtimeforkotlin.loacaldao.LocalBookInfo
import java.util.ArrayList

/**
 * @author Zhao Chenping
 * @creat 2017/9/27.
 * @description
 */
class BookTransformer {
    companion object {
        fun localBookConvertRecommendBooks(book:LocalBookInfo):Recommend.RecommendBooks{
            var transFormer =Recommend.RecommendBooks()
            transFormer.id = book.id
            transFormer._id =book.url
            transFormer.author = book.author
            transFormer.cover = book.cover
            transFormer.shortIntro = book.shortIntro
            transFormer.title = book.title
            transFormer.hasCp = book.hasCp
            transFormer.isTop = book.isTop
            transFormer.isSeleted = book.isSeleted
            transFormer.showCheckBox = book.showCheckBox
            transFormer.isFromSD = book.isFromSD
            transFormer.path = book.path
            transFormer.latelyFollower = book.latelyFollower
            transFormer.retentionRatio = book.retentionRatio
            transFormer.updated = book.updated
            transFormer.chaptersCount =book.chaptersCount
            transFormer.lastChapter = book.lastChapter
            transFormer.recentReadingTime = book.recentReadingTime
            return transFormer
        }
        fun RecommendBooksConvertlocalBook(book:Recommend.RecommendBooks):LocalBookInfo{
            var localBook = LocalBookInfo()
            localBook.id = book.id
            localBook.url =book._id
            localBook.author = book.author
            localBook.cover = book.cover
            localBook.shortIntro = book.shortIntro
            localBook.title = book.title
            localBook.hasCp = book.hasCp
            localBook.isTop = book.isTop
            localBook.isSeleted = book.isSeleted
            localBook.showCheckBox = book.showCheckBox
            localBook.isFromSD = book.isFromSD
            localBook.path = book.path
            localBook.latelyFollower = book.latelyFollower
            localBook.retentionRatio = book.retentionRatio
            localBook.updated = book.updated
            localBook.chaptersCount =book.chaptersCount
            localBook.lastChapter = book.lastChapter
            localBook.recentReadingTime = book.recentReadingTime
            return localBook
        }
        fun locaBookChaptersConvertChaptersBean(queryChapters: MutableList<BookChapterInfo>?): ArrayList<BookMixATocBean.MixTocBean.ChaptersBean> {
            var chapters = ArrayList<BookMixATocBean.MixTocBean.ChaptersBean>()
            val chaptersBean = BookMixATocBean.MixTocBean.ChaptersBean()
            queryChapters?.forEach {
                chaptersBean.title = it.title
                chaptersBean.link = it.link
                chapters.add(chaptersBean)
            }
            return chapters
        }
    }
}