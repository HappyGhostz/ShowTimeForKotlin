package com.example.happyghost.showtimeforkotlin.ui.music.listdetail

import android.app.Activity
import android.content.Context
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.musicdate.RankingListItem
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongListDetail
import com.example.happyghost.showtimeforkotlin.bean.musicdate.WrapperSongListInfo
import com.example.happyghost.showtimeforkotlin.inject.module.musicmodule.MusicListDetailModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import org.jetbrains.anko.startActivity

/**
 * @author Zhao Chenping
 * @creat 2017/11/25.
 * @description
 */
class MusicListDetialActivity:BaseActivity<IBasePresenter>(),IBaseMusicListView {
    lateinit var mMusicList:WrapperSongListInfo.SongListInfo
    lateinit var mMusicRank:RankingListItem.RangkingDetail
    lateinit var musicListDetailModule:MusicListDetailModule
    override fun loadMusicListDetial(list: SongListDetail) {

    }

    override fun upDataView() {

    }

    override fun initView() {

    }

    override fun initInjector() {
        val playListItem = intent.getParcelableExtra<WrapperSongListInfo.SongListInfo>(PLAY_LIST)
        val musicRank = intent.getParcelableExtra<RankingListItem.RangkingDetail>(MUSIC_RANK)
        if(playListItem!=null){
            mMusicList = playListItem
            musicListDetailModule = MusicListDetailModule(this, mMusicList.listid)
        }
        if(musicRank!=null){
            mMusicRank = musicRank
            musicListDetailModule
        }
        DaggerMusicListDetailComponent.builder()
                .applicationComponent(getAppComponent())
                .musicListDetailModule(musicListDetailModule)
                .build()
                .inject(this)

    }

    override fun getContentView(): Int = R.layout.fragment_music_list_detail
    companion object {
        var PLAY_LIST = "playlist"
        var MUSIC_RANK = "musicrank"
        fun lunch(mContext: Context, item: WrapperSongListInfo.SongListInfo?,
                  itemRank: RankingListItem.RangkingDetail?, playList: Boolean) {
            if(playList&&item!=null){
                mContext.startActivity<MusicListDetialActivity>(PLAY_LIST to item)
            }else if(!playList&&itemRank!=null){
                mContext.startActivity<MusicListDetialActivity>(MUSIC_RANK to itemRank)
            }
            (mContext as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }
}