package com.example.happyghost.showtimeforkotlin.ui.book.search

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.BookRackAdapter
import com.example.happyghost.showtimeforkotlin.bean.bookdata.Recommend
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import com.example.happyghost.showtimeforkotlin.ui.base.IBasePresenter
import com.example.happyghost.showtimeforkotlin.utils.ConsTantUtils
import com.example.happyghost.showtimeforkotlin.utils.FileUtils
import com.example.happyghost.showtimeforkotlin.utils.RecyclerViewHelper
import kotlinx.android.synthetic.main.activity_scan_local_layout.*
import org.jetbrains.anko.startActivity
import java.util.ArrayList

/**
 * @author Zhao Chenping
 * @creat 2017/11/23.
 * @description
 */
class ScanLocalBookActivity:BaseActivity<IBasePresenter>() {
    lateinit var mAdapter:BookRackAdapter
    override fun upDataView() {
        queryFiles()
    }

    private fun queryFiles() {
        val projection = arrayOf(MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.SIZE)

        // cache
        val bookpath = FileUtils.createRootPath(this)

        // 查询后缀名为txt与pdf，并且不位于项目缓存中的文档
        val cursor = contentResolver.query(
                Uri.parse("content://media/external/file"),
                projection,
                MediaStore.Files.FileColumns.DATA + " not like ? and ("
                        + MediaStore.Files.FileColumns.DATA + " like ? or "
                        + MediaStore.Files.FileColumns.DATA + " like ? or "
                        + MediaStore.Files.FileColumns.DATA + " like ? or "
                        + MediaStore.Files.FileColumns.DATA + " like ? )",
                arrayOf("%$bookpath%", "%" + ConsTantUtils.SUFFIX_TXT, "%" + ConsTantUtils.SUFFIX_PDF, "%" + ConsTantUtils.SUFFIX_EPUB, "%" + ConsTantUtils.SUFFIX_CHM), null)

        if (cursor != null && cursor.moveToFirst()) {
            val idindex = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID)
            val dataindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA)
            val sizeindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE)
            val list = ArrayList<Recommend.RecommendBooks>()


            do {
                val path = cursor.getString(dataindex)

                val dot = path.lastIndexOf("/")
                var name = path.substring(dot + 1)
                if (name.lastIndexOf(".") > 0)
                    name = name.substring(0, name.lastIndexOf("."))

                val books = Recommend.RecommendBooks()
                books._id = name
                books.path = path
                books.title = name
                books.isFromSD = true
                books.lastChapter = FileUtils.formatFileSizeToString(cursor.getLong(sizeindex))

                list.add(books)
            } while (cursor.moveToNext())

            cursor.close()
            mAdapter.replaceData(list)
        } else {

        }
    }

    override fun initView() {
        initActionBar(common_toolbar,"本地书籍",true)
        mAdapter = BookRackAdapter(true)
        RecyclerViewHelper.initRecycleViewV(this,rvDisscussionComments,mAdapter,true)
    }

    override fun initInjector() {

    }

    override fun getContentView(): Int =R.layout.activity_scan_local_layout
    companion object {
        fun open(context: Context){
            context.startActivity<ScanLocalBookActivity>()
            (context as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }
}