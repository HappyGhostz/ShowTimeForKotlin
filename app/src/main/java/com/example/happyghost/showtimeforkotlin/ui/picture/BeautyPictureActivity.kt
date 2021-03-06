package com.example.happyghost.showtimeforkotlin.ui.picture

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.ViewPagerAdapter
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.ui.picture.WelfarePicture.WelfarePictureFragment
import com.example.happyghost.showtimeforkotlin.ui.picture.beautypicture.BeautyPictureFragment
import kotlinx.android.synthetic.main.activity_cross_talk_layout.*
import org.jetbrains.anko.startActivity

/**
 * @author Zhao Chenping
 * @creat 2017/12/12.
 * @description
 */
class BeautyPictureActivity: BaseActivity<IBasePresenter>() {
    lateinit var mAdapter: ViewPagerAdapter
    override fun upDataView() {
        var titles = ArrayList<String>()
        var fragments = ArrayList<Fragment>()
        titles.add("美图")
        titles.add("福利")
        fragments.add(BeautyPictureFragment.instance())
        fragments.add(WelfarePictureFragment.instance())
        mAdapter.setItems(fragments,titles)
    }

    override fun initView() {
        initActionBar(tool_bar,"图片",true)
        mAdapter = ViewPagerAdapter(supportFragmentManager)
        new_vp.adapter =mAdapter
        tab_new_layout.setupWithViewPager(new_vp)
    }

    override fun initInjector() {

    }

    override fun getContentView(): Int = R.layout.activity_cross_talk_layout
    companion object {
        fun open(context: Context){
            context.startActivity<BeautyPictureActivity>()
            (context as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }
}