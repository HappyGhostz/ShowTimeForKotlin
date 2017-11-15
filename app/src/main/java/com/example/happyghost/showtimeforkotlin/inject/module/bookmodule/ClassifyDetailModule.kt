package com.example.happyghost.showtimeforkotlin.inject.module.bookmodule

import com.example.happyghost.showtimeforkotlin.adapter.bookadapter.ClassifyDetailAdapter
import com.example.happyghost.showtimeforkotlin.inject.PerFragment
import com.example.happyghost.showtimeforkotlin.ui.book.classify.classifydetail.ClassifyDetailFragment
import com.example.happyghost.showtimeforkotlin.ui.book.classify.classifydetail.ClassifyDetailPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Zhao Chenping
 * @creat 2017/11/15.
 * @description
 */
@Module
class ClassifyDetailModule(classifyDetailFragment: ClassifyDetailFragment, mSex: String, mName: String, mType: String) {
    var view= classifyDetailFragment
    var gender = mSex
    var name = mName
    var type = mType
    @PerFragment
    @Provides
    fun providesClassifyPresenter():ClassifyDetailPresenter= ClassifyDetailPresenter(view,gender,name,type)
    @PerFragment
    @Provides
    fun provideClassifyAdapter():ClassifyDetailAdapter = ClassifyDetailAdapter()
}