package com.example.happyghost.showtimeforkotlin.base


import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
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

    @Inject
    var mPresenter : T? = null

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

}
