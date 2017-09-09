package com.example.happyghost.showtimeforkotlin.ui.video

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.happyghost.showtimeforkotlin.R

/**
 * @author Zhao Chenping
 * @creat 2017/8/28.
 * @description
 */
class VideoMainFragment :Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_layout_test, null)
        return view
    }
}