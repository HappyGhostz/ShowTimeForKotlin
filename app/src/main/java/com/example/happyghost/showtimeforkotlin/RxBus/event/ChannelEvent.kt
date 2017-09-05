package com.example.happyghost.showtimeforkotlin.RxBus.event

import com.example.happyghost.showtimeforkotlin.loacaldao.NewsTypeInfo

/**
 * @author Zhao Chenping
 * @creat 2017/9/4.
 * @description
 */
class ChannelEvent() {
    /**
     * 频道事件：添加、删除和交换位置
     */
   companion object {
       var ADD_EVENT:Int=101
        var DEL_EVENT:Int =102
        var SWAP_EVENT:Int =103
   }
    var mEventType :Int = 0

    lateinit var mNewsData : NewsTypeInfo

    constructor(eventType: Int, data: NewsTypeInfo) : this(){
       this.mEventType = eventType
        this.mNewsData = data
    }
    var mFroPos:Int=-1
    var mToPos:Int = -1
    constructor(eventType: Int,froPos:Int,toPos:Int) : this(){
        this.mEventType = eventType
        this.mFroPos = froPos
        this.mToPos = toPos
    }
}