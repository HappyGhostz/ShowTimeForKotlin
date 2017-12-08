package com.example.happyghost.showtimeforkotlin.ui.crosstalk

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.ViewPagerAdapter
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.ui.crosstalk.crossfragment.CrossTalkFragment
import com.example.happyghost.showtimeforkotlin.ui.crosstalk.funnypicture.FunnyPictureFragment
import kotlinx.android.synthetic.main.activity_cross_talk_layout.*
import org.jetbrains.anko.startActivity

/**
 * @author Zhao Chenping
 * @creat 2017/12/8.
 * @description
 */
class CrossTalkActivity: BaseActivity<IBasePresenter>() {
    lateinit var mAdapter:ViewPagerAdapter
    override fun upDataView() {
        var titles = ArrayList<String>()
        var fragments = ArrayList<Fragment>()
        titles.add("段子")
        titles.add("图片")
        fragments.add(CrossTalkFragment.instance())
        fragments.add(FunnyPictureFragment.instance())
        mAdapter.setItems(fragments,titles)

    }

    override fun initView() {
        initActionBar(tool_bar,"段子",true)
        mAdapter = ViewPagerAdapter(supportFragmentManager)
        new_vp.adapter =mAdapter
        tab_new_layout.setupWithViewPager(new_vp)
    }

    override fun initInjector() {

    }

    override fun getContentView(): Int=R.layout.activity_cross_talk_layout
    companion object {
        fun open(context: Context){
            context.startActivity<CrossTalkActivity>()
            (context as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }
}