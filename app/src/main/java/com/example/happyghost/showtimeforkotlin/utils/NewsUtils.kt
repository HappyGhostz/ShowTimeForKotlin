package com.example.happyghost.showtimeforkotlin.utils

import com.example.happyghost.showtimeforkotlin.bean.NewsInfo

/**
 * @author Zhao Chenping
 * @creat 2017/9/6.
 * @description
 */
class NewsUtils {
    companion object {
        // 新闻列表头部
        private val HAS_HEAD = 1
        // 新闻ID长度
        private val NEWS_ID_LENGTH = 16
        // 新闻ID前缀，eg：BV9KHEMS0001124J
        private val NEWS_ID_PREFIX = "BV"
        // 新闻ID后缀，eg：http://news.163.com/17/0116/16/CATR1P580001875N.html
        private val NEWS_ID_SUFFIX = ".html"

        private val NEWS_ITEM_SPECIAL = "special"
        private val NEWS_ITEM_PHOTO_SET = "photoset"
        /**
         * 判断是否为广告
         *
         * @param newsBean
         * @return
         */
        fun isAbNews(newsBean: NewsInfo):Boolean{
            return (newsBean.hasHead== HAS_HEAD&&newsBean.ads!=null&& newsBean.ads!!.size>1)
        }
        /**
         * 从超链接中取出新闻ID
         *
         * @param url url
         * @return 新闻ID
         */
        fun clipNewsIdFromUrl(url:String): String? {
            var typeId: String? = null
            var indexOf = url.indexOf(NEWS_ID_PREFIX)
            if(indexOf!=-1){
                typeId = url.substring(indexOf,indexOf+ NEWS_ID_LENGTH)
            }else if(url.endsWith(NEWS_ID_SUFFIX)){
                indexOf = url.length- NEWS_ID_LENGTH- NEWS_ID_SUFFIX.length
                typeId = url.substring(indexOf,indexOf+ NEWS_ID_LENGTH)
            }
            return typeId
        }
        /**
         * 判断新闻类型
         *
         * @param skipType
         * @return
         */
        fun isNewsSpecial(skipType:String?):Boolean{
            return NEWS_ITEM_SPECIAL.equals(skipType)
        }
        fun isNewsPhotoSet(skipType: String?):Boolean{
            return NEWS_ITEM_PHOTO_SET.equals(skipType)
        }
    }



}