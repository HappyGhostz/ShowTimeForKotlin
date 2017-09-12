package com.example.happyghost.showtimeforkotlin.utils

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author Zhao Chenping
 * @creat 2017/9/6.
 * @description
 */
class RecyclerViewHelper {
    companion object {
         fun initRecycleViewV(mContext:Context?, recyclerView: RecyclerView, adapter:RecyclerView.Adapter<BaseViewHolder>, hasDivider:Boolean) {
            val layoutManager = LinearLayoutManager(mContext)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            recyclerView.layoutManager = layoutManager
            if(hasDivider){
                recyclerView.addItemDecoration(DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL))
            }
            recyclerView.adapter = adapter
        }
    }
}