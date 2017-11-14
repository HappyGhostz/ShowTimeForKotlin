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

    class MixTocBean() :Parcelable{

        var _id: String? = null
        var book: String? = null
        var chaptersCount1: Int = 0
        var chaptersUpdated: String? = null
        var updated: String? = null
        var chapters: List<ChaptersBean>? = null

        constructor(parcel: Parcel) : this() {
            _id = parcel.readString()
            book = parcel.readString()
            chaptersCount1 = parcel.readInt()
            chaptersUpdated = parcel.readString()
            updated = parcel.readString()
            chapters = parcel.createTypedArrayList(ChaptersBean.CREATOR)
        }

        class ChaptersBean() :Parcelable{
            /**
             * title : 第1章 剑尘
             * link : http://book.my716.com/getBooks.aspx?method=content&bookId=685418&chapterFile=U_685418_201709121536157738_4453_1.txt
             * unreadble : false
             */

            var title: String? = null
            var link: String? = null
            var bookid :String? = null
            var isUnreadble: Boolean = false

            constructor(parcel: Parcel) : this() {
                title = parcel.readString()
                link = parcel.readString()
                isUnreadble = parcel.readByte() != 0.toByte()
            }

            override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(title)
                parcel.writeString(link)
                parcel.writeByte(if (isUnreadble) 1 else 0)
            }

            override fun describeContents(): Int {
                return 0
            }

            companion object CREATOR : Parcelable.Creator<ChaptersBean> {
                override fun createFromParcel(parcel: Parcel): ChaptersBean {
                    return ChaptersBean(parcel)
                }

                override fun newArray(size: Int): Array<ChaptersBean?> {
                    return arrayOfNulls(size)
                }
            }
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(_id)
            parcel.writeString(book)
            parcel.writeInt(chaptersCount1)
            parcel.writeString(chaptersUpdated)
            parcel.writeString(updated)
            parcel.writeTypedList(chapters)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<MixTocBean> {
            override fun createFromParcel(parcel: Parcel): MixTocBean {
                return MixTocBean(parcel)
            }

            override fun newArray(size: Int): Array<MixTocBean?> {
                return arrayOfNulls(size)
            }
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