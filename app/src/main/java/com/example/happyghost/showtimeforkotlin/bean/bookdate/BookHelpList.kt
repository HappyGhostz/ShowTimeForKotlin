package com.example.happyghost.showtimeforkotlin.bean.bookdate

/**
 * @author Zhao Chenping
 * @creat 2017/9/15.
 * @description
 */
class BookHelpList {
    /**
     * _id : 57c63a9e641e6d0b762e3ac1
     * title : 【追书读书会】第一期·那些该死的快穿文
     * author : {"_id":"56e903c1febd4661455a0692",
     * "avatar":"/avatar/7b/e1/7be142f47d8ef8834727b1dd2c7bbbc1","nickname":"追书家的眼镜娘",
     * "type":"official","lv":8,"gender":"female"}
     * likeCount : 17
     * state : hot
     * updated : 2016-09-01T13:57:22.643Z
     * created : 2016-08-31T02:02:06.227Z
     * commentCount : 183
     */

    var helps: List<HelpsBean>? = null

    class HelpsBean {
        var _id: String? = null
        var title: String? = null
        /**
         * _id : 56e903c1febd4661455a0692
         * avatar : /avatar/7b/e1/7be142f47d8ef8834727b1dd2c7bbbc1
         * nickname : 追书家的眼镜娘
         * type : official
         * lv : 8
         * gender : female
         */

        var author: AuthorBean? = null
        var likeCount: Int = 0
        var state: String? = null
        var updated: String? = null
        var created: String? = null
        var commentCount: Int = 0

        class AuthorBean {
            var _id: String? = null
            var avatar: String? = null
            var nickname: String? = null
            var type: String? = null
            var lv: Int = 0
            var gender: String? = null
        }
    }
}