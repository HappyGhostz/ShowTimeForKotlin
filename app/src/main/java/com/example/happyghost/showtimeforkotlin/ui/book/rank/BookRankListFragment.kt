package com.example.happyghost.showtimeforkotlin.ui.book.rank

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.BookFemaleRankAdapter
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.BookMaleRankAdapter
import com.example.happyghost.showtimeforkotlin.bean.bookdate.RankingListBean
import com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent.DaggerBookRankComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookRankListModule
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import org.jetbrains.anko.find

/**
 * @author Zhao Chenping
 * @creat 2017/9/15.
 * @description
 */
class BookRankListFragment: BaseFragment<BookRankPresent>(),IBookRankView {
//    @Inject
    lateinit var maleAdapter:BookMaleRankAdapter
//    @Inject
    lateinit var femaleAdapter:BookFemaleRankAdapter
    var groups = ArrayList<RankingListBean.MaleBean>()
    var children = ArrayList<RankingListBean.MaleBean>()
    var femalegroups = ArrayList<RankingListBean.MaleBean>()
    var femalechildren = ArrayList<RankingListBean.MaleBean>()
    var isExpend:Boolean  =false
    override fun LoadRankList(rankingList: RankingListBean) {
        val male = rankingList.male
        val female = rankingList.female
        upDataMale(male)
        upDataFemale(female)
    }
    fun upDataMale(male: List<RankingListBean.MaleBean>?) {
        male?.forEach {
            if(!it.collapse){
                groups.add(it)
            }else{
                children.add(it)
            }
        }
        maleAdapter.replaceData(groups)
        maleAdapter.addData(RankingListBean.MaleBean("其他火热排行榜"))
    }
    fun upDataFemale(female: List<RankingListBean.MaleBean>?) {
        female?.forEach {
            if(!it.collapse){
                femalegroups.add(it)
            }else{
                femalechildren.add(it)
            }
        }
        femaleAdapter.replaceData(femalegroups)
        femaleAdapter.addData(RankingListBean.MaleBean("其他火热排行榜"))
    }

    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView(mRootView: View?) {
        val maleView = mRootView!!.find<RecyclerView>(R.id.rv_male_list)
        var femaleView = mRootView!!.find<RecyclerView>(R.id.rv_female_list)
        maleAdapter = BookMaleRankAdapter(groups,children)
        femaleAdapter = BookFemaleRankAdapter(femalegroups,femalechildren)
        maleAdapter.bindToRecyclerView(maleView)
        RecyclerViewHelper.initRecycleViewV(mContext,maleView,maleAdapter,true)
        RecyclerViewHelper.initRecycleViewV(mContext,femaleView,femaleAdapter,true)
    }

    override fun initInject() {
        DaggerBookRankComponent.builder()
                .applicationComponent(getAppComponent())
                .bookRankListModule(BookRankListModule(this))
                .build()
                .inject(this)
    }

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_book_rank_layout
    }

    companion object {
        var BOOK_RACK_TYPE:String="bookracktype"
        fun lunch(type:String):Fragment{
            val fragment = BookRankListFragment()
            val bundle = Bundle()
            bundle.putString(BOOK_RACK_TYPE,type)
            fragment.arguments = bundle
            return fragment
        }
    }
}