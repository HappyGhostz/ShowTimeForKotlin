package com.example.happyghost.showtimeforkotlin.ui.home

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.book.main.BookMainFragment
import com.example.happyghost.showtimeforkotlin.ui.crosstalk.CrossTalkActivity
import com.example.happyghost.showtimeforkotlin.ui.music.MusicMainFragment
import com.example.happyghost.showtimeforkotlin.ui.news.main.NewsMainFragment
import com.example.happyghost.showtimeforkotlin.ui.picture.BeautyPictureActivity
import com.example.happyghost.showtimeforkotlin.ui.video.VideoMainFragment
import com.example.happyghost.showtimeforkotlin.ui.video.live.LiveActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast


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
                R.id.tab_book->{
                    //开启Manifest.permission.WRITE_SETTINGS
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (!Settings.System.canWrite(this@MainActivity)) {
                            var intent = Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS)
                            intent.data = Uri.parse("package:" + this@MainActivity.packageName)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } else {
                            //有了权限，具体的动作
                            replaceFragment(R.id.framlayout_main, BookMainFragment(), "book")
                        }
                    }else{
                        replaceFragment(R.id.framlayout_main, BookMainFragment(), "book")
                    }
                }
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
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            //添加以下代码会使8.0手机（我只有华为Mate10测试）状态栏半透明，而不是全透明
            //而不加会使5.0（*模拟器4.4）一下手机侧边栏拉出时顶部会有灰色条
//            val layoutParams = window.attributes
//            layoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or layoutParams.flags
//            //主页面延伸至statusbar
//            dl_root.clipToPadding=false
//            //侧拉栏延伸至statusbar
//            dl_root.fitsSystemWindows=true
        }
        dl_root.addDrawerListener(object : DrawerLayout.SimpleDrawerListener(){
//            override fun onDrawerClosed(drawerView: View?) {
//                nv_right.setItemTextAppearance()
//            }
        })
        nv_right.setNavigationItemSelectedListener(this)
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        dl_root.closeDrawer(Gravity.START)
//        if(item.isChecked){
////            return  true
//            item.isChecked = false
//        }
        when(item.itemId){
            R.id.nav_news-> CrossTalkActivity.open(this)
            R.id.nav_photos-> BeautyPictureActivity.open(this)
            R.id.nav_videos-> LiveActivity.open(this)
            R.id.nav_setting->toast("设置")
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

    override fun getContentView(): Int = R.layout.activity_main

}
