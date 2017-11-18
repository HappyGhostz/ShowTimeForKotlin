package com.example.happyghost.showtimeforkotlin.bean.bookdata

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by e445 on 2017/11/15.
 */
class RecommendBookList {
    /**
     * id : 5617c5f3e8a2065627e4cb85
     * title : 此单在手，书荒不再有！
     * author : 选择
     * desc : 应有尽有！注：随时有可能添加新书！
     * bookCount : 498
     * cover : /agent/http://image.cmfu.com/books/3582111/3582111.jpg
     * collectorCount : 3925
     */

    var booklists: List<RecommendBook>? = null

    class RecommendBook() :Parcelable{
        var id: String? = null
        var title: String? = null
        var author: String? = null
        var desc: String? = null
        var bookCount: Int = 0
        var cover: String? = null
        var collectorCount: Int = 0

        constructor(parcel: Parcel) : this() {
            id = parcel.readString()
            title = parcel.readString()
            author = parcel.readString()
            desc = parcel.readString()
            bookCount = parcel.readInt()
            cover = parcel.readString()
            collectorCount = parcel.readInt()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeString(title)
            parcel.writeString(author)
            parcel.writeString(desc)
            parcel.writeInt(bookCount)
            parcel.writeString(cover)
            parcel.writeInt(collectorCount)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<RecommendBook> {
            override fun createFromParcel(parcel: Parcel): RecommendBook {
                return RecommendBook(parcel)
            }

            override fun newArray(size: Int): Array<RecommendBook?> {
                return arrayOfNulls(size)
            }
        }
    }
}