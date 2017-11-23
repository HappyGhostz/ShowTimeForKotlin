package com.example.happyghost.showtimeforkotlin.bean.musicdate

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by e445 on 2017/11/23.
 */
data class SongLocalBean(var title: String?=null,
                         var path: String?=null,
                         var size: String?=null,
                         var duration: Int = 0,
                         var artist: String?=null,
                         var albun_id: Int = 0,
                         var _id: Int = 0,
                         var album: String?=null,
                         var picurl: String?=null): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(path)
        parcel.writeString(size)
        parcel.writeInt(duration)
        parcel.writeString(artist)
        parcel.writeInt(albun_id)
        parcel.writeInt(_id)
        parcel.writeString(album)
        parcel.writeString(picurl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SongLocalBean> {
        override fun createFromParcel(parcel: Parcel): SongLocalBean {
            return SongLocalBean(parcel)
        }

        override fun newArray(size: Int): Array<SongLocalBean?> {
            return arrayOfNulls(size)
        }
    }
}