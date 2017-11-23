package com.example.happyghost.showtimeforkotlin.bean.bookdate

import com.google.gson.annotations.SerializedName

/**
 * @author Zhao Chenping
 * @creat 2017/11/20.
 * @description
 */
class Rankings {
    /**
     * _id : 54d42d92321052167dfb75e3
     * updated : 2016-08-14T21:20:21.090Z
     * title : 追书最热榜 Top100
     * tag : zhuishuLatelyFollowerMale
     * cover : /ranking-cover/142319144267827
     * __v : 509
     * monthRank : 564d820bc319238a644fb408
     * totalRank : 564d8494fe996c25652644d2
     * isSub : false
     * collapse : false
     * new : true
     * gender : male
     * priority : 250
     * books : [{"_id":"53855a750ac0b3a41e00c7e6","title":"择天记","author":"猫腻",
     * "shortIntro":"太始元年，有神石自太空飞来，分散落在人间，其中落在东土大陆的神石，上面镌刻着奇怪的图腾，人因观其图腾而悟道，后立国教。
     * 数千年后，十四岁的少年孤儿陈长生，为治病...","cover":"/agent/http://image.cmfu.com/books/3347595/3347595.jpg",
     * "site":"qidian","cat":"玄幻","banned":0,"latelyFollower":182257,"latelyFollowerBase":0,
     * "minRetentionRatio":0,"retentionRatio":"52.48"}]
     * created : 2015-02-06T02:57:22.000Z
     * id : 54d42d92321052167dfb75e3
     */

    var ranking: RankingBean? = null

    class RankingBean {
        var _id: String? = null
        var updated: String? = null
        var title: String? = null
        var tag: String? = null
        var cover: String? = null
        var __v: Int = 0
        var monthRank: String? = null
        var totalRank: String? = null
        var isSub: Boolean = false
        var collapse: Boolean = false
        @SerializedName("new")
        var newX: Boolean = false
        var gender: String? = null
        var priority: Int = 0
        var created: String? = null
        var id: String? = null
        /**
         * _id : 53855a750ac0b3a41e00c7e6
         * title : 择天记
         * author : 猫腻
         * shortIntro : 太始元年，有神石自太空飞来，分散落在人间，其中落在东土大陆的神石，上面镌刻着奇怪的图腾，人因观其图腾而悟道，后立国教。 数千年后，十四岁的少年孤儿陈长生，为治病...
         * cover : /agent/http://image.cmfu.com/books/3347595/3347595.jpg
         * site : qidian
         * cat : 玄幻
         * banned : 0
         * latelyFollower : 182257
         * latelyFollowerBase : 0
         * minRetentionRatio : 0
         * retentionRatio : 52.48
         */

        var books: List<BooksBean>? = null

        class BooksBean {
            var _id: String? = null
            var title: String? = null
            var author: String? = null
            var shortIntro: String? = null
            var cover: String? = null
            var site: String? = null
            var cat: String? = null
            var banned: Int = 0
            var latelyFollower: Int = 0
            var latelyFollowerBase: Int = 0
            var minRetentionRatio: String? = null
            var retentionRatio: String? = null
        }
    }
}