package com.example.happyghost.showtimeforkotlin.utils

import com.example.happyghost.showtimeforkotlin.R
import java.util.*

/**
 * @author Zhao Chenping
 * @creat 2017/9/7.
 * @description
 */
class DefIconFactory() {
    companion object {
        private val DEF_ICON_ID = intArrayOf(R.mipmap.ic_default_1, R.mipmap.ic_default_2,
                R.mipmap.ic_default_3, R.mipmap.ic_default_4, R.mipmap.ic_default_5)
        private var NO_DATAT_ID= intArrayOf(R.mipmap.no_data_one,R.mipmap.no_data_two,R.mipmap.no_data_three,
                R.mipmap.no_data_four,R.mipmap.no_data_five,R.mipmap.no_data_sex,
                R.mipmap.no_data_seven,R.mipmap.no_data_eight,R.mipmap.no_data_nine,
                R.mipmap.no_data_ten)

        private val sRandom = Random()



        fun provideIcon(): Int {
            val index = sRandom.nextInt(DEF_ICON_ID.size)
            return DEF_ICON_ID[index]
        }
        fun provideNoDataIcon():Int{
            val index = sRandom.nextInt(NO_DATAT_ID.size)
            return NO_DATAT_ID[index]
        }
    }
}