package com.example.happyghost.showtimeforkotlin.RxBus.event

/**
 * @author Zhao Chenping
 * @creat 2017/11/23.
 * @description
 */
class LocalBookEvent {
    var isLocal = false
    constructor(boolean: Boolean){
        isLocal = boolean
    }
}