package com.example.happyghost.showtimeforkotlin.inject.module.bookmodule

import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.SubjectBookListAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.subject.SubjectBookListDetailActivity
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.subject.SubjectBookListDetailPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
@Module
class SubjectBookListDetailModule(subjectBookListDetailActivity: SubjectBookListDetailActivity, mBookId: String?) {
    var view = subjectBookListDetailActivity
    var bookid = mBookId
    @PerActivity
    @Provides
    fun providesPresenter():SubjectBookListDetailPresenter= SubjectBookListDetailPresenter(view,bookid)
    @PerActivity
    @Provides
    fun providesAdapter():SubjectBookListAdapter=SubjectBookListAdapter()

}