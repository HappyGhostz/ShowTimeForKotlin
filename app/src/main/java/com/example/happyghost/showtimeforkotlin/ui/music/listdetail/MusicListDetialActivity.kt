package com.example.happyghost.showtimeforkotlin.ui.music.listdetail

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.musicadapter.MusicListDetialAdapter
import com.example.happyghost.showtimeforkotlin.bean.musicdate.RankingListDetail
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongListDetail
import com.example.happyghost.showtimeforkotlin.bean.musicdate.WrapperSongListInfo
import com.example.happyghost.showtimeforkotlin.inject.module.musicmodule.MusicListDetailModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.utils.DefIconFactory
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader
import com.example.happyghost.showtimeforkotlin.utils.MusicTransformer
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import kotlinx.android.synthetic.main.fragment_music_list_detail.*
import kotlinx.android.synthetic.main.music_list_toolbar_head.*
import kotlinx.android.synthetic.main.music_list_toolbar_head_rank.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject
import android.view.View
import android.widget.RelativeLayout
import com.example.happyghost.showtimeforkotlin.inject.component.musiccomponent.DaggerMusicListDetailComponent
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.ui.music.play.MusicPlayActivity
import kotlinx.android.synthetic.main.item_head_music.*
import org.jetbrains.anko.find


/**
 * @author Zhao Chenping
 * @creat 2017/11/25.
 * @description
 */
class MusicListDetialActivity:BaseActivity<MusicListDetialPresenter>(),IBaseMusicListView {
    var mMusicList: WrapperSongListInfo.SongListInfo? = null
    var mType : String? =null
    var mTitle:String?=null
    @Inject lateinit var mAdapter: MusicListDetialAdapter
    lateinit var allMusicLayout:RelativeLayout
    override fun loadMusicListDetial(list: SongListDetail) {
        val content = list.getContent()
        tv_play_all_number.text = "(共" + content?.size.toString() + "首)"
        var musicList = MusicTransformer.musicListTransformer(content)
        mAdapter.replaceData(musicList)

    }
    override fun loadRankPlayList(detail: RankingListDetail) {
        val billboard = detail.getBillboard()
        ImageLoader.loadCenterCrop(this, billboard?.pic_s210!!, iv_album_art, DefIconFactory.provideIcon())
        val song_list = detail.getSong_list()
        tv_play_all_number.text = "(共" + song_list?.size.toString() + "首)"
        var rankMusic = MusicTransformer.musicRankListTransformer(song_list)
        mAdapter.replaceData(rankMusic)

    }



    override fun upDataView() {
        if(mMusicList!=null){
            mPresenter.getMusicListInfo()
        }else{
            mPresenter.getRankMusicInfo()
        }
    }

    override fun initView() {
        if(mTitle!=null){
            initActionBar(toolbar,"",true)
            toolbarTitle.text = mTitle
        }else{
            initActionBar(toolbar,"",true)
            toolbarTitle.text = "歌单"
            initToolBarBackRes()
        }

        if(mMusicList==null){
            visible(headFrame)
            gone(headerview)
        }
        RecyclerViewHelper.initRecycleViewV(this,irv_list_detail,mAdapter,true)
        var musicHead = View.inflate(this, R.layout.item_head_music, null)
        allMusicLayout = musicHead!!.find<RelativeLayout>(R.id.rl_play_all_layout)
        if (mAdapter.headerLayout != null) {
            mAdapter.removeAllHeaderView()
            mAdapter.addHeaderView(musicHead)
        } else {
            mAdapter.addHeaderView(musicHead)
        }
        if(mMusicList!=null){
            MusicPlayActivity.open()
        }
    }

    private fun initToolBarBackRes() {
        ImageLoader.loadCenterCrop(this, mMusicList?.pic_300!!, iv_songlist_photo, DefIconFactory.provideIcon())
        tv_songlist_count.text = mMusicList?.listenum
        tv_songlist_name.text = mMusicList?.title
        val split = mMusicList?.tag?.split(",".toRegex())!!.dropLastWhile({ it.isEmpty() }).toTypedArray()
        val stringBuffer = StringBuffer()
        stringBuffer.append("标签：")
        for (i in split.indices) {
            stringBuffer.append(split[i] + " ")
        }
        tv_songlist_detail.text = stringBuffer
//        new PathAsyncTask(mAlbumArt).execute(photoUrl);
        MusicTransformer.pictureDim(this as BaseActivity<IBasePresenter>,mMusicList?.pic_300!!,album_art)
    }
    override fun initInjector() {
        var property: String? =null
        val playListItem = intent.getParcelableExtra<WrapperSongListInfo.SongListInfo>(PLAY_LIST)
        val type = intent.getIntExtra("type",0)
        val title = intent.getStringExtra("title")
        if(playListItem!=null){
            mMusicList = playListItem
//            musicListDetailModule = MusicListDetailModule(this, mMusicList?.listid)
            property =mMusicList?.listid
        }
        if(type!=0){
            mType = type.toString()
            mTitle=title
//            musicListDetailModule = MusicListDetailModule(this,mType)
            property = mType
        }
        DaggerMusicListDetailComponent.builder()
                .applicationComponent(getAppComponent())
                .musicListDetailModule(MusicListDetailModule(this,property))
                .build()
                .inject(this)

    }

    override fun getContentView(): Int = R.layout.fragment_music_list_detail
    companion object {
        var PLAY_LIST = "playlist"
        var MUSIC_RANK = "musicrank"
        fun lunch(mContext: Context, item: WrapperSongListInfo.SongListInfo?, playList: Boolean) {
            if(playList&&item!=null){
                mContext.startActivity<MusicListDetialActivity>(PLAY_LIST to item)
            }
            (mContext as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }

        fun lunch(mContext: Context, position: Int, title: String, playList: Boolean) {
            val intent = Intent(mContext, MusicListDetialActivity::class.java)
            if (position + 1 == 5) {
                intent.putExtra("type", 20)
            } else if (position + 1 == 8) {
                intent.putExtra("type", 24)
            } else if (position + 1 == 3) {
                intent.putExtra("type", 21)
            } else if (position + 1 == 6) {
                intent.putExtra("type", 22)
            } else if (position + 1 == 9) {
                intent.putExtra("type", 23)
            } else if (position + 1 == 7) {
                intent.putExtra("type", 25)
            } else if (position + 1 == 10) {
                intent.putExtra("type", 8)
            } else if (position + 1 == 4) {
                intent.putExtra("type", 100)
            } else {
                intent.putExtra("type", position + 1)
            }
            intent.putExtra("title", title)
            intent.putExtra(PLAY_LIST,playList)
            mContext.startActivity(intent)
            (mContext as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }
}