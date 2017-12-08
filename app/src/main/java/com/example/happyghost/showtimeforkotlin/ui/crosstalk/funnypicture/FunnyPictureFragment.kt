package com.example.happyghost.showtimeforkotlin.ui.crosstalk.funnypicture

import android.support.v4.app.Fragment
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter

/**
 * @author Zhao Chenping
 * @creat 2017/12/8.
 * @description
 */
class FunnyPictureFragment: BaseFragment<IBasePresenter>() {
    override fun upDataView() {

    }

    override fun initView(mRootView: View?) {

    }

    override fun initInject() {

    }

    override fun getFragmentLayout(): Int = R.layout.fragment_layout_test
    companion object {
        fun instance():Fragment = FunnyPictureFragment()
    }
}