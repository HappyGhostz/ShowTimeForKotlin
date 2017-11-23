package com.example.happyghost.showtimeforkotlin.bean.newsdate

/**
 * @author Zhao Chenping
 * @creat 2017/9/9.
 * @description
 */
class SpecialInfo {
    private var sid: String? = null
    private var shownav: String? = null
    private var tag: String? = null
    private var photoset: String? = null
    private var digest: String? = null
    private var webviews: Any? = null
    private var type: String? = null
    private var sname: String? = null
    private var ec: String? = null
    private var lmodify: String? = null
    private var imgsrc: String? = null
    private var del: Int = 0
    private var ptime: String? = null
    private var sdocid: String? = null
    private var banner: String? = null
    private var topicslatest: List<*>? = null
    private var topicspatch: List<*>? = null
    private var headpics: List<*>? = null
    private var topicsplus: List<*>? = null

    private var topics: List<TopicsEntity>? = null

    fun getSid(): String? {
        return sid
    }

    fun setSid(sid: String) {
        this.sid = sid
    }

    fun getShownav(): String? {
        return shownav
    }

    fun setShownav(shownav: String) {
        this.shownav = shownav
    }

    fun getTag(): String? {
        return tag
    }

    fun setTag(tag: String) {
        this.tag = tag
    }

    fun getPhotoset(): String? {
        return photoset
    }

    fun setPhotoset(photoset: String) {
        this.photoset = photoset
    }

    fun getDigest(): String? {
        return digest
    }

    fun setDigest(digest: String) {
        this.digest = digest
    }

    fun getWebviews(): Any? {
        return webviews
    }

    fun setWebviews(webviews: Any) {
        this.webviews = webviews
    }

    fun getType(): String? {
        return type
    }

    fun setType(type: String) {
        this.type = type
    }

    fun getSname(): String? {
        return sname
    }

    fun setSname(sname: String) {
        this.sname = sname
    }

    fun getEc(): String? {
        return ec
    }

    fun setEc(ec: String) {
        this.ec = ec
    }

    fun getLmodify(): String? {
        return lmodify
    }

    fun setLmodify(lmodify: String) {
        this.lmodify = lmodify
    }

    fun getImgsrc(): String? {
        return imgsrc
    }

    fun setImgsrc(imgsrc: String) {
        this.imgsrc = imgsrc
    }

    fun getDel(): Int {
        return del
    }

    fun setDel(del: Int) {
        this.del = del
    }

    fun getPtime(): String? {
        return ptime
    }

    fun setPtime(ptime: String) {
        this.ptime = ptime
    }

    fun getSdocid(): String? {
        return sdocid
    }

    fun setSdocid(sdocid: String) {
        this.sdocid = sdocid
    }

    fun getBanner(): String? {
        return banner
    }

    fun setBanner(banner: String) {
        this.banner = banner
    }

    fun getTopicslatest(): List<*>? {
        return topicslatest
    }

    fun setTopicslatest(topicslatest: List<*>) {
        this.topicslatest = topicslatest
    }

    fun getTopicspatch(): List<*>? {
        return topicspatch
    }

    fun setTopicspatch(topicspatch: List<*>) {
        this.topicspatch = topicspatch
    }

    fun getHeadpics(): List<*>? {
        return headpics
    }

    fun setHeadpics(headpics: List<*>) {
        this.headpics = headpics
    }

    fun getTopicsplus(): List<*>? {
        return topicsplus
    }

    fun setTopicsplus(topicsplus: List<*>) {
        this.topicsplus = topicsplus
    }

    fun getTopics(): List<TopicsEntity>? {
        return topics
    }

    fun setTopics(topics: List<TopicsEntity>) {
        this.topics = topics
    }

    class TopicsEntity {
        var index: Int = 0
        var tname: String? = null
        var shortname: String? = null
        var type: String? = null
        /**
         * postid : BV55MRHF00237VT1
         * votecount : 0
         * replyCount : 0
         * tag :
         * ltitle : 学习进行时：中国体育的习近平印记
         * digest :
         * url : http://3g.163.com/ntes/16/0823/09/BV55MRHF00237VT1.html
         * ipadcomment :
         * docid : BV55MRHF00237VT1
         * title : 学习进行时：中国体育的习近平印记
         * source : 新华网
         * lmodify : 2016-08-23 10:31:09
         * imgsrc : http://cms-bucket.nosdn.127.net/fc18ad4cf44d4c76a54f3325032b114320160823102514.jpeg
         * ptime : 2016-08-23 09:48:02
         */

        var docs: List<NewsItemInfo>? = null

        override fun toString(): String {
            return "TopicsEntity{" +
                    "index=" + index +
                    ", tname='" + tname + '\'' +
                    ", shortname='" + shortname + '\'' +
                    ", type='" + type + '\'' +
                    ", docs=" + docs +
                    '}'
        }
    }

    override fun toString(): String {
        return "SpecialBean{" +
                "sid='" + sid + '\'' +
                ", shownav='" + shownav + '\'' +
                ", tag='" + tag + '\'' +
                ", photoset='" + photoset + '\'' +
                ", digest='" + digest + '\'' +
                ", webviews=" + webviews +
                ", type='" + type + '\'' +
                ", sname='" + sname + '\'' +
                ", ec='" + ec + '\'' +
                ", lmodify='" + lmodify + '\'' +
                ", imgsrc='" + imgsrc + '\'' +
                ", del=" + del +
                ", ptime='" + ptime + '\'' +
                ", sdocid='" + sdocid + '\'' +
                ", banner='" + banner + '\'' +
                ", topicslatest=" + topicslatest +
                ", topicspatch=" + topicspatch +
                ", headpics=" + headpics +
                ", topicsplus=" + topicsplus +
                ", topics=" + topics +
                '}'
    }
}