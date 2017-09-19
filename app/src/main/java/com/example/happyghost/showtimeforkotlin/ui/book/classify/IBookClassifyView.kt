package com.example.happyghost.showtimeforkotlin.ui.book.classify

import com.example.happyghost.showtimeforkotlin.bean.bookdata.CategoryList
import com.example.happyghost.showtimeforkotlin.ui.base.IBaseView

/**
 * @author Zhao Chenping
 * @creat 2017/9/19.
 * @description
 */
interface IBookClassifyView:IBaseView {
   fun LoadMaleCategoryList(data: List<CategoryList.MaleBean>)
    fun LoadFemaleCategoryList(data: List<CategoryList.MaleBean>)
}