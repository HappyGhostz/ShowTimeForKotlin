package com.example.happyghost.showtimeforkotlin.ui.book.classify.classifydetail

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.ViewPagerAdapter
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import kotlinx.android.synthetic.main.activity_classify_detail.*
import org.jetbrains.anko.startActivity
import java.util.ArrayList

/**
 * @author Zhao Chenping
 * @creat 2017/11/15.
 * @description
 */
class ClassifyDetailActivity: BaseActivity<IBasePresenter>() {
    override fun upDataView() {

    }
    lateinit var mCategoryType:String
    lateinit var mCategoryName :String
    lateinit var mViewPagerAdapter:ViewPagerAdapter
    override fun initView() {
        mCategoryType = intent.getStringExtra(CLASSIFY_SEX)
        mCategoryName = intent.getStringExtra(CHANNEL_NAME)
        initActionBar(tool_category_bar,mCategoryName,true)
        mViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        initToolayoutAndViewPager()
    }

    private fun initToolayoutAndViewPager() {
        val titles = ArrayList<String>()
        titles.add("新书")
        titles.add("热门")
        titles.add("口碑")
        titles.add("完结")
        val fragments = ArrayList<Fragment>()
        fragments.add(ClassifyDetailFragment.lunch(mCategoryName, mCategoryType, "new"))
        fragments.add(ClassifyDetailFragment.lunch(mCategoryName, mCategoryType, "hot"))
        fragments.add(ClassifyDetailFragment.lunch(mCategoryName, mCategoryType, "reputation"))
        fragments.add(ClassifyDetailFragment.lunch(mCategoryName, mCategoryType, "over"))
        mViewPagerAdapter.setItems(fragments, titles)
        category_vp.adapter = mViewPagerAdapter
        tab_category_layout.setupWithViewPager(category_vp)
    }

    override fun initInjector() {

    }

    override fun getContentView(): Int {
        return R.layout.activity_classify_detail
    }
    companion object {
        var CHANNEL_NAME = "channelname"
        var CLASSIFY_SEX = "classifysex"
        fun start(context: Context,name:String,sex:String){
           context.startActivity<ClassifyDetailActivity>(CHANNEL_NAME to name,CLASSIFY_SEX to sex)
            (context as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }
}