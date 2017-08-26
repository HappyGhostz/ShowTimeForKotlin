package com.example.happyghost.showtimeforkotlin.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.news.NewsMainFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<MainPresenter>() {
    override fun upDataView() {

    }

    override fun initView() {
        bottomBar.setOnTabSelectListener {
            when(it){
                R.id.tab_news->addInitFragment(R.id.framlayout_main, NewsMainFragment(),"news")
            }
        }
    }

    override fun initInjector() {

    }

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

}
