package com.example.happyghost.showtimeforkotlin.ui.news.special

import android.app.Activity
import android.content.Context

import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.ui.base.BaseSwipeBackActivity
import com.example.happyghost.showtimeforkotlin.bean.SpecialInfo
import com.example.happyghost.showtimeforkotlin.bean.SpecialItem
import org.jetbrains.anko.startActivity

class NewsSpecialActivity : BaseSwipeBackActivity<NewsSpecialPresenter>(),INewsSpecialView {
    override fun loadData(specialItems: List<SpecialItem>) {

    }

    override fun loadBanner(specialInfo: SpecialInfo) {

    }

    override fun upDataView() {

    }

    override fun initView() {
    }

    override fun initInjector() {
    }

    override fun getContentView(): Int {
        return R.layout.activity_news_special
    }

    companion object {
        var SPECIAL_ID:String = "specialid"
        fun lunch(context: Context,specialId:String){
            context.startActivity<NewsSpecialActivity>(SPECIAL_ID to specialId)
            (context as Activity).overridePendingTransition(R.anim.slide_right_entry, R.anim.fade_exit)
        }
    }
}
