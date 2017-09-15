package com.example.happyghost.showtimeforkotlin.ui.home

import android.os.Build
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.MenuItem
import android.view.WindowManager
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.book.main.BookMainFragment
import com.example.happyghost.showtimeforkotlin.ui.music.MusicMainFragment
import com.example.happyghost.showtimeforkotlin.ui.news.main.NewsMainFragment
import com.example.happyghost.showtimeforkotlin.ui.video.VideoMainFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<MainPresenter>(), NavigationView.OnNavigationItemSelectedListener {


    override fun upDataView() {

    }

    override fun initView() {
        initStatusBar()
        initNavigationView()
        addInitFragment(R.id.framlayout_main, NewsMainFragment(),"news")
        //bottom的icon必须为透明无色无填充图标，可以在https://github.com/google/material-design-icons/上找
        bottomBar.setOnTabSelectListener {
            when(it){
                R.id.tab_news->replaceFragment(R.id.framlayout_main, NewsMainFragment(),"news")
                R.id.tab_video->replaceFragment(R.id.framlayout_main,VideoMainFragment(),"video")
                R.id.tab_music->replaceFragment(R.id.framlayout_main, MusicMainFragment(),"music")
                R.id.tab_book->replaceFragment(R.id.framlayout_main, BookMainFragment(),"book")
            }
        }


    }

    /**
     * 对顶部状态栏覆盖toolbar的情况做处理
     */
    fun initStatusBar(){
        var statusBarHeight : Int =-1
        val identifierId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if(identifierId>0){
           statusBarHeight =  resources.getDimensionPixelSize(identifierId)
        }
        status_hight.height = statusBarHeight
    }

    /**
     * 初始化侧拉栏
     */
    fun initNavigationView(){
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT){
            val layoutParams = window.attributes
            layoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or layoutParams.flags
            //主页面延伸至statusbar
            dl_root.clipToPadding=false
            //侧拉栏延伸至statusbar
            dl_root.fitsSystemWindows=true
        }
        dl_root.addDrawerListener(object : DrawerLayout.SimpleDrawerListener(){

        })
        nv_right.setNavigationItemSelectedListener(this)
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        dl_root.closeDrawer(Gravity.START)
        if(item.isChecked){
            return  true
        }
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId==android.R.id.home){
            dl_root.openDrawer(Gravity.START)
            return  true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initInjector() {

    }

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

}
