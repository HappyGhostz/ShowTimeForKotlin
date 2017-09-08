package com.example.happyghost.showtimeforkotlin.utils

/**
 * @author Zhao Chenping
 * @creat 2017/9/7.
 * @description
 */
class ListUtils {
    companion object {
          fun <T>isEmpty(list: List<T>?):Boolean{
              return (list==null || list.size==0)
          }
    }
}