package com.example.happyghost.showtimeforkotlin.ui.news.special

import android.app.Activity
import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewStub
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.dl7.tag.TagLayout
import com.dl7.tag.TagView

import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.NewsSpecialAdapter
import com.example.happyghost.showtimeforkotlin.ui.base.BaseSwipeBackActivity
import com.example.happyghost.showtimeforkotlin.bean.SpecialInfo
import com.example.happyghost.showtimeforkotlin.bean.SpecialItem
import com.example.happyghost.showtimeforkotlin.inject.component.DaggerNewsSpecialComponent
import com.example.happyghost.showtimeforkotlin.inject.module.NewSpecialModule
import com.example.happyghost.showtimeforkotlin.utils.DefIconFactory
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_news_special.*
import kotlinx.android.synthetic.main.item_head_special_news.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class NewsSpecialActivity : BaseSwipeBackActivity<NewsSpecialPresenter>(),INewsSpecialView {
    @Inject lateinit var mAdapter :NewsSpecialAdapter
    private val mSkipId = IntArray(20)
    lateinit var specialId:String
    lateinit var layoutManager : LinearLayoutManager
    lateinit var tagLayout:TagLayout
    override fun loadData(specialItems: List<SpecialItem>) {
        mAdapter.replaceData(specialItems)
        handleTagLayout(specialItems)

    }

    override fun loadBanner(specialInfo: SpecialInfo) {
        val view = LayoutInflater.from(this).inflate(R.layout.item_head_special_news, null)
        val imageView = view.find<ImageView>(R.id.iv_banner)
        ImageLoader.loadFitCenter(this,specialInfo.getBanner(),imageView,DefIconFactory.provideIcon())
        if(!TextUtils.isEmpty(specialInfo.getDigest())){
            val viewStub = view.find<ViewStub>(R.id.vs_digest)
            assert(viewStub != null)
            viewStub.inflate()
            val textView = view.find<TextView>(R.id.tv_digest)
            textView.setText(specialInfo.getDigest())
        }
        //后添加的view视图，必须用View.find不能用简化
        tagLayout = view.find<TagLayout>(R.id.tag_layout)
        if(mAdapter.headerLayout!=null){
            mAdapter.removeAllHeaderView()
            mAdapter.addHeaderView(view)
        }else{
            mAdapter.addHeaderView(view)
        }

    }
    /**
     * 处理导航标签
     *
     * @param specialItems
     */
    fun handleTagLayout(specialItems: List<SpecialItem>) {
        Observable.fromIterable(specialItems)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(object :Predicate<SpecialItem> {
                    var i :Int = 0
                    var index = mAdapter.headerLayoutCount
                    override fun test(t: SpecialItem): Boolean {

                        if(t.isHeader){
                            // 记录头部的列表索引值，用来跳转
                            mSkipId[i++] = index
                        }
                        index++
                        return t.isHeader
                    }
                })
                .map {
                    chipHeadStr(it.header)
                }
                .subscribe { tagLayout.addTag(it) }
        tagLayout.setTagClickListener(TagView.OnTagClickListener { position, text, tagMode ->
            // 跳转到对应position,比scrollToPosition（）精确
            layoutManager.scrollToPositionWithOffset(mSkipId[position], 0)
        })
    }
    fun chipHeadStr(header: String):String {
        var head: String = null.toString()
        val indexOf = header.indexOf("")
        if(indexOf!=-1){
            head = header.substring(indexOf,header.length)
        }
        return head
    }

    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView() {
        initActionBar(toolbar,"",true)
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        RecyclerViewHelper.initRecycleViewV(this,rv_special_list,mAdapter,true)
        layoutManager = rv_special_list.layoutManager as LinearLayoutManager
        val actionButton = find<FloatingActionButton>(R.id.fab_coping)
        actionButton.setOnClickListener {
            layoutManager.scrollToPosition(0)
        }
    }

    override fun initInjector() {
        specialId = intent.getStringExtra(SPECIAL_ID)
        DaggerNewsSpecialComponent.builder()
                .applicationComponent(getAppComponent())
                .newSpecialModule(NewSpecialModule(this,specialId))
                .build()
                .inject(this)
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

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit)
    }
}
