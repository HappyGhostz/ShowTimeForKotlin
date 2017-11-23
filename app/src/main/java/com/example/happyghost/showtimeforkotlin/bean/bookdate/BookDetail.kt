package com.example.happyghost.showtimeforkotlin.bean.bookdate

/**
 * Created by e445 on 2017/11/15.
 */
class BookDetail {
    /**
     * _id : 525253d094336b3155000dd8
     * author : w风雪
     * banned : 0
     * cover : /agent/http://image.cmfu.com/books/2797907/2797907.jpg
     * creater : iPhone 5 (GSM+CDMA)
     * dramaPoint : null
     * followerCount : 35
     * gradeCount : 0
     * isSerial : true
     * lastChapter : 请安装【追书神器】，本应用已停用
     * latelyFollower : 2385
     * longIntro : 您当前所使用的软件已改名为【追书神器】。
     * 请搜索“追书神器”下载安装最新版【追书神器】。
     * 无广告；不闪退；章节更新自动通知。
     * postCount : 111
     * serializeWordCount : 4614
     * site : zhuishuvip
     * tags : ["热血","洪荒封神","洪荒","架空","修炼","仙侠"]
     * title : 洪荒造化
     * tocs : ["525253d194336b3155000dd9","525253e6a4e35219120006a6","525253e6a4e35219120006a7","525253e6a4e35219120006a8","525253e6a4e35219120006a9","525253e6a4e35219120006ab","52c65225c1988af227000251","52c68d2cf91d99312b000d92","52c690bc0f3d8bda2b000873","532cf9dd39493253790020f4"]
     * totalPoint : null
     * type : wxxx
     * updated : 2016-04-03T13:48:05.907Z
     * writingPoint : null
     * hasNotice : false
     * tagStuck : 0
     * chaptersCount : 1294
     * tocCount : 15
     * tocUpdated : 2016-03-25T21:03:42.962Z
     * retentionRatio : 20.56
     * hasCmread : true
     * thirdFlagsUpdated : 2014-09-01T06:01:24.259Z
     * categories : ["洪荒封神","仙侠"]
     * wordCount : 5947980
     * cat : 仙侠
     * gender : ["male"]
     * majorCate : 仙侠
     * minorCate : 洪荒封神
     * reviewCount : 9
     * monthFollower : {"11":2170}
     * totalFollower : 2170
     * cpOnly : false
     * hasCp : true
     * _le : false
     */

    var _id: String? = null
    var author: String? = null
    var banned: Int = 0
    var cover: String? = null
    var creater: String? = null
    var dramaPoint: Any? = null
    var followerCount: Int = 0
    var gradeCount: Int = 0
    var isSerial: Boolean = false
    var lastChapter: String? = null
    var latelyFollower: Int = 0
    var longIntro: String? = null
    var postCount: Int = 0 // 社区帖子数
    var serializeWordCount: Int = 0
    var site: String? = null
    var title: String? = null
    var totalPoint: Any? = null
    var type: String? = null
    var updated: String? = null
    var writingPoint: Any? = null
    var hasNotice: Boolean = false
    var tagStuck: Int = 0
    var chaptersCount: Int = 0
    var tocCount: Int = 0
    var tocUpdated: String? = null
    var retentionRatio: String? = null
    var hasCmread: Boolean = false
    var thirdFlagsUpdated: String? = null
    var wordCount: Int = 0
    var cat: String? = null
    var majorCate: String? = null
    var minorCate: String? = null
    var reviewCount: Int = 0
    var totalFollower: Int = 0
    var cpOnly: Boolean = false
    var hasCp: Boolean = false
    var _le: Boolean = false
    var tags: List<String>? = null
    var tocs: List<String>? = null
    var categories: List<String>? = null
    var gender: Any? = null // MLGB, 偶尔是String，偶尔是Array
}