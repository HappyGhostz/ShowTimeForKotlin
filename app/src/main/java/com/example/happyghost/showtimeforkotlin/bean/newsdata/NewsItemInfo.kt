package com.example.happyghost.showtimeforkotlin.bean.newsdata

/**
 * @author Zhao Chenping
 * @creat 2017/9/9.
 * @description
 */
class NewsItemInfo {
    private var id: String? = null
    private var docID: String? = null
    private var type: String? = null
    private var href: String? = null
    private var postid: String? = null
    private var votecount: Int = 0
    private var replyCount: Int = 0
    private var tag: String? = null
    private var ltitle: String? = null
    private var digest: String? = null
    private var url: String? = null
    private var ipadcomment: String? = null
    private var docid: String? = null
    private var title: String? = null
    private var source: String? = null
    private var lmodify: String? = null
    private var imgsrc: String? = null
    private var ptime: String? = null
    private var skipType: String? = null
    private var specialID: String? = null

    fun getPhotosetID(): String? {
        return photosetID
    }

    fun setPhotosetID(photosetID: String) {
        this.photosetID = photosetID
    }

    private var photosetID: String? = null

    fun getSkipType(): String? {
        return skipType
    }

    fun setSkipType(skipType: String) {
        this.skipType = skipType
    }

    fun getSpecialID(): String? {
        return specialID
    }

    fun setSpecialID(specialID: String) {
        this.specialID = specialID
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getDocID(): String? {
        return docID
    }

    fun setDocID(docID: String) {
        this.docID = docID
    }

    fun getType(): String? {
        return type
    }

    fun setType(type: String) {
        this.type = type
    }

    fun getHref(): String? {
        return href
    }

    fun setHref(href: String) {
        this.href = href
    }

    fun getPostid(): String? {
        return postid
    }

    fun setPostid(postid: String) {
        this.postid = postid
    }

    fun getVotecount(): Int {
        return votecount
    }

    fun setVotecount(votecount: Int) {
        this.votecount = votecount
    }

    fun getReplyCount(): Int {
        return replyCount
    }

    fun setReplyCount(replyCount: Int) {
        this.replyCount = replyCount
    }

    fun getTag(): String? {
        return tag
    }

    fun setTag(tag: String) {
        this.tag = tag
    }

    fun getLtitle(): String? {
        return ltitle
    }

    fun setLtitle(ltitle: String) {
        this.ltitle = ltitle
    }

    fun getDigest(): String? {
        return digest
    }

    fun setDigest(digest: String) {
        this.digest = digest
    }

    fun getUrl(): String? {
        return url
    }

    fun setUrl(url: String) {
        this.url = url
    }

    fun getIpadcomment(): String? {
        return ipadcomment
    }

    fun setIpadcomment(ipadcomment: String) {
        this.ipadcomment = ipadcomment
    }

    fun getDocid(): String? {
        return docid
    }

    fun setDocid(docid: String) {
        this.docid = docid
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getSource(): String? {
        return source
    }

    fun setSource(source: String) {
        this.source = source
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

    fun getPtime(): String? {
        return ptime
    }

    fun setPtime(ptime: String) {
        this.ptime = ptime
    }
}