package com.example.happyghost.showtimeforkotlin.news.newlist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.happyghost.showtimeforkotlin.R
import kotlinx.android.synthetic.main.fragment_layout_test.*

/**
 * @author Zhao Chenping
 * @creat 2017/8/30.
 * @description
 */
class NewsListFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_layout_test, null)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var key = arguments.getString(NEWS_TYPE_KEY)
        test_fragment.setText(key)
    }

     companion object {
         val NEWS_TYPE_KEY: String = "newstypekey"
         fun newInstance(newsId: String): NewsListFragment {
             val fragment = NewsListFragment()
             val bundle = Bundle()
             bundle.putString(NEWS_TYPE_KEY, newsId)
             fragment.arguments = bundle
             return fragment
         }
     }

}