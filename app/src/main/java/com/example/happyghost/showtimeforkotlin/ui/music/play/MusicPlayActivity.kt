package com.example.happyghost.showtimeforkotlin.ui.music.play

import android.app.Activity
import android.content.Context
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.musicdate.SongLocalBean
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import org.jetbrains.anko.startActivity

/**
 * Created by e445 on 2017/11/27.
 */
class MusicPlayActivity:BaseActivity<IBasePresenter>() {
    override fun upDataView() {

    }

    override fun initView() {

    }

    override fun initInjector() {
        val localSons = intent.getParcelableArrayListExtra<SongLocalBean>(LOCAL_SONGS)
        var itemPosition = intent.getIntExtra(LOCAL_SONGS_POSITION,0)
        var isLocalMusic = intent.getBooleanExtra(IS_LOCAL_MUSIC,true)
    }

    override fun getContentView(): Int = R.layout.activity_music_play
    companion object {
        var LOCAL_SONGS = "localsongs"
        var LOCAL_SONGS_POSITION = "position"
        var IS_LOCAL_MUSIC = "islocalmusic"
        fun open(mContext: Context?, sons: ArrayList<SongLocalBean>, position: Int, isLocal: Boolean) {
            mContext?.startActivity<MusicPlayActivity>(LOCAL_SONGS to sons,
                    LOCAL_SONGS_POSITION to position,IS_LOCAL_MUSIC to isLocal)
            (mContext as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
        fun open(){}
    }
}