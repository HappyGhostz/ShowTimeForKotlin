package com.example.happyghost.showtimeforkotlin.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.content.ContextCompat
import android.support.v4.util.LruCache
import android.view.View
import android.widget.ImageView
import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.R
import java.util.ArrayList

/**
 * @author Zhao Chenping
 * @creat 2017/9/30.
 * @description
 */
class ThemeManager {
    init {
        setLruCache()
    }

    companion object {
        val NORMAL = 0
        val YELLOW = 1
        val GREEN = 2
        val LEATHER = 3
        val GRAY = 4
        val NIGHT = 5

        fun setReaderTheme(theme: Int, view: ImageView) {
            when (theme) {
                NORMAL -> view.setImageResource(R.drawable.theme_white_bg)
                YELLOW -> view.setImageResource(R.drawable.theme_yellow_bg)
                GREEN -> view.setImageResource(R.drawable.theme_green_bg)
                LEATHER -> view.setImageBitmap(handlerImageRes(R.drawable.theme_leather_bg, view))
                GRAY -> view.setImageResource(R.drawable.theme_gray_bg)
                NIGHT -> view.setImageResource(R.drawable.theme_night_bg)
                else -> {
                }
            }
        }

        fun handlerImageRes(id: Int, view: ImageView): Bitmap {
            val resources = AppApplication.instance.getContext().resources
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeResource(resources, id, options)
            val imageHeight = options.outHeight
            val imageWidth = options.outWidth
            //默认压缩
            var inSampleSize = 4
            if (imageHeight > view.height || imageWidth > view.width) {
                // 计算出实际宽高和目标宽高的比率
                var heightRatio = Math.round(imageHeight.toFloat() / view.height.toFloat())
                var widthRatio = Math.round(imageWidth.toFloat() / view.width.toFloat())
                // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
                // 一定都会大于等于目标的宽和高。
                inSampleSize = Math.min(widthRatio, heightRatio)
                if (inSampleSize < 4) {
                    inSampleSize = 4
                }
            }
            options.inSampleSize = inSampleSize
            options.inJustDecodeBounds = false
            val bitmap = BitmapFactory.decodeResource(resources, id, options)
            return bitmap
        }

        //        fun setReaderTheme(theme: Int, view: ImageView) {
//            when (theme) {
//                NORMAL -> ImageLoader.loadImageFromRes(AppApplication.instance.getContext(),R.drawable.theme_white_bg,view)
//                YELLOW -> ImageLoader.loadImageFromRes(AppApplication.instance.getContext(),R.drawable.theme_yellow_bg,view)
//                GREEN -> ImageLoader.loadImageFromRes(AppApplication.instance.getContext(),R.drawable.theme_green_bg,view)
//                LEATHER -> ImageLoader.loadImageFromRes(AppApplication.instance.getContext(),R.drawable.theme_leather_bg,view)
//                GRAY -> ImageLoader.loadImageFromRes(AppApplication.instance.getContext(),R.drawable.theme_gray_bg,view)
//                NIGHT -> ImageLoader.loadImageFromRes(AppApplication.instance.getContext(),R.drawable.theme_night_bg,view)
//                else -> {
//                }
//            }
//        }
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
//            val bitmapFromMemCache = getBitmapFromMemCache(theme.toString() + "bmp")
//            if (bitmapFromMemCache != null && !bitmapFromMemCache.isRecycled) {
//                return bitmapFromMemCache
//            } else {BitmapFactory.decodeResource(AppApplication.instance.resources, R.drawable.theme_leather_bg)
                var bmp = Bitmap.createBitmap(ScreenUtils.getScreenWidth() / 16, ScreenUtils.getScreenHeight() / 16, Bitmap.Config.ARGB_8888)
//                addBitmapToMemoryCache(theme.toString() + "bmp", bmp)
                when (theme) {
                    NORMAL -> bmp.eraseColor(ContextCompat.getColor(AppApplication.instance.getContext(), R.color.read_theme_white))
                    YELLOW -> bmp.eraseColor(ContextCompat.getColor(AppApplication.instance.getContext(), R.color.read_theme_yellow))
                    GREEN -> bmp.eraseColor(ContextCompat.getColor(AppApplication.instance.getContext(), R.color.read_theme_green))
                    LEATHER -> bmp = getHandlePicture(R.drawable.theme_leather_bg, theme)
                    GRAY -> bmp.eraseColor(ContextCompat.getColor(AppApplication.instance.getContext(), R.color.read_theme_gray))
                    NIGHT -> bmp.eraseColor(ContextCompat.getColor(AppApplication.instance.getContext(), R.color.blackColor))
                    else -> {
                    }
                }
                return bmp
//            }
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


        //图片处理,防止OOM
        fun getHandlePicture(imgId: Int, theme: Int): Bitmap {
            val bitmapFromMemCache = getBitmapFromMemCache(imgId.toString()+"bmp")
            if (bitmapFromMemCache != null && !bitmapFromMemCache.isRecycled) {
                return bitmapFromMemCache
            } else {
                val resources = AppApplication.instance.getContext().resources
                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                BitmapFactory.decodeResource(resources, imgId, options)
                val imageHeight = options.outHeight
                val imageWidth = options.outWidth
                //默认压缩
                var inSampleSize = 4
                if (imageHeight > ScreenUtils.getScreenHeight() || imageWidth > ScreenUtils.getScreenWidth()) {
                    // 计算出实际宽高和目标宽高的比率
                    var heightRatio = Math.round(imageHeight.toFloat() / ScreenUtils.getScreenHeight().toFloat())
                    var widthRatio = Math.round(imageWidth.toFloat() / ScreenUtils.getScreenWidth().toFloat())
                    // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
                    // 一定都会大于等于目标的宽和高。
                    inSampleSize = Math.min(widthRatio, heightRatio)
                    if (inSampleSize < 4) {
                        inSampleSize = 4
                    }
                }
                options.inSampleSize = inSampleSize
                options.inJustDecodeBounds = false
                val bitmap = BitmapFactory.decodeResource(resources, imgId, options)
                addBitmapToMemoryCache(theme.toString() + "bmp", bitmap)
                return bitmap
            }
        }

        lateinit var mMemoryCache: LruCache<String, Bitmap>
        //lru缓存图片
        fun setLruCache() {
            // LruCache通过构造函数传入缓存值，以KB为单位。
            val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
            // 使用最大可用内存值的1/8作为缓存的大小。
            val cacheSize = maxMemory / 8
            mMemoryCache = object : LruCache<String, Bitmap>(cacheSize) {
                override fun sizeOf(key: String, bitmap: Bitmap): Int {
                    // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                    return bitmap.byteCount / 1024
                }
            }
        }

        fun addBitmapToMemoryCache(key: String, bitmap: Bitmap) {
            if (getBitmapFromMemCache(key) == null) {
                mMemoryCache.put(key, bitmap)
            }
        }

        fun getBitmapFromMemCache(key: String): Bitmap? {
            return mMemoryCache.get(key)
        }

    }
}
public class ReadTheme {
    var theme: Int = 0
}
