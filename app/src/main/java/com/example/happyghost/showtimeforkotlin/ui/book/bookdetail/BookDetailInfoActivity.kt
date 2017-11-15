package com.example.happyghost.showtimeforkotlin.ui.book.bookdetail

import android.app.Activity
import android.content.Context
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.ui.base.BaseActivity
import org.jetbrains.anko.startActivity

/**
 * @author Zhao Chenping
 * @creat 2017/11/15.
 * @description
 */
class BookDetailInfoActivity: BaseActivity<BookDetailPresent>() {
    override fun upDataView() {

    }

    override fun initView() {

    }

    override fun initInjector() {

    }

    override fun getContentView(): Int {
        return R.layout.activity_book_detail_info
    }
    companion object {
        var BOOK_INFO_ID:String = "bookid"
        fun open(context: Context, id: String) {
            //context.startActivity<BookDetailInfoActivity>(BOOK_INFO_ID to id)括号里的参数（id）不能为空的参数
            //即不能为open(context: Context, id: String?)这样的形式，必须把‘？’号去掉才可以使用下面的startActivity
            context.startActivity<BookDetailInfoActivity>(BOOK_INFO_ID to id)
            (context as Activity).overridePendingTransition(R.anim.fade_entry, R.anim.fade_exit)
        }
    }
}