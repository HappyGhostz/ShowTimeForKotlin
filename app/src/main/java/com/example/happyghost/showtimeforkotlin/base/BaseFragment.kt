package com.example.happyghost.showtimeforkotlin.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.components.support.RxFragment
import kotlinx.android.synthetic.main.fragment_list_layout.*
import kotlinx.android.synthetic.main.layout_comment_empty.*
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/8/26.
 * @description
 */
abstract class BaseFragment<T : IBasePresenter> : RxFragment() ,IBaseView{
    /**
     * @Inject
     * protected  var mPresenter: T？=null//这样注释是错误的 inject不支持私有属性
     */


    protected lateinit var mPresenter: T
       @Inject set



    var mContext : Context? = null
    var mRootView : View? = null
    var mIsMulti :Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }
    @Nullable
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(mRootView==null){
            mRootView = inflater?.inflate(getFragmentLayout(), null)
            initInject()
            //如果想在这个地方初始化控件，必须使用mRootView.find，而不能直接使用控件的名字搞事情
            //所以这里需传入mRootBiew
            initView(mRootView)
            initSmartRefresh()
        }
        val parent = mRootView?.parent as? ViewGroup
        if(parent!=null){
            parent.removeView(mRootView)
        }

        return mRootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(userVisibleHint&&mRootView!=null&&!mIsMulti){
            mIsMulti=true
            upDataView()
        }

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if(isVisibleToUser&&isVisible&&mRootView!=null&&!mIsMulti){
            mIsMulti=true
            upDataView()
        }else{
            super.setUserVisibleHint(isVisibleToUser)
        }


    }

    override fun showNetError(onReTryListener: EmptyErrLayout.OnReTryListener) {
        if(empty_comment!=null){
            empty_comment.setEmptyStatus(empty_comment.STATUS_NO_DATA)
            empty_comment.setOnRetryListener(onReTryListener)
            smart_refresh.isEnableRefresh=false
        }

    }

    override fun hideLoading() {
        if(empty_comment!=null){
            empty_comment?.hide()
            smart_refresh?.isEnableRefresh=true
        }
    }

    override fun showLoading() {
        if(empty_comment!=null){
            empty_comment?.setEmptyStatus(empty_comment.STATUS_LOADING)
            smart_refresh?.isEnableRefresh=false
        }


    }
    fun initSmartRefresh(){
        if(smart_refresh!=null){
            smart_refresh.setOnRefreshListener({
                upDataView()
                smart_refresh.finishRefresh(500)
            })
        }

//        smart_refresh?.setOnLoadmoreListener {
//            upDataView()
//            smart_refresh?.finishLoadmore(3000)
//        }
    }

    override fun <T> bindToLife(): LifecycleTransformer<T> {
        return  this.bindToLifecycle<T>()
    }
    /**
     * 获取 ApplicationComponent
     *
     * @return ApplicationComponent
     */
    protected fun getAppComponent(): ApplicationComponent? {
        return AppApplication.instance?.getAppComponent()
        //        return ((AndroidApplication) getApplication()).getAppComponent();
    }

    /**
     * 初始化Toolbar
     */
    fun initToolBar(toolbar: Toolbar,string: String,isHomeForUse : Boolean){
        var baseActivity = activity as BaseActivity<T>
        baseActivity.initActionBar(toolbar,string,isHomeForUse)

    }

    abstract fun upDataView()

    abstract fun initView(mRootView: View?)

    abstract fun initInject()

    abstract fun getFragmentLayout(): Int
}