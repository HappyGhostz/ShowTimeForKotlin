package com.example.happyghost.showtimeforkotlin.utils

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.provider.Settings
import android.util.TypedValue
import com.example.happyghost.showtimeforkotlin.AppApplication
import java.lang.reflect.Field

/**
 * @author Zhao Chenping
 * @creat 2017/9/30.
 * @description
 */
 class ScreenUtils {
    companion object {
        /**
         * 计算图片要显示的高度
         *
         * @param pixel 原始分辨率
         * @param width 要显示的宽度
         * @return
         */
        fun calcPhotoHeight(height: Int?, width: Int?, photoWidth: Int): Int {
            var photoHeight = -1
            try {
                if (height != null) {
                    photoHeight = (height * (photoWidth * 1.0f / width!!)).toInt()
                }
            } catch (e: NumberFormatException) {
                return -1
            }
            return photoHeight
        }
        enum class EScreenDensity {
            XXHDPI, //超高分辨率    1080×1920
            XHDPI, //超高分辨率    720×1280
            HDPI, //高分辨率         480×800
            MDPI
            //中分辨率         320×480
        }

        fun getDisply(context: Context): EScreenDensity {
            val eScreenDensity: EScreenDensity
            //初始化屏幕密度
            val dm = context.applicationContext.resources.displayMetrics
            val densityDpi = dm.densityDpi

            if (densityDpi <= 160) {
                eScreenDensity = EScreenDensity.MDPI
            } else if (densityDpi <= 240) {
                eScreenDensity = EScreenDensity.HDPI
            } else if (densityDpi < 400) {
                eScreenDensity = EScreenDensity.XHDPI
            } else {
                eScreenDensity = EScreenDensity.XXHDPI
            }
            return eScreenDensity
        }

        /**
         * 获取屏幕宽度
         *
         * @return
         */
        fun getScreenWidth(): Int {
            return  AppApplication.instance.getContext().resources.displayMetrics.widthPixels
        }

        /**
         * 获取屏幕高度
         *
         * @return
         */
        fun getScreenHeight(): Int {
            return  AppApplication.instance.getContext().resources.displayMetrics.heightPixels
        }

        /**
         * 将dp转换成px
         *
         * @param dp
         * @return
         */
        fun dpToPx(dp: Float): Float {
            return dp *  AppApplication.instance.getContext().resources.displayMetrics.density
        }

        fun dpToPxInt(dp: Float): Int {
            return (dpToPx(dp) + 0.5f).toInt()
        }

        /**
         * 将px转换成dp
         *
         * @param px
         * @return
         */
        fun pxToDp(px: Float): Float {
            return px /  AppApplication.instance.getContext().resources.displayMetrics.density
        }

        fun pxToDpInt(px: Float): Int {
            return (pxToDp(px) + 0.5f).toInt()
        }

        /**
         * 将px值转换为sp值
         *
         * @param pxValue
         * @return
         */
        fun pxToSp(pxValue: Float): Float {
            return pxValue /  AppApplication.instance.getContext().resources.displayMetrics.scaledDensity
        }

        /**
         * 将sp值转换为px值
         *
         * @param spValue
         * @return
         */
        fun spToPx(spValue: Float): Float {
            return spValue *  AppApplication.instance.getContext().resources.displayMetrics.scaledDensity
        }

        fun getStatusBarHeight(context: Context): Int {
            var result = 0
            val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }

        fun getActionBarSize(context: Context): Int {
            val tv = TypedValue()
            return if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                TypedValue.complexToDimensionPixelSize(tv.data, context.resources.displayMetrics)
            } else 0
        }

        private fun getStatusBarHeight(): Int {
            var c: Class<*>? = null
            var obj: Any? = null
            var field: Field? = null
            var x = 0
            var sbar = 0
            try {
                c = Class.forName("com.android.internal.R\$dimen")
                obj = c!!.newInstance()
                field = c.getField("status_bar_height")
                x = Integer.parseInt(field!!.get(obj).toString())
                sbar = AppApplication.instance.getContext().resources.getDimensionPixelSize(x)
            } catch (e1: Exception) {
                e1.printStackTrace()
            }

            return sbar
        }

        /**
         * 当前是否是横屏
         *
         * @param context context
         * @return boolean
         */
        fun isLandscape(context: Context): Boolean {
            return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        }

        /**
         * 当前是否是竖屏
         *
         * @param context context
         * @return boolean
         */
        fun isPortrait(context: Context): Boolean {
            return context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        }

        /**
         * 调整窗口的透明度  1.0f,0.5f 变暗
         *
         * @param from    from>=0&&from<=1.0f
         * @param to      to>=0&&to<=1.0f
         * @param context 当前的activity
         */
        fun dimBackground(from:Float, to:Float, context:Activity) {
            val window = context.window
            val valueAnimator = ValueAnimator.ofFloat(from, to)
            valueAnimator.duration = 500
            valueAnimator.addUpdateListener { animation ->
                val params = window.attributes
                params.alpha = animation.animatedValue as Float
                window.attributes = params
            }
            valueAnimator.start()
        }

        /**
         * 判断是否开启了自动亮度调节
         *
         * @param activity
         * @return
         */
        fun isAutoBrightness(activity: Activity): Boolean {
            var isAutoAdjustBright = false
            try {
                isAutoAdjustBright = Settings.System.getInt(
                        activity.contentResolver,
                        Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
            }

            return isAutoAdjustBright
        }

        /**
         * 关闭亮度自动调节
         *
         * @param activity
         */
        fun stopAutoBrightness(activity: Activity) {
            Settings.System.putInt(activity.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
        }

        /**
         * 开启亮度自动调节
         *
         * @param activity
         */

        fun startAutoBrightness(activity: Activity) {
            Settings.System.putInt(activity.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC)
        }

        /**
         * 获得当前屏幕亮度值
         *
         * @param mContext
         * @return 0~100
         */
        fun getScreenBrightness(mContext: Context): Float {
            var screenBrightness = 255
            try {
                screenBrightness = Settings.System.getInt(mContext.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return screenBrightness / 255.0f * 100
        }

        /**
         * 设置当前屏幕亮度值
         *
         * @param paramInt 0~100
         * @param mContext
         */
        fun saveScreenBrightness(paramInt: Int, mContext: Context) {
            var paramInt = paramInt
            if (paramInt <= 5) {
                paramInt = 5
            }
            try {
                val f = paramInt / 100.0f * 255
                Settings.System.putInt(mContext.contentResolver, Settings.System.SCREEN_BRIGHTNESS, f.toInt())
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        /**
         * 设置Activity的亮度
         *
         * @param paramInt
         * @param mActivity
         */
        fun setScreenBrightness(paramInt: Int, mActivity: Activity) {
            var paramInt = paramInt
            if (paramInt <= 5) {
                paramInt = 5
            }
            val localWindow = mActivity.window
            val localLayoutParams = localWindow.attributes
            val f = paramInt / 100.0f
            localLayoutParams.screenBrightness = f
            localWindow.attributes = localLayoutParams
        }
    }
}
