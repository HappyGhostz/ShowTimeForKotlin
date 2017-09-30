package com.example.happyghost.showtimeforkotlin.wegit.read

/**
 * @author Zhao Chenping
 * @creat 2017/9/30.
 * @description
 */
enum class BookStatus {
    NO_PRE_PAGE,
    NO_NEXT_PAGE,

    PRE_CHAPTER_LOAD_FAILURE,
    NEXT_CHAPTER_LOAD_FAILURE,

    LOAD_SUCCESS
}