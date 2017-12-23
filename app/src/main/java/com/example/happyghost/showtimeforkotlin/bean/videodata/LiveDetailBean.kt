package com.example.happyghost.showtimeforkotlin.bean.videodata

/**
 * @author Zhao Chenping
 * @creat 2017/12/19.
 * @description
 */
class LiveDetailBean {
    /**
     * msg :
     * result : {"enable":1,"game_type":"dota2","is_followed":0,"live_id":"507882","live_img":"https://rpic.douyucdn.cn/alrpic/171221/507882_1342.jpg","live_name":"douyu","live_nickname":"820邹倚天","live_online":193272,"live_title":"820回来了！！","live_type":"douyu","live_userimg":"http://uc.douyutv.com/avatar.php?uid=12986804&size=small","stream_list":[{"type":"普清","url":"http://hls3.douyucdn.cn/live/507882rl8O828ku6_550/playlist.m3u8?wsSecret=ab8751ae5e04daf1d7a9e04ee28fef7f&wsTime=1513832631&token=h5-douyu-0-507882-29ee679f4981cfc903241ce71e9801be&did=h5_did"}]}
     * status : ok
     */

    var msg: String? = null
    var result: ResultBean? = null
    var status: String? = null

    class ResultBean {
        /**
         * enable : 1
         * game_type : dota2
         * is_followed : 0
         * live_id : 507882
         * live_img : https://rpic.douyucdn.cn/alrpic/171221/507882_1342.jpg
         * live_name : douyu
         * live_nickname : 820邹倚天
         * live_online : 193272
         * live_title : 820回来了！！
         * live_type : douyu
         * live_userimg : http://uc.douyutv.com/avatar.php?uid=12986804&size=small
         * stream_list : [{"type":"普清","url":"http://hls3.douyucdn.cn/live/507882rl8O828ku6_550/playlist.m3u8?wsSecret=ab8751ae5e04daf1d7a9e04ee28fef7f&wsTime=1513832631&token=h5-douyu-0-507882-29ee679f4981cfc903241ce71e9801be&did=h5_did"}]
         */

        var enable: Int = 0
        var game_type: String? = null
        var is_followed: Int = 0
        var live_id: String? = null
        var live_img: String? = null
        var live_name: String? = null
        var live_nickname: String? = null
        var live_online: Int = 0
        var live_title: String? = null
        var live_type: String? = null
        var live_userimg: String? = null
        var stream_list: List<StreamListBean>? = null

        class StreamListBean {
            /**
             * type : 普清
             * url : http://hls3.douyucdn.cn/live/507882rl8O828ku6_550/playlist.m3u8?wsSecret=ab8751ae5e04daf1d7a9e04ee28fef7f&wsTime=1513832631&token=h5-douyu-0-507882-29ee679f4981cfc903241ce71e9801be&did=h5_did
             */

            var type: String? = null
            var url: String? = null
        }
    }
}