package com.example.happyghost.showtimeforkotlin.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.content.ContextCompat
import android.view.View
import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.R
import java.util.ArrayList

/**
 * @author Zhao Chenping
 * @creat 2017/9/30.
 * @description
 */
object ThemeManager {
    val NORMAL = 0
    val YELLOW = 1
    val GREEN = 2
    val LEATHER = 3
    val GRAY = 4
    val NIGHT = 5

    fun setReaderTheme(theme: Int, view: View) {
        when (theme) {
            NORMAL -> view.setBackgroundResource(R.drawable.theme_white_bg)
            YELLOW -> view.setBackgroundResource(R.drawable.theme_yellow_bg)
            GREEN -> view.setBackgroundResource(R.drawable.theme_green_bg)
            LEATHER -> view.setBackgroundResource(R.drawable.theme_leather_bg)
            GRAY -> view.setBackgroundResource(R.drawable.theme_gray_bg)
            NIGHT -> view.setBackgroundResource(R.drawable.theme_night_bg)
            else -> {
            }
        }
    }

    fun getThemeDrawable(theme: Int): Bitmap {
        var bmp = Bitmap.createBitmap(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight(), Bitmap.Config.ARGB_8888)
        when (theme) {
            NORMAL -> bmp.eraseColor(ContextCompat.getColor(AppApplication.instance.getContext(), R.color.read_theme_white))
            YELLOW -> bmp.eraseColor(ContextCompat.getColor(AppApplication.instance.getContext(), R.color.read_theme_yellow))
            GREEN -> bmp.eraseColor(ContextCompat.getColor(AppApplication.instance.getContext(), R.color.read_theme_green))
            LEATHER -> bmp = BitmapFactory.decodeResource(AppApplication.instance.getContext().getResources(), R.drawable.theme_leather_bg)
            GRAY -> bmp.eraseColor(ContextCompat.getColor(AppApplication.instance.getContext(), R.color.read_theme_gray))
            NIGHT -> bmp.eraseColor(ContextCompat.getColor(AppApplication.instance.getContext(), R.color.blackColor))
            else -> {
            }
        }
        return bmp
    }

    fun getReaderThemeData(curTheme: Int): List<ReadTheme> {
        val themes = intArrayOf(NORMAL, YELLOW, GREEN, LEATHER, GRAY, NIGHT)
        val list = ArrayList<ReadTheme>()
        var theme: ReadTheme
        for (i in themes.indices) {
            theme = ReadTheme()
            theme.theme = themes[i]
            list.add(theme)
        }
        return list
    }
}

public class ReadTheme {
    var theme: Int = 0
}
