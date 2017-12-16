package com.example.happyghost.showtimeforkotlin.ui.video.live

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.ViewPagerAdapter
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import kotlinx.android.synthetic.main.activity_cross_talk_layout.*
import org.jetbrains.anko.startActivity
import java.util.*

/**
 * @author Zhao Chenping
 * @creat 2017/12/15.
 * @description
 */
class LiveActivity: BaseActivity<IBasePresenter>() {
    lateinit var mAdapter:ViewPagerAdapter
    private val typeIdList = ArrayList<String>()    //直播平台id
    private var currentPos: Int = 0
    private var pos = 0
    override fun upDataView() {
        var titles = ArrayList<String>()
        var fragments = ArrayList<Fragment>()
        titles.add(getString(R.string.live_all))
        titles.add(getString(R.string.live_lol))
        titles.add(getString(R.string.live_hs))
        titles.add(getString(R.string.live_dota2))
        titles.add(getString(R.string.live_ow))
        titles.add(getString(R.string.live_csgo))
        fragments.add(LiveFragment.newInstance(getString(R.string.game_type_all), typeIdList[pos]))
        fragments.add(LiveFragment.newInstance(getString(R.string.game_type_lol), typeIdList[pos]))
        fragments.add(LiveFragment.newInstance(getString(R.string.game_type_hs), typeIdList[pos]))
        fragments.add(LiveFragment.newInstance(getString(R.string.game_type_dota2), typeIdList[pos]))
        fragments.add(LiveFragment.newInstance(getString(R.string.game_type_ow), typeIdList[pos]))
        fragments.add(LiveFragment.newInstance(getString(R.string.game_type_csgo), typeIdList[pos]))
        mAdapter.setItems(fragments,titles)
    }

    override fun initView() {
        initActionBar(tool_bar,"直播",true)
        mAdapter = ViewPagerAdapter(supportFragmentManager)
        new_vp.adapter =mAdapter
        tab_new_layout.setupWithViewPager(new_vp)
        //直播平台ID
        typeIdList.addAll(resources.getStringArray(R.array.live_type_id))
    }

    override fun initInjector() {

    }

    override fun getContentView(): Int = R.layout.activity_video_layout
    companion object {
        fun open(context: Context){
            context.startActivity<LiveActivity>()
            (context as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }
}