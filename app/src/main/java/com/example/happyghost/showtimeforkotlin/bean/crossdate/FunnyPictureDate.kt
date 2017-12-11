package com.example.happyghost.showtimeforkotlin.bean.crossdate

/**
 * @author Zhao Chenping
 * @creat 2017/12/9.
 * @description
 */
class FunnyPictureDate {

    var message: String? = null
    var data: DataBeanX? = null
    class DataBeanX {
        var isHas_more: Boolean = false
        var tip: String? = null
        var isHas_new_message: Boolean = false
        var max_time: Double = 0.toDouble()
        var min_time: Int = 0
        var data: List<DataBean>? = null

        class DataBean {
            var group: GroupBean? = null
            var type: Int = 0
            var comments: List<*>? = null

            class GroupBean {
                /**
                 * user : {"is_living":false,"user_id":5219445046,"name":"麻辣t","followings":0,"user_verified":false,"ugc_count":23,"avatar_url":"http://wx.qlogo.cn/mmopen/gTiaWLzArw21GtHN1Tmnmv9iaiakhxHwnoxFrz3QZtnribUsCG1qS1pC0ibxAar5R3GHyletVLOodzxGk1wbCiaWxljSTibkFmHtgibD/0","followers":9,"is_following":false,"is_pro_user":false,"medals":[{"count":3,"icon_url":"http://p1.pstatp.com/obj/3b630009fc5cacb7c73e","name":"hot_content","small_icon_url":"http://p1.pstatp.com/obj/3b680001625ef138a4a3"}]}
                 * text : 速度与激情7的发型师估计是最轻松的[捂脸]………眉毛病
                 * neihan_hot_start_time : 00-00-00
                 * dislike_reason : [{"type":2,"id":2,"title":"吧:搞笑囧图"},{"type":4,"id":0,"title":"内容重复"},{"type":3,"id":5219445046,"title":"作者:麻辣t"}]
                 * create_time : 1512609995
                 * id : 79246636826
                 * favorite_count : 12
                 * go_detail_count : 57551
                 * category_show_ranking : 0
                 * category_activity_schema_url :
                 * user_favorite : 0
                 * share_type : 1
                 * category_activity_text : 测试测试测试测试
                 * max_screen_width_percent : 0.6
                 * is_can_share : 1
                 * category_type : 1
                 * share_url : http://m.neihanshequ.com/share/group/79246636826/?iid=3216590132&app=joke_essay
                 * label : 1
                 * content : 速度与激情7的发型师估计是最轻松的[捂脸]………眉毛病
                 * comment_count : 56
                 * id_str : 79246636826
                 * media_type : 1
                 * share_count : 2
                 * type : 3
                 * status : 112
                 * has_comments : 0
                 * large_image : {"width":1080,
                 * "r_height":1280,"r_width":720,"url_list":[{"url":"http://p1.pstatp.com/w720/4d7a0004f101329bfef2.webp"},{"url":"http://pb3.pstatp.com/w720/4d7a0004f101329bfef2.webp"},{"url":"http://pb9.pstatp.com/w720/4d7a0004f101329bfef2.webp"}],"uri":"w720/4d7a0004f101329bfef2","height":1920}
                 * user_bury : 0
                 * activity : {}
                 * status_desc : 热门发帖
                 * quick_comment : false
                 * category_activity_end_time : 1506700800
                 * display_type : 0
                 * neihan_hot_end_time : 00-00-00
                 * user_digg : 0
                 * online_time : 1512609995
                 * category_name : 搞笑囧图
                 * category_visible : true
                 * bury_count : 39
                 * is_anonymous : false
                 * repin_count : 12
                 * min_screen_width_percent : 0.167
                 * is_neihan_hot : false
                 * digg_count : 813
                 * has_hot_comments : 0
                 * allow_dislike : true
                 * category_activity_start_time : 1504195200
                 * image_status : 1
                 * category_is_activity : 0
                 * user_repin : 0
                 * neihan_hot_link : {}
                 * group_id : 79246636826
                 * middle_image : {"width":1080,"r_height":853,"r_width":480,"url_list":[{"url":"http://p1.pstatp.com/w480/4d7a0004f101329bfef2.webp"},{"url":"http://pb3.pstatp.com/w480/4d7a0004f101329bfef2.webp"},{"url":"http://pb9.pstatp.com/w480/4d7a0004f101329bfef2.webp"}],"uri":"w480/4d7a0004f101329bfef2","height":1920}
                 * category_id : 2
                 */

                var user: UserBean? = null
                var text: String? = null
                var neihan_hot_start_time: String? = null
                var create_time: Int = 0
                var id: Long = 0
                var favorite_count: Int = 0
                var go_detail_count: Int = 0
                var category_show_ranking: Int = 0
                var category_activity_schema_url: String? = null
                var user_favorite: Int = 0
                var share_type: Int = 0
                var category_activity_text: String? = null
                var max_screen_width_percent: Double = 0.toDouble()
                var is_can_share: Int = 0
                var category_type: Int = 0
                var share_url: String? = null
                var label: Int = 0
                var content: String? = null
                var comment_count: Int = 0
                var id_str: String? = null
                var media_type: Int = 0
                var share_count: Int = 0
                var type: Int = 0
                var status: Int = 0
                var has_comments: Int = 0
                var large_image: LargeImageBean? = null
                var user_bury: Int = 0
                var activity: ActivityBean? = null
                var status_desc: String? = null
                var isQuick_comment: Boolean = false
                var category_activity_end_time: Int = 0
                var display_type: Int = 0
                var neihan_hot_end_time: String? = null
                var user_digg: Int = 0
                var online_time: Int = 0
                var category_name: String? = null
                var isCategory_visible: Boolean = false
                var bury_count: Int = 0
                var isIs_anonymous: Boolean = false
                var repin_count: Int = 0
                var min_screen_width_percent: Double = 0.toDouble()
                var isIs_neihan_hot: Boolean = false
                var digg_count: Int = 0
                var has_hot_comments: Int = 0
                var isAllow_dislike: Boolean = false
                var category_activity_start_time: Int = 0
                var image_status: Int = 0
                var category_is_activity: Int = 0
                var user_repin: Int = 0
                var neihan_hot_link: NeihanHotLinkBean? = null
                var group_id: Long = 0
                var category_id: Int = 0

                class UserBean {
                    /**
                     * is_living : false
                     * user_id : 5219445046
                     * name : 麻辣t
                     * followings : 0
                     * user_verified : false
                     * ugc_count : 23
                     * avatar_url : http://wx.qlogo.cn/mmopen/gTiaWLzArw21GtHN1Tmnmv9iaiakhxHwnoxFrz3QZtnribUsCG1qS1pC0ibxAar5R3GHyletVLOodzxGk1wbCiaWxljSTibkFmHtgibD/0
                     * followers : 9
                     * is_following : false
                     * is_pro_user : false
                     * medals : [{"count":3,"icon_url":"http://p1.pstatp.com/obj/3b630009fc5cacb7c73e","name":"hot_content","small_icon_url":"http://p1.pstatp.com/obj/3b680001625ef138a4a3"}]
                     */

                    var isIs_living: Boolean = false
                    var user_id: Long = 0
                    var name: String? = null
                    var followings: Int = 0
                    var isUser_verified: Boolean = false
                    var ugc_count: Int = 0
                    var avatar_url: String? = null
                    var followers: Int = 0
                    var isIs_following: Boolean = false
                    var isIs_pro_user: Boolean = false
                    var medals: List<MedalsBean>? = null

                    class MedalsBean {
                        /**
                         * count : 3
                         * icon_url : http://p1.pstatp.com/obj/3b630009fc5cacb7c73e
                         * name : hot_content
                         * small_icon_url : http://p1.pstatp.com/obj/3b680001625ef138a4a3
                         */

                        var count: Int = 0
                        var icon_url: String? = null
                        var name: String? = null
                        var small_icon_url: String? = null
                    }
                }
                class ActivityBean

                class NeihanHotLinkBean

                class LargeImageBean {
                    /**
                     * width : 1080
                     * r_height : 853
                     * r_width : 480
                     * url_list : [{"url":"http://p1.pstatp.com/w480/4d7a0004f101329bfef2.webp"},
                     * {"url":"http://pb3.pstatp.com/w480/4d7a0004f101329bfef2.webp"},
                     * {"url":"http://pb9.pstatp.com/w480/4d7a0004f101329bfef2.webp"}]
                     * uri : w480/4d7a0004f101329bfef2
                     * height : 1920
                     */

                    var width: Int = 0
                    var r_height: Int = 0
                    var r_width: Int = 0
                    var uri: String? = null
                    var height: Int = 0
                    var url_list: List<UrlListBeanX>? = null

                    class UrlListBeanX {
                        /**
                         * url : http://p1.pstatp.com/w480/4d7a0004f101329bfef2.webp
                         */

                        var url: String? = null
                    }
                }
            }
        }
    }
}