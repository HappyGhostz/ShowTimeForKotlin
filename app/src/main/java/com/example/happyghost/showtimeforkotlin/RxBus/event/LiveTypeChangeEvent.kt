package com.example.happyghost.showtimeforkotlin.RxBus.event

/**
 * @author Zhao Chenping
 * @creat 2017/12/22.
 * @description
 */
class LiveTypeChangeEvent {
    lateinit var liveType:String
    constructor(type: String){
        liveType = type
    }
}