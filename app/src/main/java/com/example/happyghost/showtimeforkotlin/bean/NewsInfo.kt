package com.example.happyghost.showtimeforkotlin.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

/**
 * @author Zhao Chenping
 * @creat 2017/9/5.
 * @description
 */
class NewsInfo{
        /**
         * template : normal1
         * lmodify : 2016-07-05 19:33:41
         * source : 知识就是力量杂志
         * postid : BR4U1LUM051183VR
         * title : 贵的牙膏真的比便宜的牙膏好吗？
         * mtime : 2016-07-05 19:33:41
         * hasImg : 1
         * topic_background : http://img2.cache.netease.com/m/newsapp/reading/cover1/C1348646712614.jpg
         * digest : 牙膏是和我们生活息息相关的用品之一，超市中牙膏琳琅满目、品种繁多，而且价格也不等，一支小小的牙膏从几元到几十元的都有。面对庞大的牙膏家族，消费者们往往都是凭感觉
         * boardid : dy_wemedia_bbs
         * alias : Top News
         * hasAD : 1
         * imgsrc : http://cms-bucket.nosdn.127.net/ef94479758a54cd1ac7a483b1d0cd57a20160705192433.jpeg
         * ptime : 2016-07-04 20:15:31
         * daynum : 16986
         * hasHead : 1
         * order : 1
         * votecount : 4938
         * hasCover : false
         * docid : BR4U1LUM051183VR
         * tname : 头条
         * url_3w :
         * priority : 80
         * url : null
         * ads : [{"subtitle":"","skipType":"photoset","skipID":"00AN0001|2273765","tag":"photoset","title":"2017金砖峰会晚宴现场:多国领导人出席","imgsrc":"http://cms-bucket.nosdn.127.net/970cf2c42ec1410d942e2905964a4da820170904192442.png","url":"00AN0001|2273765"},{"subtitle":"","skipType":"photoset","skipID":"00AN0001|2273804","tag":"photoset","title":"养眼！各国帅哥美女记者齐聚厦门","imgsrc":"http://cms-bucket.nosdn.127.net/fcf829e8be3e4a4ab6682ceb4bdec95f20170905093001.jpeg","url":"00AN0001|2273804"},{"subtitle":"","skipType":"photoset","skipID":"00AN0001|2273826","tag":"photoset","title":"新闻中心志愿者助力厦门金砖会晤","imgsrc":"http://cms-bucket.nosdn.127.net/c11f953ebf524000bf2498784f92804d20170905115004.jpeg","url":"00AN0001|2273826"},{"subtitle":"","skipType":"photoset","skipID":"00AN0001|2273806","tag":"photoset","title":"外国走私船伪装成中国籍:船名印反了","imgsrc":"http://cms-bucket.nosdn.127.net/e35136bad2c9438ba38fe59dbe2354da20170905095343.jpeg","url":"00AN0001|2273806"},{"subtitle":"","skipType":"photoset","skipID":"00AP0001|2273788","tag":"photoset","title":"运蜂货车侧翻 千万只蜜蜂漫天飞舞","imgsrc":"http://cms-bucket.nosdn.127.net/4a1b84f396f24445bbdec1bb7886c44f20170905092042.jpeg","url":"00AP0001|2273788"}]
         * ename : androidnews
         * replyCount : 5216
         * ltitle : 贵的牙膏真的比便宜的牙膏好吗？
         * hasIcon : false
         * subtitle :
         * cid : C1348646712614
         * imgextra : [{"imgsrc":"http://cms-bucket.nosdn.127.net/f544412774be4cce817f73831da0d95420170905120839.jpeg"},{"imgsrc":"http://cms-bucket.nosdn.127.net/38dc435802d244c6b28e578567e8645f20170905120840.jpeg"}]
         * skipID : 00AP0001|2273829
         * imgsum : 3
         * skipType : photoset
         * photosetID : 00AP0001|2273829
         */

        var template: String? = null
        var lmodify: String? = null
        var source: String? = null
        var postid: String? = null
        var title: String? = null
        var mtime: String? = null
        var hasImg: Int = 0
        var topic_background: String? = null
        var digest: String? = null
        var boardid: String? = null
        var alias: String? = null
        var hasAD: Int = 0
        var imgsrc: String? = null
        var ptime: String? = null
        var daynum: String? = null
        var hasHead: Int = 0
        var order: Int = 0
        var votecount: Int = 0
        var isHasCover: Boolean = false
        var docid: String? = null
        var tname: String? = null
        var url_3w: String? = null
        var priority: Int = 0
        var url: String? = null
        var ename: String? = null
        var replyCount: Int = 0
        var ltitle: String? = null
        var isHasIcon: Boolean = false
        var subtitle: String? = null
        var cid: String? = null
        var skipID: String? = null
        var imgsum: Int = 0
        var skipType: String? = null
        var photosetID: String? = null
        var ads: List<AdsBean>? = null
        var imgextra: List<ImgextraBean>? = null
        var specialID: String? = null

        class AdsBean private constructor() :Parcelable{
            /**
             * subtitle :
             * skipType : photoset
             * skipID : 00AN0001|2273765
             * tag : photoset
             * title : 2017金砖峰会晚宴现场:多国领导人出席
             * imgsrc : http://cms-bucket.nosdn.127.net/970cf2c42ec1410d942e2905964a4da820170904192442.png
             * url : 00AN0001|2273765
             */

            var subtitle: String? = null
            var skipType: String? = null
            var skipID: String? = null
            var tag: String? = null
            var title: String? = null
            var imgsrc: String? = null
            var url: String? = null

            constructor(parcel: Parcel) : this() {
                subtitle = parcel.readString()
                skipType = parcel.readString()
                skipID = parcel.readString()
                tag = parcel.readString()
                title = parcel.readString()
                imgsrc = parcel.readString()
                url = parcel.readString()
            }

            override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(subtitle)
                parcel.writeString(skipType)
                parcel.writeString(skipID)
                parcel.writeString(tag)
                parcel.writeString(title)
                parcel.writeString(imgsrc)
                parcel.writeString(url)
            }

            override fun describeContents(): Int {
                return 0
            }

            companion object CREATOR : Parcelable.Creator<AdsBean> {
                override fun createFromParcel(parcel: Parcel): AdsBean {
                    return AdsBean(parcel)
                }

                override fun newArray(size: Int): Array<AdsBean?> {
                    return arrayOfNulls(size)
                }
            }
        }

        class ImgextraBean {
            /**
             * imgsrc : http://cms-bucket.nosdn.127.net/f544412774be4cce817f73831da0d95420170905120839.jpeg
             */

            var imgsrc: String? = null
        }

}