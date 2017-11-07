package com.example.happyghost.showtimeforkotlin.ui.book.rack

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.RxBus.event.ReadEvent
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.BookRackAdapter
import com.example.happyghost.showtimeforkotlin.bean.bookdata.Recommend
import com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent.DaggerBookRackComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookRackMoudle
import com.example.happyghost.showtimeforkotlin.loacaldao.LocalBookInfo
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.ui.book.read.ReadActivity
import com.example.happyghost.showtimeforkotlin.utils.BookTransformer
import com.example.happyghost.showtimeforkotlin.utils.DialogHelper
import com.example.happyghost.showtimeforkotlin.utils.ListUtils
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import io.reactivex.functions.Consumer
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.selector
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

/**
 * @author Zhao Chenping
 * @creat 2017/9/15.
 * @description
 */
class BookRackListFragment : BaseFragment<BookRackPresent>(),IBookRackView {

    var  mLocalBooks = ArrayList<Recommend.RecommendBooks>()
    @Inject lateinit var adapter : BookRackAdapter
    override fun loadRecommendList(list: List<Recommend.RecommendBooks>) {
        if(!ListUtils.isEmpty(list)){
            adapter.replaceData(list)
            var title = "保存"
            var message = "是否将推荐列表保存到本地？"
            DialogHelper.creatAddLocalBookDialog(mContext!!,title,message,object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    mPresenter.insertBooks(list)
                    loadLocalBookList(mPresenter.queryAll())
                    toast("保存完成")
                    dialog?.dismiss()
                }
            })
        }
    }
    override fun loadLocalBookList(list: List<LocalBookInfo>) {
        mLocalBooks.clear()
        if(!ListUtils.isEmpty(list)){
            for (book in list){
                val recommendBooks = BookTransformer.localBookConvertRecommendBooks(book)
                mLocalBooks.add(recommendBooks)
            }
            adapter.replaceData(mLocalBooks)
        }
    }

    override fun upDataView() {
        mPresenter.getData()
    }

    override fun initView(mRootView: View?) {
        val recyclerView = mRootView!!.find<RecyclerView>(R.id.recyclerview)
        RecyclerViewHelper.initRecycleViewV(mContext,recyclerView,adapter,true)
        showItemHandle()
        showItemLongHandle()
        mPresenter.registerRxBus(ReadEvent::class.java, Consumer { t -> handlInsertBookRack(t) })
    }

    private fun handlInsertBookRack(t: ReadEvent) {
        if(t.mIsInsert&&t.mBookBean!=null){
            mPresenter.insertBook(t.mBookBean)
            mPresenter.getData()
        }
    }

    fun showItemHandle(){
        adapter.setOnItemClickListener { adapter, view, position ->
            val recommendBooks = adapter.getItem(position) as Recommend.RecommendBooks
            ReadActivity.open(mContext,recommendBooks)
        }
    }
    fun showItemLongHandle(){
        adapter.setOnItemLongClickListener { adapter, view, position ->
            val recommendBooks = adapter.getItem(position) as Recommend.RecommendBooks
            var  countries = listOf<String>()
            if(recommendBooks.isTop){
                countries = listOf("已置顶", "书籍详情", "缓存全本", "删除")
            }else{
                countries = listOf("置顶", "书籍详情", "缓存全本", "删除")
            }
            //这个selector要引入compile "org.jetbrains.anko:anko:$anko_version"依赖，或者有可能在一个单独的依赖中
            //只是我没找到
            selector(recommendBooks.title, countries) { dialogInterface, i ->
                when(i){
                    0-> {
                        if(TextUtils.equals(countries[0],"已置顶")){
                            toast("该书籍已置顶，请不要重复！")
                        }else{
                            mPresenter.isTop(recommendBooks)
//                        adapter.remove(position)
//                        adapter.addData(0,recommendBooks)
//                            adapter.notifyItemMoved(position,0)
                            loadLocalBookList(mPresenter.queryAll())
//                        adapter.notifyDataSetChanged()
                            toast("置顶成功！")
                        }
                    }
                    1-> toast("书籍详情")
                    2-> toast("缓存全本")
                    3-> {
                        mPresenter.deleteBook(recommendBooks)
                        adapter.remove(position)
                        adapter.notifyDataSetChanged()
                        toast("删除成功！")
                    }

                }


            }

            return@setOnItemLongClickListener true
        }
    }

    override fun initInject() {
        DaggerBookRackComponent.builder()
                .applicationComponent(getAppComponent())
                .bookRackMoudle(BookRackMoudle(this))
                .build()
                .inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.unregisterRxBus()
    }

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_list_layout
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