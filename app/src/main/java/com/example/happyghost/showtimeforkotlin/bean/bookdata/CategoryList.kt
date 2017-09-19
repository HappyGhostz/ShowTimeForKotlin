package com.example.happyghost.showtimeforkotlin.bean.bookdata



/**
 * @author Zhao Chenping
 * @creat 2017/9/19.
 * @description
 */
class CategoryList {
    /**
     * male : [{"name":"玄幻","bookCount":188244},{"name":"奇幻","bookCount":24183}]
     * ok : true
     */

    var male: List<MaleBean>? = null
    /**
     * name : 古代言情
     * bookCount : 125103
     */

    var female: List<MaleBean>? = null

    class MaleBean  {
        var name: String? = null
        var bookCount: Int = 0
    }
}