package com.example.happyghost.showtimeforkotlin.ui.video.live

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.RxBus.event.LiveTypeChangeEvent
import com.example.happyghost.showtimeforkotlin.adapter.ViewPagerAdapter
import com.example.happyghost.showtimeforkotlin.adapter.videoadapter.AlertDialogAdapter
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import kotlinx.android.synthetic.main.activity_video_layout.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import java.util.*

/**
 * @author Zhao Chenping
 * @creat 2017/12/15.
 * @description
 */
class LiveActivity: BaseActivity<IBasePresenter>() {
    lateinit var mAdapter:ViewPagerAdapter
    private val typeIdList = ArrayList<String>()    //直播平台id
    private val typeNameList = ArrayList<String>()    //直播平台id
    private var currentPos: Int = 0
    private var pos = 0
    private val logoArrays = arrayOf<Int>(R.mipmap.logo_all, R.mipmap.logo_douyu, R.mipmap.logo_panda,
            R.mipmap.logo_zhanqi, R.mipmap.logo_yy, R.mipmap.logo_longzhu, R.mipmap.logo_quanmin,
            R.mipmap.logo_cc, R.mipmap.logo_huomao)
    override fun upDataView() {
        var titles = ArrayList<String>()
        var fragments = ArrayList<Fragment>()
        titles.add(getString(R.string.live_all))
        titles.add(getString(R.string.live_lol))
        titles.add(getString(R.string.live_hs))
        titles.add(getString(R.string.live_dota2))
        titles.add(getString(R.string.live_ow))
        titles.add(getString(R.string.live_csgo))
        fragments.add(LiveFragment.newInstance(getString(R.string.game_type_all), typeIdList[pos]))
        fragments.add(LiveFragment.newInstance(getString(R.string.game_type_lol), typeIdList[pos]))
        fragments.add(LiveFragment.newInstance(getString(R.string.game_type_hs), typeIdList[pos]))
        fragments.add(LiveFragment.newInstance(getString(R.string.game_type_dota2), typeIdList[pos]))
        fragments.add(LiveFragment.newInstance(getString(R.string.game_type_ow), typeIdList[pos]))
        fragments.add(LiveFragment.newInstance(getString(R.string.game_type_csgo), typeIdList[pos]))
        mAdapter.setItems(fragments,titles)
    }

    override fun initView() {
        initActionBar(tool_bar,"直播",true)
        mAdapter = ViewPagerAdapter(supportFragmentManager)
        new_vp.adapter =mAdapter
        tab_new_layout.setupWithViewPager(new_vp)
        //直播平台ID
        typeIdList.addAll(resources.getStringArray(R.array.live_type_id))
        typeNameList.addAll(resources.getStringArray(R.array.live_type_name))
        tv_live_type.setOnClickListener {
            selectLiveType()
        }
    }
    fun selectLiveType(){
        val builder = AlertDialog.Builder(this)
        val dialogView = View.inflate(this, R.layout.layout_dialog, null)
        val typeLogo = dialogView.find<ImageView>(R.id.live_type_picker_logo)
        val typeName = dialogView.find<RecyclerView>(R.id.layout_live_type_picker_recyclerview)
        val mAdapter = AlertDialogAdapter()
        mAdapter.replaceData(typeNameList)
        RecyclerViewHelper.initRecycleViewG(this, typeName, mAdapter, 3,false)
        mAdapter.setOnItemClickListener { _, _, position ->
            ImageLoader.loadImageFromRes(this@LiveActivity, logoArrays[position], typeLogo)
            typeLogo.scaleType = ImageView.ScaleType.FIT_CENTER
            currentPos = position
        }
        builder.setView(dialogView)
                .setPositiveButton("确定") { dialog, which ->
                    pos = currentPos
                    tv_live_type.text = typeNameList[pos]
                    upDataView()
                    AppApplication.instance.mRxBus?.post(LiveTypeChangeEvent(typeIdList[pos]))
                    dialog.dismiss()
                }
                .setNegativeButton("取消") { dialog, which -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.baseColorPrimary))
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.common_h2))
    }

    override fun initInjector() {

    }

    override fun getContentView(): Int = R.layout.activity_video_layout
    companion object {
        fun open(context: Context){
            context.startActivity<LiveActivity>()
            (context as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }
}