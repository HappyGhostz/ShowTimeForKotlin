package com.example.happyghost.showtimeforkotlin.RxBus.event

/**
 * @author Zhao Chenping
 * @creat 2017/11/10.
 * @description
 */
class DownloadProgressEvent {
    var bookId: String

    var message: String

    var isAlreadyDownload = false

    constructor(bookId: String, message: String, isAlreadyDownload: Boolean) {
        this.bookId = bookId
        this.message = message
        this.isAlreadyDownload = isAlreadyDownload
    }
}