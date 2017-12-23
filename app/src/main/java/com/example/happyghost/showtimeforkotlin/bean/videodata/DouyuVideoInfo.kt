package com.example.happyghost.showtimeforkotlin.bean.videodata

/**
 * @author Zhao Chenping
 * @creat 2017/12/22.
 * @description
 */
class DouyuVideoInfo {
    /**
     * error : 0
     * data : {"rtmp_cdn":"ws","room_id":"79631","room_name":"明皇直播：韩国训练第6天播到11点","rateSwitch":1,"hls_url":"http://hls3.douyucdn.cn/live/79631rwT9hvGtWgJ/playlist.m3u8?wsSecret=f20b8082924172fbd5cab393335e0402&wsTime=1489036472","live_url":"http://hdl3.douyucdn.cn/live/79631rwT9hvGtWgJ.flv?wsAuth=4484fe0cb51acf407d640e017b85d42c&token=cpn-pcclient-905619-79631-5189de0c9dc4e4e5f942a9b2a507f355&logo=0&expire=0"}
     */

    var error: Int = 0
    /**
     * rtmp_cdn : ws
     * room_id : 79631
     * room_name : 明皇直播：韩国训练第6天播到11点
     * rateSwitch : 1
     * hls_url : http://hls3.douyucdn.cn/live/79631rwT9hvGtWgJ/playlist.m3u8?wsSecret=f20b8082924172fbd5cab393335e0402&wsTime=1489036472
     * live_url : http://hdl3.douyucdn.cn/live/79631rwT9hvGtWgJ.flv?wsAuth=4484fe0cb51acf407d640e017b85d42c&token=cpn-pcclient-905619-79631-5189de0c9dc4e4e5f942a9b2a507f355&logo=0&expire=0
     */

    var data: DataEntity? = null

    class DataEntity {
        var rtmp_cdn: String? = null
        var room_id: String? = null
        var room_name: String? = null
        var rateSwitch: Int = 0
        var hls_url: String? = null
        var live_url: String? = null

    }
}