package com.example.happyghost.showtimeforkotlin.RxBus.event

import android.os.Parcel
import android.os.Parcelable
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookMixATocBean

/**
 * @author Zhao Chenping
 * @creat 2017/11/10.
 * @description
 */
class DownloadEvent : Parcelable {
    lateinit var bookId: String

    lateinit var list: List<BookMixATocBean.MixTocBean.ChaptersBean>

    var start: Int = 0

    var end: Int = 0

    /**
     * 是否已经开始下载
     */
    var isStartDownload = false

    /**
     * 是否中断下载
     */
    var isCancel = false

    /**
     * 是否下载完成
     */
    var isFinish = false

    constructor(parcel: Parcel) : this() {
        bookId = parcel.readString()
        list = parcel.createTypedArrayList(BookMixATocBean.MixTocBean.ChaptersBean.CREATOR)
        start = parcel.readInt()
        end = parcel.readInt()
        isStartDownload = parcel.readByte() != 0.toByte()
        isCancel = parcel.readByte() != 0.toByte()
        isFinish = parcel.readByte() != 0.toByte()
    }

    constructor(bookId: String, list: List<BookMixATocBean.MixTocBean.ChaptersBean>, start: Int, end: Int) : this() {
        this.bookId = bookId
        this.list = list
        this.start = start
        this.end = end
    }

    /**
     * 空事件。表示通知继续执行下一条任务
     */
    constructor(){}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(bookId)
        parcel.writeTypedList(list)
        parcel.writeInt(start)
        parcel.writeInt(end)
        parcel.writeByte(if (isStartDownload) 1 else 0)
        parcel.writeByte(if (isCancel) 1 else 0)
        parcel.writeByte(if (isFinish) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DownloadEvent> {
        override fun createFromParcel(parcel: Parcel): DownloadEvent {
            return DownloadEvent(parcel)
        }

        override fun newArray(size: Int): Array<DownloadEvent?> {
            return arrayOfNulls(size)
        }
    }
}