package com.example.happyghost.showtimeforkotlin.inject.component.bookcomponent

import com.example.happyghost.showtimeforkotlin.inject.PerActivity
import com.example.happyghost.showtimeforkotlin.inject.component.ApplicationComponent
import com.example.happyghost.showtimeforkotlin.inject.module.bookmodule.SubjectBookListDetailModule
import com.example.happyghost.showtimeforkotlin.ui.book.bookdetail.subject.SubjectBookListDetailActivity
import dagger.Component

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class),modules = arrayOf(SubjectBookListDetailModule::class))
interface SubjectBookListDetailComponent {
    fun inject(subjectBookListDetailActivity: SubjectBookListDetailActivity)
}