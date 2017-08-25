package com.example.happyghost.showtimeforkotlin.wegit

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View

import android.widget.FrameLayout
import com.example.happyghost.showtimeforkotlin.R
import com.github.ybq.android.spinkit.SpinKitView
import kotlinx.android.synthetic.main.layout_empty_err.view.*
import org.jetbrains.anko.find


/**
 * @author Zhao Chenping
 * @creat 2017/8/23.
 * @description
 */
class EmptyErrLayout : FrameLayout {
    var mBackColor:Int = 0
    val STATUS_LOADING : Int =1
    val STATUS_HIDE : Int =2
    val STATUS_NO_DATA:Int =3
    var mEmptyStatus=STATUS_LOADING
    lateinit var mEmptyContainer:FrameLayout
    lateinit var mEmptyLayout :FrameLayout
    lateinit var mEmptyLoading:SpinKitView

    constructor(context: Context):this (context,null){

    }

    constructor(context: Context,atts: AttributeSet?) : super (context,atts){
        init(context,atts)
    }

    /**
     * 初始化
     */
    fun init(context: Context, atts: AttributeSet?){
        val typedArray = context.obtainStyledAttributes(atts, R.styleable.EmptyLayout)
        try {
             mBackColor = typedArray.getColor(R.styleable.EmptyLayout_background_color, Color.WHITE)
        }finally {
            typedArray.recycle()
        }
        View.inflate(context, R.layout.layout_empty_err,null)
        //这是ANKO框架提供的fiandViewByID
        mEmptyContainer = find<FrameLayout>(R.id.rl_empty_container)
        mEmptyLayout = find<FrameLayout>(R.id.framlayout_emptay)
        mEmptyLoading = find<SpinKitView>(R.id.empty_loading)
        mEmptyLayout.setBackgroundColor(mBackColor)
        //这是Kotlin插件提供的直接使用id，但必须手动import :import kotlinx.android.synthetic.main.layout_empty_err.view.*
        //这里因为习惯没有用，以后改
//        rl_empty_container.setBackgroundColor(mBackColor)
//        setOnClickListener({mEmptyContainer->mOnRetryListener.onReTry()})
        mEmptyContainer.setOnClickListener({mOnRetryListener.onReTry()})
        switchShowView()


    }

    /**
     * 设置视图
     */
     fun switchShowView(){
//        kotlin版的Switch(),但功能更加强大
        when(mEmptyStatus){
            STATUS_LOADING->{//lambdas
                visibility=View.VISIBLE
                mEmptyContainer.visibility= View.GONE
                mEmptyLoading.visibility= View.VISIBLE
            }
            STATUS_NO_DATA ->{
                visibility=View.VISIBLE
                mEmptyContainer.visibility=View.VISIBLE
                mEmptyLoading.visibility=View.GONE
            }
            STATUS_HIDE->visibility=View.GONE
        }
    }
    /**
     * 隐藏视图
     */
    fun hide(){
        mEmptyStatus=STATUS_HIDE
        switchShowView()
    }
    /**
     * 设置状态
     */
    fun setEmptyStatus(empetyStatus :Int){
        mEmptyStatus = empetyStatus
        switchShowView()
    }
    /**
     * 获取状态
     */
    fun getEmptyStatus() : Int{
        return mEmptyStatus
    }

    /**
     * 重置监听
     */
    lateinit var mOnRetryListener:OnReTryListener
    public interface OnReTryListener{
        fun onReTry()
    }

    fun setOnRetryListener(onReTryListener: OnReTryListener){
        mOnRetryListener = onReTryListener
    }




}




