package com.example.happyghost.showtimeforkotlin.bean.bookdata

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * @author Zhao Chenping
 * @creat 2017/9/28.
 * @description
 */
class BookMixATocBean() :Parcelable{


    private var mixToc: MixTocBean? = null
    private var ok: Boolean = false

    constructor(parcel: Parcel) : this() {
        ok = parcel.readByte() != 0.toByte()
    }

    fun getMixToc(): MixTocBean? {
        return mixToc
    }

    fun setMixToc(mixToc: MixTocBean) {
        this.mixToc = mixToc
    }

    fun isOk(): Boolean {
        return ok
    }

    fun setOk(ok: Boolean) {
        this.ok = ok
    }

    class MixTocBean {

        var _id: String? = null
        var book: String? = null
        var chaptersCount1: Int = 0
        var chaptersUpdated: String? = null
        var updated: String? = null
        var chapters: List<ChaptersBean>? = null

        class ChaptersBean {
            /**
             * title : 第1章 剑尘
             * link : http://book.my716.com/getBooks.aspx?method=content&bookId=685418&chapterFile=U_685418_201709121536157738_4453_1.txt
             * unreadble : false
             */

            var title: String? = null
            var link: String? = null
            var isUnreadble: Boolean = false
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (ok) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BookMixATocBean> {
        override fun createFromParcel(parcel: Parcel): BookMixATocBean {
            return BookMixATocBean(parcel)
        }

        override fun newArray(size: Int): Array<BookMixATocBean?> {
            return arrayOfNulls(size)
        }
    }

}