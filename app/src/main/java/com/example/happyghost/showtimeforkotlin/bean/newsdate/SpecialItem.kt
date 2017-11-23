package com.example.happyghost.showtimeforkotlin.bean.newsdate

import com.chad.library.adapter.base.entity.SectionEntity

/**
 * @author Zhao Chenping
 * @creat 2017/9/9.
 * @description
 */
class SpecialItem : SectionEntity<NewsItemInfo> {

    constructor(isHeader: Boolean, header: String) : super(isHeader, header) {}

    constructor(newsItemBean: NewsItemInfo) : super(newsItemBean) {}
}