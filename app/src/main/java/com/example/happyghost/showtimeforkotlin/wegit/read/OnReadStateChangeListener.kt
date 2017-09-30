package com.example.happyghost.showtimeforkotlin.wegit.read

/**
 * @author Zhao Chenping
 * @creat 2017/9/30.
 * @description
 */
interface OnReadStateChangeListener {
    fun onChapterChanged(chapter: Int)

    fun onPageChanged(chapter: Int, page: Int)

    fun onLoadChapterFailure(chapter: Int)

    fun onCenterClick()

    fun onFlip()
}