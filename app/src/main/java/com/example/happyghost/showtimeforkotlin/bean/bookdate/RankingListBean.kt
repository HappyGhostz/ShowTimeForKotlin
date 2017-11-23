package com.example.happyghost.showtimeforkotlin.bean.bookdate

/**
 * @author Zhao Chenping
 * @creat 2017/9/25.
 * @description
 */
class RankingListBean  {


    /**
     * female : [{"_id":"54d43437d47d13ff21cad58b","title":"追书最热榜 Top100",
     * "cover":"/ranking-cover/142319314350435","collapse":false,
     * "monthRank":"564d853484665f97662d0810","totalRank":"564d85b6dd2bd1ec660ea8e2"}]
     * ok : true
     */

    var female: List<MaleBean>? = null
    /**
     * _id : 54d42d92321052167dfb75e3
     * title : 追书最热榜 Top100
     * cover : /ranking-cover/142319144267827
     * collapse : false
     * monthRank : 564d820bc319238a644fb408
     * totalRank : 564d8494fe996c25652644d2
     */

    var male: List<MaleBean>? = null

    class MaleBean {
        var _id: String? = null
        var title: String? = null
        var cover: String? = null
        var collapse: Boolean = false
        var monthRank: String? = null
        var totalRank: String? = null

        constructor() {}

        constructor(title: String) {
            this.title = title
        }
    }
}