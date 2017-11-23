package com.example.happyghost.showtimeforkotlin.ui.news.photonews

import android.content.Context
import android.support.v4.view.ViewPager
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.newsadapter.PhotoSetAdapter
import com.example.happyghost.showtimeforkotlin.bean.newsdate.PhotoSetInfo
import com.example.happyghost.showtimeforkotlin.inject.component.newscomponent.DaggerPhotoSetNewsComponent
import com.example.happyghost.showtimeforkotlin.inject.module.newsmodule.PhotoSetNewsModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_photo_set.*
import kotlinx.android.synthetic.main.layout_toolbar_transparent.*
import org.jetbrains.anko.startActivity

/**
 * @author Zhao Chenping
 * @creat 2017/9/13.
 * @description
 */
class PhotoSetNewsActivity : BaseActivity<PhotoSetPresenter>(),IPhotoSetView{
    var isToolBarHide = false
    override fun loadData(photoSetBean: PhotoSetInfo) {
        val imgList = ArrayList<String>()
        val photosEntity = photoSetBean.getPhotos()
        for (entity in photosEntity!!){
            imgList.add(entity.imgurl!!)
        }
        val photoSetAdapter = PhotoSetAdapter(this, imgList)
        vp_photo.adapter = photoSetAdapter
        vp_photo.offscreenPageLimit = imgList.size
        tv_count.setText(photosEntity.size.toString()+"")
        tv_title.setText(photoSetBean.getSetname())
        tv_index.setText("1/")
        tv_content.setText(photosEntity.get(0).note)
        vp_photo.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                tv_content.setText(photosEntity.get(position).note)
                tv_index.setText((position+1).toString()+"/")
            }
        })
        photoSetAdapter.setTapListener(object : PhotoSetAdapter.OnTapListener{
            override fun onPhotoClick() {
                isToolBarHide=!isToolBarHide
                if(isToolBarHide){
                    drag_layout.scrollOutScreen(300)
                    toolbar.animate().translationY(-toolbar.bottom.toFloat()).setDuration(300)
                }else{
                    drag_layout.scrollInScreen(300)
                    toolbar.animate().translationY(0f).setDuration(300)
                }
            }
        })


    }

    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView() {
        initActionBar(toolbar,"",true)
    }

    override fun initInjector() {
        val photoId = intent.getStringExtra(PHOTO_SET_ID)
        DaggerPhotoSetNewsComponent.builder()
                .applicationComponent(getAppComponent())
                .photoSetNewsModule(PhotoSetNewsModule(this, photoId))
                .build()
                .inject(this)
    }

    override fun getContentView(): Int {
        return R.layout.activity_photo_set
    }
    companion object {
        var PHOTO_SET_ID:String ="photosetid"
        fun launch(context: Context,photoId:String){
            context.startActivity<PhotoSetNewsActivity>(PHOTO_SET_ID to photoId)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.hold, R.anim.fade_exit)
    }
}