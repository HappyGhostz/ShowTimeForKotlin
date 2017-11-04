package com.example.happyghost.showtimeforkotlin.utils

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.utils.itemdivider.DividerGridItemDecoration

/**
 * @author Zhao Chenping
 * @creat 2017/9/6.
 * @description
 */
class RecyclerViewHelper {
    companion object {

        /**
         * 配置垂直列表RecyclerView
         * @param view
         */
         fun initRecycleViewV(mContext:Context?, recyclerView: RecyclerView, adapter:RecyclerView.Adapter<BaseViewHolder>, hasDivider:Boolean) {
            val layoutManager = LinearLayoutManager(mContext)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            recyclerView.layoutManager = layoutManager
            if(hasDivider){
                recyclerView.addItemDecoration(DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL))
            }
            recyclerView.adapter = adapter
        }
        /**
         * 配置网格列表RecyclerView
         * @param view
         */
        fun initRecycleViewG(mContext:Context?, recyclerView: RecyclerView, adapter:RecyclerView.Adapter<BaseViewHolder>,column:Int,hasDivider:Boolean){
            var groupManager = GridLayoutManager(mContext,column,LinearLayoutManager.VERTICAL,false)
            recyclerView.layoutManager = groupManager
            if(hasDivider){
                recyclerView.addItemDecoration(DividerGridItemDecoration(mContext))
            }
            recyclerView.adapter = adapter
        }
        /**
         * 配置横向列表RecyclerView
         * @param view
         */
        fun initRecycleViewH(mContext:Context?, recyclerView: RecyclerView, adapter:RecyclerView.Adapter<BaseViewHolder>, hasDivider:Boolean) {
            val layoutManager = LinearLayoutManager(mContext)
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
            recyclerView.layoutManager = layoutManager
            if(hasDivider){
                recyclerView.addItemDecoration(DividerItemDecoration(mContext, LinearLayoutManager.HORIZONTAL))
            }
            recyclerView.adapter = adapter
        }
    }
}