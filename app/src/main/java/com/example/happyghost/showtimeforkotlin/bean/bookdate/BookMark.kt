package com.example.happyghost.showtimeforkotlin.bean.bookdate

import java.io.Serializable

/**
 * @author Zhao Chenping
 * @creat 2017/9/30.
 * @description
 */
class BookMark : Serializable {
    var chapter: Int = 0

    var title: String? = null

    var startPos: Int = 0

    var endPos: Int = 0

    var desc = ""
}