package com.example.happyghost.showtimeforkotlin.ui.crosstalk.funnypicture

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.crossadapter.FunnyPictureAdapter
import com.example.happyghost.showtimeforkotlin.bean.crossdate.FunnyPictureDate
import com.example.happyghost.showtimeforkotlin.inject.component.crosscomponent.DaggerFunnyPictureComponent
import com.example.happyghost.showtimeforkotlin.inject.module.crossmodule.FunnyPictureModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/12/8.
 * @description
 */
class FunnyPictureFragment: BaseFragment<FunnyPicturePresenter>(),IFunnyPictureView {
    @Inject lateinit var mAdapter:FunnyPictureAdapter
    override fun loadFunnyPictureDate(date: FunnyPictureDate) {
        mAdapter.replaceData(date.data?.data!!)
    }

    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView(mRootView: View?) {
        val recyclerView = mRootView!!.find<RecyclerView>(R.id.recyclerview)
        RecyclerViewHelper.initRecycleViewV(mContext,recyclerView,mAdapter,true)
    }

    override fun initInject() {
        DaggerFunnyPictureComponent.builder()
                .applicationComponent(getAppComponent())
                .funnyPictureModule(FunnyPictureModule(this))
                .build()
                .inject(this)
    }

    override fun getFragmentLayout(): Int = R.layout.fragment_list_layout
    companion object {
        fun instance():Fragment = FunnyPictureFragment()
    }
}