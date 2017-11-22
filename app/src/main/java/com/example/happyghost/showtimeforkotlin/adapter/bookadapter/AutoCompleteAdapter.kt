package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.util.ArrayList
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.example.happyghost.showtimeforkotlin.R
import org.jetbrains.anko.find


/**
 * @author Zhao Chenping
 * @creat 2017/11/22.
 * @description
 */
class AutoCompleteAdapter(context: Context, mAutoList: ArrayList<String>) : BaseAdapter() {
    var mContext = context
    var list= mAutoList

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: ViewHolder?
        var view: View
        if (convertView == null) {
            holder = ViewHolder()
            view = LayoutInflater.from(mContext).inflate(R.layout.item_pop_list, null)
            holder.ivIconSeach = view.find(R.id.ivIconSeach)
            holder.tvAutoCompleteItem = view.find(R.id.tvAutoCompleteItem)
            view?.tag = holder

        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }
        holder.tvAutoCompleteItem?.text = list[position]
        return view
    }

    override fun getItem(position: Int): String =list[position]

    override fun getItemId(position: Int): Long =position.toLong()

    override fun getCount(): Int = list.size
    inner class ViewHolder{
        var ivIconSeach: ImageView? = null
        var tvAutoCompleteItem : TextView? = null
    }
}