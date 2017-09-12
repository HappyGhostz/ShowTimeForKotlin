package com.example.happyghost.showtimeforkotlin.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.flyco.dialog.widget.popup.BubblePopup

/**
 * @author Zhao Chenping
 * @creat 2017/9/12.
 * @description
 */
class DialogHelper {
    companion object {
        /**
         * 生成 Popup
         * @param context
         * @param layoutResId
         * @return
         */
        fun creatPopup(context: Context,resLayout:Int): BubblePopup {
            val view = View.inflate(context, resLayout, null)
            return BubblePopup(context,view)
        }
    }
}