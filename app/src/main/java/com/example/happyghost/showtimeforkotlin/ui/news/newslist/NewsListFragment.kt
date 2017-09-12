package com.example.happyghost.showtimeforkotlin.ui.news.newlist

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.daimajia.slider.library.SliderLayout
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.NewsListAdapter
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.bean.NewsInfo
import com.example.happyghost.showtimeforkotlin.bean.NewsMultiItem
import com.example.happyghost.showtimeforkotlin.inject.component.DaggerNewsListComponent
import com.example.happyghost.showtimeforkotlin.inject.module.NewsListModule
import com.example.happyghost.showtimeforkotlin.ui.news.newslist.INewsListView
import com.example.happyghost.showtimeforkotlin.ui.news.newslist.NewsListPresenter
import com.example.happyghost.showtimeforkotlin.utils.DefIconFactory
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import com.example.happyghost.showtimeforkotlin.utils.SliderHelper
import kotlinx.android.synthetic.main.fragment_list_layout.*
import org.jetbrains.anko.find

/**
 * @author Zhao Chenping
 * @creat 2017/8/30.
 * @description
 */
class NewsListFragment : BaseFragment<NewsListPresenter>(), INewsListView {
//    @Inject lateinit var mAdapter : NewsListAdapter
    lateinit var mAdapter:NewsListAdapter
    var dataList:ArrayList<NewsMultiItem> = ArrayList()
    override fun loadData(data: List<NewsMultiItem>) {
        mAdapter.replaceData(data)

    }

    override fun loadMoreData(moreData: List<NewsMultiItem>) {
        mAdapter.loadMoreComplete()
        mAdapter.addData(moreData)
    }

    override fun loadNoData() {

        val inflate = View.inflate(mContext, R.layout.item_no_data_layout, null)
        val imageView = inflate.find<ImageView>(R.id.iv_noData)
        ImageLoader.loadImageFromRes(this!!.mContext!!,DefIconFactory.provideNoDataIcon(),imageView)
        mAdapter.addFooterView(inflate)
        smart_refresh.setLoadmoreFinished(true)
    }

    override fun loadAdData(newsBean: NewsInfo) {
         var slideView = View.inflate(mContext, R.layout.item_head_addata_layout, null)
        val sliderLayout = slideView!!.find<SliderLayout>(R.id.slider_ads)
        SliderHelper.initAdSlider(mContext, sliderLayout, newsBean)
        if (mAdapter.headerLayout != null) {
            mAdapter.removeAllHeaderView()
            mAdapter.addHeaderView(slideView)
        } else {
            mAdapter.addHeaderView(slideView)
        }
    }

    override fun upDataView() {
         mPresenter.getData()
    }

    override fun initView(mRootView: View?) {
//        dataList = ArrayList()
        mAdapter = NewsListAdapter(dataList)
        val recyclerView = mRootView!!.find<RecyclerView>(R.id.recyclerview)
        mAdapter.openLoadAnimation()
        RecyclerViewHelper.initRecycleViewV(mContext,recyclerView,mAdapter,true)
    }



    override fun initInject() {
        val keyID = arguments.getString(NEWS_TYPE_KEY)
         DaggerNewsListComponent.builder()
                 .applicationComponent(getAppComponent())
                 .newsListModule(NewsListModule(this,keyID))
                 .build()
                 .inject(this)
    }

    override fun getFragmentLayout(): Int {
         return R.layout.fragment_list_layout
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