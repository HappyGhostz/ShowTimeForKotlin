package com.example.happyghost.showtimeforkotlin.bean.musicdate

/**
 * @author Zhao Chenping
 * @creat 2017/12/5.
 * @description
 */
class MusicSearchList {
    /**
     * query : 爱我还是他
     * is_artist : 0
     * is_album : 0
     * rs_words :
     * pages : {"total":"2","rn_num":"2"}
     * song_list : [{"title":"爱我还是他","song_id":"31197196","author":"洪卓立","artist_id":"913","all_artist_id":"913","album_title":"Neway Music Live X 洪卓立音乐会","appendix":"现场","album_id":"31199665","lrclink":"/data2/lrc/65425384/65425384.lrc","resource_type":"0","content":"","relate_status":0,"havehigh":2,"copy_type":"1","del_status":"0","all_rate":"24,64,128,192,256,320,flac","has_mv":0,"has_mv_mobile":0,"mv_provider":"0000000000","charge":0,"toneid":"0","info":"","data_source":0,"learn":0},{"title":"爱我还是他","song_id":"2109208","author":"群星","artist_id":"313607","all_artist_id":"313607","album_title":"LOVE 国语情歌集","appendix":"","album_id":"7311955","lrclink":"/data2/lrc/13910062/13910062.lrc","resource_type":"0","content":"","relate_status":1,"havehigh":2,"copy_type":"0","del_status":"0","all_rate":"64,128,256,320","has_mv":0,"has_mv_mobile":1,"mv_provider":"1100000000","charge":0,"toneid":"0","info":"","data_source":0,"learn":1},{"title":"空气稀薄","song_id":"13903108","author":"杨杰荃","artist_id":"13903099","all_artist_id":"13903099","album_title":"新新人类","appendix":"","album_id":"13903112","lrclink":"/data2/lrc/241835653/241835653.lrc","resource_type":"0","content":"遇到分岔\n是爱我还是他\n不要再用双眼泛起的泪光\n当作是你的回答\n\n空气稀薄吗\n怎么连见面都变得尴尬\n满腮的胡渣\n是我太过潇洒\n还是太邋遢OHYEYE\n\n紧随的步伐\n经不起风沙\n在中途就","relate_status":0,"havehigh":2,"copy_type":"0","del_status":"0","all_rate":"128","has_mv":0,"has_mv_mobile":0,"mv_provider":"0000000000","charge":0,"toneid":"","info":"","data_source":0,"learn":0},{"title":"累了就散了","song_id":"116791887","author":"王惠杰","artist_id":"116789723","all_artist_id":"116789723","album_title":"","appendix":"","album_id":"0","lrclink":"/data2/lrc/116791806/116791806.txt","resource_type":"0","content":"谎话\n你到底爱我还是他\n你爱我还是他\n啊可笑可笑可笑吧\n我值得值得我值得吗\n我是对了还是错了吗\n谁能给我个回答\n谁能给我个回答\n给我个回答\n啊散了散了散了吧\n我忘了忘了我忘","relate_status":1,"havehigh":2,"copy_type":"1","del_status":"0","all_rate":"128","has_mv":0,"has_mv_mobile":0,"mv_provider":"0000000000","charge":0,"toneid":"","info":"","data_source":0,"learn":0},{"title":"我我我他他他","song_id":"32996797","author":"王绎龙","artist_id":"1810","all_artist_id":"1810","album_title":"DJ万万岁","appendix":"混音","album_id":"32996738","lrclink":"/data2/lrc/65430904/65430904.lrc","resource_type":"0","content":"诉我你到底爱我还是他\n为何心里明明有他还要对我说那些情话\n现在请你不要犹豫说你心里真实的回答\n离开的人是我还是他Please Baby\n你爱的人是我我我我吗\n别再说爱着他他他的话\n你","relate_status":0,"havehigh":2,"copy_type":"0","del_status":"0","all_rate":"128","has_mv":0,"has_mv_mobile":0,"mv_provider":"0000000000","charge":0,"toneid":"","info":"","data_source":0,"learn":0}]
     */

    var query: String? = null
    var is_artist: Int = 0
    var is_album: Int = 0
    var rs_words: String? = null
    var pages: PagesBean? = null
    var song_list: List<SongListBean>? = null

    class PagesBean {
        /**
         * total : 2
         * rn_num : 2
         */

        var total: String? = null
        var rn_num: String? = null
    }

    class SongListBean {
        /**
         * title : 爱我还是他
         * song_id : 31197196
         * author : 洪卓立
         * artist_id : 913
         * all_artist_id : 913
         * album_title : Neway Music Live X 洪卓立音乐会
         * appendix : 现场
         * album_id : 31199665
         * lrclink : /data2/lrc/65425384/65425384.lrc
         * resource_type : 0
         * content :
         * relate_status : 0
         * havehigh : 2
         * copy_type : 1
         * del_status : 0
         * all_rate : 24,64,128,192,256,320,flac
         * has_mv : 0
         * has_mv_mobile : 0
         * mv_provider : 0000000000
         * charge : 0
         * toneid : 0
         * info :
         * data_source : 0
         * learn : 0
         */

        var title: String? = null
        var song_id: String? = null
        var author: String? = null
        var artist_id: String? = null
        var all_artist_id: String? = null
        var album_title: String? = null
        var appendix: String? = null
        var album_id: String? = null
        var lrclink: String? = null
        var resource_type: String? = null
        var content: String? = null
        var relate_status: Int = 0
        var havehigh: Int = 0
        var copy_type: String? = null
        var del_status: String? = null
        var all_rate: String? = null
        var has_mv: Int = 0
        var has_mv_mobile: Int = 0
        var mv_provider: String? = null
        var charge: Int = 0
        var toneid: String? = null
        var info: String? = null
        var data_source: Int = 0
        var learn: Int = 0
    }
}