package com.example.happyghost.showtimeforkotlin.base


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.wegit.EmptyErrLayout
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity


import kotlinx.android.synthetic.main.layout_comment_empty.view.*
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/8/23.
 * @description
 */

abstract class BaseActivity<T : IBasePresenter>() : RxAppCompatActivity() ,IBaseView {


    @Inject lateinit var mPresenter : T

    var mEmptyComment: EmptyErrLayout? = null
        get() = find<EmptyErrLayout>(R.id.empty_comment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        initInjector()
        initView()
        upDataView()
    }

    abstract fun upDataView()

    abstract fun initView()

    abstract fun initInjector()

    abstract fun  getContentView() :Int

    override fun showLoading() {
//        !! 操作符这会返回一个非空的 值 如果 mEmptyComment  为空，就会抛出一个 NPE 异常：
//        mEmptyComment!!.setEmptyStatus(1)
        mEmptyComment?.setEmptyStatus(mEmptyComment?.STATUS_LOADING!!)
    }

    override fun hideLoading() {
        mEmptyComment?.setEmptyStatus(mEmptyComment?.STATUS_HIDE!!)
    }

    override fun showNetError(onReTryListener: EmptyErrLayout.OnReTryListener) {
        mEmptyComment?.setEmptyStatus(mEmptyComment?.STATUS_NO_DATA!!)
        mEmptyComment?.setOnRetryListener(onReTryListener)
    }

    override fun <T> bindToLife(): LifecycleTransformer<T> {
        return this.bindToLifecycle()
    }

    fun initActionBar(toolbar: Toolbar, string: String, isShowHomeEnable:Boolean){
        toolbar.title=string
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(isShowHomeEnable)
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId==android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    //vararg 表示参数个数可变，多参数
    fun gone(vararg views:View){
        for(view in views){
             view?.visibility=View.GONE
        }
    }
    fun visible(vararg views :View){
        for (view in views){
            view?.visibility=View.VISIBLE
        }
    }

    /**
     * 初始化第一个Fragment
     */
    fun addInitFragment(containerId : Int,fragment :Fragment,string: String){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.add(containerId,fragment,string)
        transaction.commit()
    }
    /**
     * 替换一个Fragment
     */
    fun replaceFragment(containerId: Int,fragment: Fragment,string: String){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.replace(containerId,fragment,string)
        transaction.commit()
    }
    /**
     * 显示下一个Fragment,隐藏当前Fragment,并且加入回退栈，视图不会销毁
     */
    fun addFragmentWithTag(containerId: Int,oldFragment: Fragment,fragment: Fragment,string: String){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.addToBackStack(string)
        transaction.hide(oldFragment)
        transaction.add(containerId,fragment,string)
        transaction.commit()
    }
    /**
     * 显示下一个Fragment,销毁shitu，并且加入回退栈（点击Back,则回到上一个Fragment）
     */
    fun replaceFragmentWithTag(containerId: Int,oldFragment: Fragment,fragment: Fragment,string: String){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.addToBackStack(string)
        transaction.replace(containerId,fragment,string)
        transaction.commit()
    }
}
