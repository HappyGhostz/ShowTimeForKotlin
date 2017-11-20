package com.example.happyghost.showtimeforkotlin.ui.book.rank.iteminfo

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.ViewPagerAdapter
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import kotlinx.android.synthetic.main.activity_sub_rank_layout.*
import org.jetbrains.anko.startActivity
import java.util.ArrayList

/**
 * @author Zhao Chenping
 * @creat 2017/11/20.
 * @description
 */
class SubRankActivity: BaseActivity<IBasePresenter>() {
    lateinit var mId:String
    lateinit var mRankMonth:String
    lateinit var mRankTotal:String
    lateinit var mRankTitle:String
    lateinit var viewPagerAdapter:ViewPagerAdapter
    override fun upDataView() {

    }

    override fun initView() {
        initActionBar(tool_category_bar,mRankTitle,true)
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        initData()
    }

    private fun initData() {
        val titles = ArrayList<String>()
        titles.add("周榜")
        titles.add("月榜")
        titles.add("总榜")
        val fragments = ArrayList<Fragment>()
        fragments.add(SubRankFragment.newInstance(mId))
        fragments.add(SubRankFragment.newInstance(mRankMonth))
        fragments.add(SubRankFragment.newInstance(mRankTotal))
        viewPagerAdapter.setItems(fragments, titles)
        viewpager.adapter = viewPagerAdapter
        tab_category_layout.setupWithViewPager(viewpager)
    }

    override fun initInjector() {
        mId = intent.getStringExtra(SUBID)
        mRankMonth = intent.getStringExtra(MONTH_RANK)
        mRankTotal = intent.getStringExtra(TOTAL_RANK)
        mRankTitle = intent.getStringExtra(SUB_TITLE).split(" ")[0]
    }

    override fun getContentView(): Int =R.layout.activity_sub_rank_layout
    companion object {
        var SUBID = "subid"
        var MONTH_RANK = "monthrank"
        var TOTAL_RANK = "totalrank"
        var SUB_TITLE = "title"
        fun startActivity(mContext: Context, _id: String, monthRank: String, totalRank: String, title: String) {
            mContext.startActivity<SubRankActivity>(SUBID to _id,MONTH_RANK to monthRank,TOTAL_RANK to totalRank,SUB_TITLE to title)
            (mContext as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }
}