package com.example.happyghost.showtimeforkotlin.ui.picture.beautypicture

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.pictureadapter.BeautyPictureAdapter
import com.example.happyghost.showtimeforkotlin.bean.picturedate.BeautyPicture
import com.example.happyghost.showtimeforkotlin.inject.component.picturecomponent.DaggerBeautyPictureComponent
import com.example.happyghost.showtimeforkotlin.inject.module.picture.BeautyPictureModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/12/12.
 * @description
 */
class BeautyPictureFragment: BaseFragment<BeautyPicturePresenter>(),IBeautyPictureView {
    @Inject lateinit var mAdapter: BeautyPictureAdapter
    override fun loadBeautyData(beauty: BeautyPicture) {
        mAdapter.replaceData(beauty.imgs!!)
    }

    override fun loadMoreBeautyData(beauty: BeautyPicture) {
        mAdapter.loadMoreComplete()
        mAdapter.addData(beauty.imgs!!)
    }

    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView(mRootView: View?) {
        val recyclerview = mRootView!!.find<RecyclerView>(R.id.recyclerview)
        RecyclerViewHelper.initRecycleViewSV(mContext,recyclerview,mAdapter,2,true)
    }

    override fun initInject() {
        DaggerBeautyPictureComponent.builder()
                .applicationComponent(getAppComponent())
                .beautyPictureModule(BeautyPictureModule(this))
                .build()
                .inject(this)
    }

    override fun getFragmentLayout(): Int= R.layout.fragment_list_layout
    companion object {
        fun instance(): Fragment {
            val fragment = BeautyPictureFragment()
            return fragment
        }
    }
}