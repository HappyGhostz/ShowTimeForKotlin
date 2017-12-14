package com.example.happyghost.showtimeforkotlin.ui.picture

import android.app.Activity
import android.content.Context
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.ScreenUtils
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.activity_beg_picture.*
import kotlinx.android.synthetic.main.activity_read.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

/**
 * @author Zhao Chenping
 * @creat 2017/12/14.
 * @description
 */
class BegPictureActivity:BaseActivity<IBasePresenter>() {
    lateinit var imageUrl:String
    lateinit var imageDes:String
    override fun upDataView() {

    }

    override fun initView() {
        //隐藏状态栏
        begRootView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        initActionBar(toolbar,imageDes,true)
        val widthPixels = ScreenUtils.getScreenWidth()
        val screenHeight = ScreenUtils.getScreenHeight()
        val simpleDraweeView = find<SimpleDraweeView>(R.id.begPicture)
        var layoutParams = simpleDraweeView.layoutParams
        layoutParams?.width = widthPixels
        layoutParams?.height =screenHeight
        simpleDraweeView.layoutParams = layoutParams

        var  controller = Fresco.newDraweeControllerBuilder()
                .setUri(imageUrl)
                .setAutoPlayAnimations(true)
                .setTapToRetryEnabled(true)
                .build()
        simpleDraweeView.controller = controller
    }

    override fun initInjector() {
        imageUrl = intent.getStringExtra(IMAGE_URL)
        imageDes = intent.getStringExtra(IMAGE_DESC)
    }

    override fun getContentView(): Int = R.layout.activity_beg_picture
    companion object {
        var IMAGE_URL = "imageurl"
        var IMAGE_DESC ="desc"
        fun open(context: Context, url: String, descDefault: String){
            context.startActivity<BegPictureActivity>(IMAGE_URL to url,IMAGE_DESC to descDefault)
            (context as Activity).overridePendingTransition(R.anim.expand_vertical_entry, R.anim.hold)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.hold, R.anim.expand_vertical_exit)
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
        overridePendingTransition(R.anim.hold, R.anim.expand_vertical_exit)
    }
}