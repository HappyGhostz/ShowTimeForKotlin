package com.example.happyghost.showtimeforkotlin.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * @author Zhao Chenping
 * @creat 2017/8/26.
 * @description
 */
abstract class BaseFragment : RxFragment() ,IBaseView{
    var mContext : Context? = null
    var mRootView : View? = null
    var mIsMulti :Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(mRootView==null){
            mRootView = inflater?.inflate(getFragmentLayout(), null)
            initInject()
            initView()
        }
        val parent = mRootView?.parent as? ViewGroup
        if(parent!=null){
            parent.removeView(mRootView)
        }

        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(userVisibleHint&&mRootView!=null&&!mIsMulti){
            mIsMulti=true
            upDataView()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if(isVisibleToUser&&isVisibleToUser&&mRootView!=null&&!mIsMulti){
            mIsMulti=true
            upDataView()
        }else{
            super.setUserVisibleHint(isVisibleToUser)
        }


    }

    abstract fun upDataView()

    abstract fun initView()

    abstract fun initInject()

    abstract fun getFragmentLayout(): Int
}