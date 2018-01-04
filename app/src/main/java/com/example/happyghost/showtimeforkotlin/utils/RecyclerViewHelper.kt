package com.example.happyghost.showtimeforkotlin.utils

import android.content.Context
import android.support.v7.widget.*
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
            recyclerView.apply {
                layoutManager = LinearLayoutManager(mContext).apply {
                    orientation = LinearLayoutManager.VERTICAL
                }
                if(hasDivider){
                    addItemDecoration(DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL))
                }
                this.adapter = adapter
            }
        }
        /**
         * 配置网格列表RecyclerView
         * @param view
         */
        fun initRecycleViewG(mContext:Context?, recyclerView: RecyclerView, adapter:RecyclerView.Adapter<BaseViewHolder>,column:Int,hasDivider:Boolean){
            recyclerView.apply {
                layoutManager = GridLayoutManager(mContext,column,LinearLayoutManager.VERTICAL,false)
                if(hasDivider){
                    addItemDecoration(DividerGridItemDecoration(mContext))
                }
                this.adapter = adapter
            }
        }
        /**
         * 配置横向列表RecyclerView
         * @param view
         */
        fun initRecycleViewH(mContext:Context?, recyclerView: RecyclerView, adapter:RecyclerView.Adapter<BaseViewHolder>, hasDivider:Boolean) {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(mContext).apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }
                if(hasDivider){
                    addItemDecoration(DividerGridItemDecoration(mContext))
                }
                this.adapter = adapter
            }
        }
        /**
         * 配置瀑布流列表RecyclerView
         * @param view
         */
        fun initRecycleViewSV(mContext:Context?, recyclerView: RecyclerView, adapter:RecyclerView.Adapter<BaseViewHolder>,column:Int,hasDivider:Boolean){
            recyclerView.apply {
                layoutManager = StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL)
                if(hasDivider){
                    addItemDecoration(DividerGridItemDecoration(mContext))
                }
                this.adapter = adapter
            }
        }
    }
}