package com.example.happyghost.showtimeforkotlin.ui.music.rank

import android.support.v4.app.Fragment
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter

/**
 * Created by e445 on 2017/11/23.
 */
class MusicRankFragment: BaseFragment<IBasePresenter>() {
    override fun upDataView() {

    }

    override fun initView(mRootView: View?) {

    }

    override fun initInject() {

    }

    override fun getFragmentLayout(): Int  = R.layout.fragment_layout_test
    companion object {
        fun lunch(): Fragment {
            val fragment = MusicRankFragment()
            return fragment
        }
    }
}