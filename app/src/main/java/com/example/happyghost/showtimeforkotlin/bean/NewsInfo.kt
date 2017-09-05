package com.example.happyghost.showtimeforkotlin.bean

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

/**
 * @author Zhao Chenping
 * @creat 2017/9/5.
 * @description
 */
class NewsInfo(){
//    private var postid: String? = null
//    private var hasHead: Int = 0
//    private var title: String? = null
//    private var photosetID: String? = null
//    private var skipType: String? = null
//    private var source: String? = null
//    private var imgsrc: String? = null
//
//    private var ptime: String? = null
//    private var specialID: String? = null
//    private var ads: ArrayList<AdData>? = null
//    private var imgextra: ArrayList<ImgExtraData>? = null
//    constructor(id:String,head:Int,title:String,phoId:String,sType:String,sour:String,img:String,time:String,
//                sId)
//    data class AdData{
//
//    }
    /**
     * postid : PHOT22SMT000100A
     * hasCover : false
     * hasHead : 1
     * replyCount : 3406
     * hasImg : 1
     * digest :
     * hasIcon : false
     * docid : 9IG74V5H00963VRO_BV3ADP8MbjjikeupdateDoc
     * title : "长征五号"运抵天津码头 "出征"首飞
     * order : 1
     * priority : 340
     * lmodify : 2016-08-22 17:22:35
     * boardid : photoview_bbs
     * ads : [{"title":"南京村庄建\u201c楼叠楼\u201d 为多拿拆迁补偿","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/4693747579184988ad6e903e3e07793620160822095553.jpeg","subtitle":"","url":"00AP0001|2192041"},{"title":"安倍晋三扮马里奥亮相奥运会闭幕式","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/14c874ae5e8c41818a1d754de935830920160822094144.jpeg","subtitle":"","url":"6TSU0005|147568"},{"title":"法国华人团体游行呼吁保障华人安全","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/9c221db80bc54e068b84f6833701df0d20160822075828.jpeg","subtitle":"","url":"00AO0001|2192019"},{"title":"济南家长自戴头灯 领小孩黑虎泉边捕鱼","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/1a12be5fb7f3495ab7626a99dbd75ace20160822101953.jpeg","subtitle":"","url":"00AP0001|2192051"},{"title":"长春现一平米\u201c弹丸\u201d车位 仅停摩托车","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/c121e479d0ca46fd937a09d2a275a96b20160822101343.jpeg","subtitle":"","url":"00AP0001|2192050"}]
     * photosetID : 00AN0001|2192093
     * template : normal1
     * votecount : 3067
     * skipID : 00AN0001|2192093
     * alias : Top News
     * skipType : photoset
     * cid : C1348646712614
     * hasAD : 1
     * imgextra : [{"imgsrc":"http://cms-bucket.nosdn.127.net/381ed60794ff4b0785b9888453f514a420160822163245.jpeg"},{"imgsrc":"http://cms-bucket.nosdn.127.net/0155de5da75449dfbcc2e2c54a45fdfa20160822163246.jpeg"}]
     * source : 网易原创
     * ename : androidnews
     * imgsrc : http://cms-bucket.nosdn.127.net/3bbed1d93b2e47888938b5d3067e561320160822172233.jpeg
     * tname : 头条
     * ptime : 2016-08-22 16:31:59
     */

    private var postid: String? = null
    private var hasCover: Boolean = false
    private var hasHead: Int = 0
    private var replyCount: Int = 0
    private var hasImg: Int = 0
    private var digest: String? = null
    private var hasIcon: Boolean = false
    private var docid: String? = null
    private var title: String? = null
    private var order: Int = 0
    private var priority: Int = 0
    private var lmodify: String? = null
    private var boardid: String? = null
    private var photosetID: String? = null
    private var template: String? = null
    private var votecount: Int = 0
    private var skipID: String? = null
    private var alias: String? = null
    private var skipType: String? = null
    private var cid: String? = null
    private var hasAD: Int = 0
    private var source: String? = null
    private var ename: String? = null
    private var imgsrc: String? = null
    private var tname: String? = null
    private var ptime: String? = null
    private var specialID: String? = null

    /**
     * title : 南京村庄建“楼叠楼” 为多拿拆迁补偿
     * tag : photoset
     * imgsrc : http://cms-bucket.nosdn.127.net/4693747579184988ad6e903e3e07793620160822095553.jpeg
     * subtitle :
     * url : 00AP0001|2192041
     */

    private var ads: List<AdData>? = null
    /**
     * imgsrc : http://cms-bucket.nosdn.127.net/381ed60794ff4b0785b9888453f514a420160822163245.jpeg
     */

    private var imgextra: List<ImgExtraData>? = null

    fun getPostid(): String? {
        return postid
    }

    fun setPostid(postid: String) {
        this.postid = postid
    }

    fun isHasCover(): Boolean {
        return hasCover
    }

    fun setHasCover(hasCover: Boolean) {
        this.hasCover = hasCover
    }

    fun getHasHead(): Int {
        return hasHead
    }

    fun setHasHead(hasHead: Int) {
        this.hasHead = hasHead
    }

    fun getReplyCount(): Int {
        return replyCount
    }

    fun setReplyCount(replyCount: Int) {
        this.replyCount = replyCount
    }

    fun getHasImg(): Int {
        return hasImg
    }

    fun setHasImg(hasImg: Int) {
        this.hasImg = hasImg
    }

    fun getDigest(): String? {
        return digest
    }

    fun setDigest(digest: String) {
        this.digest = digest
    }

    fun isHasIcon(): Boolean {
        return hasIcon
    }

    fun setHasIcon(hasIcon: Boolean) {
        this.hasIcon = hasIcon
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

    fun getOrder(): Int {
        return order
    }

    fun setOrder(order: Int) {
        this.order = order
    }

    fun getPriority(): Int {
        return priority
    }

    fun setPriority(priority: Int) {
        this.priority = priority
    }

    fun getLmodify(): String? {
        return lmodify
    }

    fun setLmodify(lmodify: String) {
        this.lmodify = lmodify
    }

    fun getBoardid(): String? {
        return boardid
    }

    fun setBoardid(boardid: String) {
        this.boardid = boardid
    }

    fun getPhotosetID(): String? {
        return photosetID
    }

    fun setPhotosetID(photosetID: String) {
        this.photosetID = photosetID
    }

    fun getTemplate(): String? {
        return template
    }

    fun setTemplate(template: String) {
        this.template = template
    }

    fun getVotecount(): Int {
        return votecount
    }

    fun setVotecount(votecount: Int) {
        this.votecount = votecount
    }

    fun getSkipID(): String? {
        return skipID
    }

    fun setSkipID(skipID: String) {
        this.skipID = skipID
    }

    fun getAlias(): String? {
        return alias
    }

    fun setAlias(alias: String) {
        this.alias = alias
    }

    fun getSkipType(): String? {
        return skipType
    }

    fun setSkipType(skipType: String) {
        this.skipType = skipType
    }

    fun getCid(): String? {
        return cid
    }

    fun setCid(cid: String) {
        this.cid = cid
    }

    fun getHasAD(): Int {
        return hasAD
    }

    fun setHasAD(hasAD: Int) {
        this.hasAD = hasAD
    }

    fun getSource(): String? {
        return source
    }

    fun setSource(source: String) {
        this.source = source
    }

    fun getEname(): String? {
        return ename
    }

    fun setEname(ename: String) {
        this.ename = ename
    }

    fun getImgsrc(): String? {
        return imgsrc
    }

    fun setImgsrc(imgsrc: String) {
        this.imgsrc = imgsrc
    }

    fun getTname(): String? {
        return tname
    }

    fun setTname(tname: String) {
        this.tname = tname
    }

    fun getPtime(): String? {
        return ptime
    }

    fun setPtime(ptime: String) {
        this.ptime = ptime
    }

    fun getAds(): List<AdData>? {
        return ads
    }

    fun setAds(ads: List<AdData>) {
        this.ads = ads
    }

    fun getImgextra(): List<ImgExtraData>? {
        return imgextra
    }

    fun setImgextra(imgextra: List<ImgExtraData>) {
        this.imgextra = imgextra
    }

    fun getSpecialID(): String? {
        return specialID
    }

    fun setSpecialID(specialID: String) {
        this.specialID = specialID
    }

    class AdData()  {
        var title: String? = null
        var tag: String? = null
        var imgsrc: String? = null
        var subtitle: String? = null
        var url: String? = null
        fun getAdDataTitle(): String? {
            return title
        }
        fun getAdDataTag(): String? {
            return tag
        }
        fun getAdDataImgsrc(): String? {
            return imgsrc
        }fun getAdDataSubtitle(): String? {
            return subtitle
        }
        fun getAdDataUrl(): String? {
            return url
        }



    }

    class ImgExtraData {
        var imgsrc: String? = null
    }

    override fun toString(): String {
        return "NewsBean{" +
                "postid='" + postid + '\'' +
                ", hasCover=" + hasCover +
                ", hasHead=" + hasHead +
                ", replyCount=" + replyCount +
                ", hasImg=" + hasImg +
                ", digest='" + digest + '\'' +
                ", hasIcon=" + hasIcon +
                ", docid='" + docid + '\'' +
                ", title='" + title + '\'' +
                ", order=" + order +
                ", priority=" + priority +
                ", lmodify='" + lmodify + '\'' +
                ", boardid='" + boardid + '\'' +
                ", photosetID='" + photosetID + '\'' +
                ", template='" + template + '\'' +
                ", votecount=" + votecount +
                ", skipID='" + skipID + '\'' +
                ", alias='" + alias + '\'' +
                ", skipType='" + skipType + '\'' +
                ", cid='" + cid + '\'' +
                ", hasAD=" + hasAD +
                ", source='" + source + '\'' +
                ", ename='" + ename + '\'' +
                ", imgsrc='" + imgsrc + '\'' +
                ", tname='" + tname + '\'' +
                ", ptime='" + ptime + '\'' +
                ", specialID='" + specialID + '\'' +
                ", ads=" + ads +
                ", imgextra=" + imgextra +
                '}'
    }





}