package com.example.happyghost.showtimeforkotlin.ui.music.listdetail

import android.view.View
import com.example.happyghost.showtimeforkotlin.bean.musicdate.RankingListDetail
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongListDetail
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.RetrofitService
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_music_list_detail.*
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

/**
 * @author Zhao Chenping
 * @creat 2017/11/27.
 * @description
 */
class MusicListDetialPresenter(view: MusicListDetialActivity, listid: String?):IBasePresenter {
    var mView =view
    var mListId =listid
    override fun getData() {
    }

    override fun getMoreData() {

    }
    fun getMusicListInfo(){
        if(mListId!=null){
            RetrofitService.getMusicListDetialAll(ConsTantUtils.MUSIC_URL_FORMAT, ConsTantUtils.MUSIC_URL_FROM,
                    ConsTantUtils.MUSIC_URL_METHOD_SONGLIST_DETAIL, mListId!!)
                    .doOnSubscribe { mView.bottom_container.visibility=View.VISIBLE }
                    .compose(mView.bindToLife())
                    .subscribe(object :Observer<SongListDetail>{
                        override fun onNext(t: SongListDetail) {
                            mView.loadMusicListDetial(t)
                        }

                        override fun onComplete() {
                            mView.bottom_container.visibility=View.GONE
                        }

                        override fun onError(e: Throwable) {

                        }

                        override fun onSubscribe(d: Disposable) {

                        }
                    })
        }

    }
    fun getRankMusicInfo(){
        val mFields = encode("song_id,title,author,album_title,pic_big,pic_small,havehigh,all_rate," +
                "charge,has_mv_mobile,learn,song_source,korean_bb_song")
        RetrofitService.getRankPlayList(ConsTantUtils.MUSIC_URL_FORMAT, ConsTantUtils.MUSIC_URL_FROM,
                ConsTantUtils.MUSIC_URL_METHOD_RANKING_DETAIL, mListId!!.toInt(), 0, 100, mFields)
                .doOnSubscribe { mView.bottom_container.visibility=View.VISIBLE }
                .compose(mView.bindToLife())
                .subscribe(object :Observer<RankingListDetail>{
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {
                        mView.bottom_container.visibility=View.GONE
                    }

                    override fun onNext(t: RankingListDetail) {
                        mView.loadRankPlayList(t)
                    }
                })

    }
    fun getMusic(song_id: String?) {
        RetrofitService.getSongDetail(ConsTantUtils.MUSIC_URL_FROM_2, ConsTantUtils.MUSIC_URL_VERSION,
                ConsTantUtils.MUSIC_URL_FORMAT, ConsTantUtils.MUSIC_URL_METHOD_SONG_DETAIL
                , song_id!!)
                .compose(mView.bindToLife())
                .subscribe {
                    mView.loadSongInfo(it)
                }
    }

    fun encode(encode: String?): String {
        if (encode == null) return ""

        try {
            return URLEncoder.encode(encode, "utf-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return encode
    }
}