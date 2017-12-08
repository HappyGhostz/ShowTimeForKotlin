package com.example.happyghost.showtimeforkotlin.ui.crosstalk.crossfragment

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.CrossTalkAdapter
import com.example.happyghost.showtimeforkotlin.bean.CrossTalkDate
import com.example.happyghost.showtimeforkotlin.inject.component.DaggerCrossTalkComponent
import com.example.happyghost.showtimeforkotlin.inject.module.CrossTalkModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/12/8.
 * @description
 */
class CrossTalkFragment: BaseFragment<CrossTalkPresenter>(),ICrossTalkView {
    @Inject lateinit var mAdapter: CrossTalkAdapter
    override fun showCrossTalk(data: CrossTalkDate) {
        mAdapter.replaceData(data.data?.data!!)
    }

    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView(mRootView: View?) {
        val recyclerView = mRootView!!.find<RecyclerView>(R.id.recyclerview)
        RecyclerViewHelper.initRecycleViewV(mContext,recyclerView,mAdapter,true)
    }

    override fun initInject() {
        DaggerCrossTalkComponent.builder()
                .applicationComponent(getAppComponent())
                .crossTalkModule(CrossTalkModule(this))
                .build()
                .inject(this)
    }

    override fun getFragmentLayout(): Int = R.layout.fragment_list_layout
    companion object {
        fun instance():Fragment{
            var fragment = CrossTalkFragment()
            return fragment
        }
    }
}