package com.example.happyghost.showtimeforkotlin.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import java.util.*

/**
 * @author Zhao Chenping
 * @creat 2017/8/30.
 * @description
 */
class ViewPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    //MutableList可变集合需要增删   list是不可变集合
    var mFragments :MutableList<Fragment>?=null
    var mTitles :MutableList<String>? = null
    init {
        mFragments = ArrayList<Fragment>()
        mTitles = ArrayList()
    }
    override fun getItem(position: Int): Fragment {
        return mFragments!!.get(position)
    }

    override fun getCount(): Int {
        return mFragments!!.size

    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitles!!.get(position)
    }

    override fun getItemPosition(`object`: Any?): Int {
        return PagerAdapter.POSITION_NONE
    }

    fun setItems(fragments:MutableList<Fragment>, titles:MutableList<String>){
        mFragments=fragments
        mTitles = titles
        notifyDataSetChanged()
    }

    fun setItems(fragments: MutableList<Fragment>, mTitles: Array<String>) {
        this.mFragments = fragments
        this.mTitles = Arrays.asList(*mTitles)
        notifyDataSetChanged()
    }
    fun addItems(fragment: Fragment,title:String){
        mFragments?.add(fragment)
        mTitles?.add(title)
        notifyDataSetChanged()
    }
    fun delItems(position: Int){
        mFragments?.removeAt(position)
        mTitles?.removeAt(position)
        notifyDataSetChanged()
    }
    fun delItems(title: String):Int{
        val indexOf = mTitles?.indexOf(title)
        if(indexOf!=-1){
            delItems(indexOf!!)
        }
        return indexOf
    }
    fun swapItems(fromPags : Int,toPags:Int){
        Collections.swap(mTitles, fromPags, toPags)
        Collections.swap(mFragments, fromPags, toPags)
        notifyDataSetChanged()
    }
}