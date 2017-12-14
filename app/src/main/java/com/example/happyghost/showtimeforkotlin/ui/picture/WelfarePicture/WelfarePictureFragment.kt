package com.example.happyghost.showtimeforkotlin.ui.picture.WelfarePicture

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.pictureadapter.WelfPictureAdapter
import com.example.happyghost.showtimeforkotlin.bean.picturedate.WelfarePhotoList
import com.example.happyghost.showtimeforkotlin.inject.component.picturecomponent.DaggerWelfarePictureComponent
import com.example.happyghost.showtimeforkotlin.inject.module.picture.WelfarePictureModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/12/12.
 * @description
 */
class WelfarePictureFragment: BaseFragment<WelfarePicturePresenter>(),IWelfView {
    @Inject lateinit var mAdapter: WelfPictureAdapter
    override fun showMoreWelfDate(welfDate: WelfarePhotoList) {
        mAdapter.loadMoreComplete()
        mAdapter.addData(welfDate.results!!)
    }

    override fun showWelfDate(welfDate: WelfarePhotoList) {
        mAdapter.replaceData(welfDate.results!!)
    }

    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView(mRootView: View?) {
        val recyclerView = mRootView!!.find<RecyclerView>(R.id.recyclerview)
        RecyclerViewHelper.initRecycleViewSV(mContext,recyclerView,mAdapter,2,true)
    }

    override fun initInject() {
       DaggerWelfarePictureComponent.builder()
               .applicationComponent(getAppComponent())
               .welfarePictureModule(WelfarePictureModule(this))
               .build()
               .inject(this)
    }

    override fun getFragmentLayout(): Int  = R.layout.fragment_list_layout
    companion object {
        fun instance():Fragment{
            val fragment = WelfarePictureFragment()
            return  fragment
        }
    }
}