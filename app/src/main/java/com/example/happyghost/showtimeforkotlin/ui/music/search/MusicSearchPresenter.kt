package com.example.happyghost.showtimeforkotlin.ui.music.search

import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService

/**
 * @author Zhao Chenping
 * @creat 2017/12/5.
 * @description
 */
class MusicSearchPresenter(view: MusicSearchReaultActivity) :IBasePresenter {
    var mView = view
    override fun getData() {

    }

    override fun getMoreData() {

    }
    fun getSearchResultList(query: String) {
        RetrofitService.getSearchMusicList(ConsTantUtils.MUSIC_URL_METHOD_SONG_SEARCH,query,50,1,ConsTantUtils.MUSIC_URL_FORMAT)
                .compose(mView.bindToLife())
                .subscribe {
                    val song_list = it.song_list
                    if(song_list!=null){
                        mView.loadSearchMusicResult(song_list!!)
                    }
                }
    }
    fun getMusic(song_id: String?) {
        RetrofitService.getSongDetail(ConsTantUtils.MUSIC_URL_FROM_2, ConsTantUtils.MUSIC_URL_VERSION,
                ConsTantUtils.MUSIC_URL_FORMAT, ConsTantUtils.MUSIC_URL_METHOD_SONG_DETAIL
                , song_id!!)
                .compose(mView.bindToLife())
                .subscribe {
                    mView.loadSearchMusicInfo(it)
                }
    }
}