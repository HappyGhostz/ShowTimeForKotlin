package com.example.happyghost.showtimeforkotlin.ui.news.channel

import android.graphics.Canvas
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback
import com.chad.library.adapter.base.listener.OnItemDragListener
import com.chad.library.adapter.base.listener.OnItemSwipeListener
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.newsadapter.ChannelAdapter
import com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent.DaggerChannelComponent
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.ChannelModule
import com.example.happyghost.showtimeforkotlin.loacaldao.NewsTypeInfo
import kotlinx.android.synthetic.main.activity_channel.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * @author Zhao Chenping
 * @creat 2017/9/4.
 * @description
 */
class ChannelActivity: BaseActivity<ChannelPresenter>(),IBaseChannelView {

//    @Inject lateinit var channelAdapter: ChannelAdapter
//    @Inject lateinit var unCheckAdapter :ChannelAdapter

    override fun upDataView() {
        mPresenter.getData()
    }
    lateinit var unCheckAdapter: ChannelAdapter
    lateinit var checkListAdapter: ChannelAdapter

    var mCheckList =ArrayList<NewsTypeInfo>()
    var mUnCheckList =ArrayList<NewsTypeInfo>()
    override fun initView() {
        initActionBar(tool_bar,"栏目管理",true)
        tool_bar.setTitleTextColor(ContextCompat.getColor(this,R.color.whiteColor))
        checkListAdapter = ChannelAdapter(mCheckList)


        rv_checked_list.layoutManager= GridLayoutManager(this,4,LinearLayoutManager.VERTICAL,false)
        rv_checked_list.adapter = checkListAdapter

        unCheckAdapter = ChannelAdapter(mUnCheckList)


        rv_unchecked_list.layoutManager = GridLayoutManager(this,4,LinearLayoutManager.VERTICAL,false)
        rv_unchecked_list.adapter = unCheckAdapter
        dragAndSwip()


    }

    private fun dragAndSwip() {
        //拖拽监听设置
        val dragAndSwipeCallback = ItemDragAndSwipeCallback(checkListAdapter)
        val itemTouchHelper = ItemTouchHelper(dragAndSwipeCallback)
        itemTouchHelper.attachToRecyclerView(rv_checked_list)
        //拖拽设置
        val undragAndSwipeCallback = ItemDragAndSwipeCallback(unCheckAdapter)
        val unitemTouchHelper = ItemTouchHelper(undragAndSwipeCallback)
        unitemTouchHelper.attachToRecyclerView(rv_unchecked_list)

        checkListAdapter.enableDragItem(itemTouchHelper)

        checkListAdapter.enableSwipeItem()
        setSwipListener()
        setUnCheckClickListener()
    }
    //对未选中的条目做修改
    private fun setUnCheckClickListener(){
        unCheckAdapter.setOnItemClickListener {
            adapter, view, position ->
            val newsTypeInfo = unCheckAdapter.getItem(position)
            if (newsTypeInfo != null) {
                unCheckAdapter.remove(position)
                checkListAdapter.addData(newsTypeInfo)
                mPresenter.insert(newsTypeInfo)
            }

        }
    }
    //对选中的条目做修改
    private fun setSwipListener() {
        checkListAdapter.setOnItemSwipeListener(object : OnItemSwipeListener {
            override fun clearView(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
                //                viewHolder?.itemView?.setBackgroundColor(ContextCompat.getColor(this@ChannelActivity,R.color.whiteColor))
                val baseViewHolder = viewHolder as BaseViewHolder
                baseViewHolder.setBackgroundColor(R.id.tv_channel_name, ContextCompat.getColor(this@ChannelActivity, R.color.whiteColor))
                baseViewHolder.setTextColor(R.id.tv_channel_name, ContextCompat.getColor(this@ChannelActivity, R.color.grayColor))
            }

            override fun onItemSwiped(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
                val newsTypeInfo = checkListAdapter.getItem(pos)
                if (newsTypeInfo != null) {
                    mPresenter.delete(newsTypeInfo)
                    unCheckAdapter.addData(newsTypeInfo)
                }
            }

            override fun onItemSwipeStart(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
                val baseViewHolder = viewHolder as BaseViewHolder
                baseViewHolder.setTextColor(R.id.tv_channel_name, ContextCompat.getColor(this@ChannelActivity, R.color.whiteColor))
                baseViewHolder.setBackgroundColor(R.id.tv_channel_name, ContextCompat.getColor(this@ChannelActivity, R.color.colorAccent))
            }

            override fun onItemSwipeMoving(canvas: Canvas?, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, isCurrentlyActive: Boolean) {
            }

        })
        checkListAdapter.setOnItemDragListener(object :OnItemDragListener{
            override fun onItemDragMoving(source: RecyclerView.ViewHolder?, from: Int, target: RecyclerView.ViewHolder?, to: Int) {
                mPresenter.upData(checkListAdapter.data)
                mPresenter.swap(from,to)
            }

            override fun onItemDragStart(viewHolder: RecyclerView.ViewHolder?, pos: Int) {

            }

            override fun onItemDragEnd(viewHolder: RecyclerView.ViewHolder?, pos: Int) {

            }

        })
    }

    override fun initInjector() {
        DaggerChannelComponent.builder()
                .applicationComponent(getAppComponent())
                .channelModule(ChannelModule(this))
                .build()
                .inject(this)
    }

    override fun getContentView(): Int {
        return R.layout.activity_channel
    }
    override fun loadData(checkList: ArrayList<NewsTypeInfo>, uncheckList: ArrayList<NewsTypeInfo>) {
        this.mCheckList = checkList
        this.mUnCheckList = uncheckList
        checkListAdapter.addData(checkList)
        unCheckAdapter.addData(uncheckList)
    }

}


