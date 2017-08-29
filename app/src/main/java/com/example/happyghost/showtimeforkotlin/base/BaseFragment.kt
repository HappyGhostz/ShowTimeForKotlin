package com.example.happyghost.showtimeforkotlin.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.components.support.RxFragment
import kotlinx.android.synthetic.main.layout_comment_empty.*
import kotlinx.android.synthetic.main.layout_comment_empty.view.*
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/8/26.
 * @description
 */
abstract class BaseFragment<T : IBasePresenter> : RxFragment() ,IBaseView{


    @Inject
    protected var mPresenter: T? = null




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

    override fun showNetError(onReTryListener: EmptyErrLayout.OnReTryListener) {
        empty_comment.setEmptyStatus(empty_comment.STATUS_NO_DATA)
        empty_comment.setOnRetryListener(onReTryListener)
    }

    override fun hideLoading() {
        empty_comment.hide()
    }

    override fun showLoading() {
        empty_comment.setEmptyStatus(empty_comment.STATUS_LOADING)
    }

    override fun <T> bindToLife(): LifecycleTransformer<T> {
        return  this.bindToLifecycle<T>()
    }

    /**
     * 初始化Toolbar
     */
    fun initToolBar(toolbar: Toolbar,string: String,isHomeForUse : Boolean){
        var baseActivity = activity as BaseActivity<T>
        baseActivity.initActionBar(toolbar,string,isHomeForUse)

    }

    abstract fun upDataView()

    abstract fun initView()

    abstract fun initInject()

    abstract fun getFragmentLayout(): Int
}