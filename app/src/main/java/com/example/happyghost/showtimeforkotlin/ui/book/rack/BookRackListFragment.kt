package com.example.happyghost.showtimeforkotlin.ui.book.rack

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.happyghost.showtimeforkotlin.R

/**
 * @author Zhao Chenping
 * @creat 2017/9/15.
 * @description
 */
class BookRackListFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_layout_test, null)
        return view
    }
    companion object {
        var BOOK_RACK_TYPE:String="bookracktype"
        fun lunch(type:String):Fragment{
            val fragment = BookRackListFragment()
            val bundle = Bundle()
            bundle.putString(BOOK_RACK_TYPE,type)
            fragment.arguments = bundle
            return fragment
        }
    }

}