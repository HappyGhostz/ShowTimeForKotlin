package com.example.happyghost.showtimeforkotlin.RxBus.event

/**
 * @author Zhao Chenping
 * @creat 2017/11/10.
 * @description
 */
class DownloadMessageEvent {
    var bookId: String

    var message: String

    var isComplete = false

    constructor(bookId: String, message: String, isComplete: Boolean) {
        this.bookId = bookId
        this.message = message
        this.isComplete = isComplete
    }
}