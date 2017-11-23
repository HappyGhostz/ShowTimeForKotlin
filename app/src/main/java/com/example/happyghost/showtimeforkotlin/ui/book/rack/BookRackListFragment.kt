package com.example.happyghost.showtimeforkotlin.ui.book.rack

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.RxBus.event.DownloadEvent
import com.example.happyghost.showtimeforkotlin.RxBus.event.LocalBookEvent
import com.example.happyghost.showtimeforkotlin.RxBus.event.ReadEvent
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.BookRackAdapter
import com.example.happyghost.showtimeforkotlin.bean.bookdate.Recommend
import com.example.happyghost.showtimeforkotlin.downloadservice.DownloadBookService
import com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent.DaggerBookRackComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.BookRackMoudle
import com.example.happyghost.showtimeforkotlin.loacaldao.LocalBookInfo
import com.example.happyghost.showtimeforkotlin.ui.base.BaseFragment
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.BookDetailInfoActivity
import com.example.happyghost.showtimeforkotlin.ui.book.read.ReadActivity
import com.example.happyghost.showtimeforkotlin.utils.*
import io.reactivex.functions.Consumer
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.selector
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.uiThread
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
        mPresenter.registerRxBus(LocalBookEvent::class.java, Consumer { t -> handlReBookRack(t) })
    }

    private fun handlReBookRack(t: LocalBookEvent) {
        if(t.isLocal){
            mPresenter.getData()
        }
    }

    private fun handlInsertBookRack(t: ReadEvent) {
        if(t.mIsInsert&&t.mBookBean!=null){
            mPresenter.insertBook(t.mBookBean)

        }
        if(t.mIsFromDetial){
            mPresenter.getData()
        }else{
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
                            loadLocalBookList(mPresenter.queryAll())
                            toast("置顶成功！")
                        }
                    }
                    1-> BookDetailInfoActivity.open(mContext!!,recommendBooks._id!!)
                    2-> {
                        if(recommendBooks.isFromSD){
                            doAsync {
                                val chapterAll = mPresenter.queryChapterAll(recommendBooks._id!!)
                               uiThread {
                                   DownloadBookService.post(DownloadEvent(recommendBooks._id!!, chapterAll, 1, chapterAll.size))
                                   toast("正在缓存")
                               }
                            }
                        }
                    }
                    3-> {
                        val progressDialog = ProgressDialog(mContext)
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                        progressDialog.setMessage("请稍后,正在删除...")
                        progressDialog.setCancelable(false)
                        progressDialog.show()
                        doAsync {
                            mPresenter.deleteBook(recommendBooks)
                            mPresenter.deleteBookChapters(recommendBooks._id!!)
                            FileUtils.deleteDir(recommendBooks._id!!)
                            uiThread {
                                adapter.remove(position)
                                adapter.notifyDataSetChanged()
                                progressDialog.dismiss()
                                toast("删除成功！")
                            }
                        }
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