package com.example.happyghost.showtimeforkotlin.adapter.bookadapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.utils.ImageLoader
import com.example.happyghost.showtimeforkotlin.utils.ReadTheme
import com.example.happyghost.showtimeforkotlin.utils.SettingManager
import com.example.happyghost.showtimeforkotlin.utils.ThemeManager
import com.example.happyghost.showtimeforkotlin.wegit.SelectCricleImageView

/**
 * @author Zhao Chenping
 * @creat 2017/11/4.
 * @description
 */
class ReadThemeAdapter(themes: List<ReadTheme>, curTheme: Int) :BaseQuickAdapter<ReadTheme,BaseViewHolder>( R.layout.item_read_theme,themes) {
    override fun convert(helper: BaseViewHolder?, item: ReadTheme?) {
        val position = helper?.adapterPosition
        val imageView = helper?.getView<SelectCricleImageView>(R.id.ivThemeBg)
        if (imageView != null) {
            ThemeManager.setReaderTheme(item!!.theme,imageView)
            val readTheme = SettingManager.getInstance()?.getReadTheme()
            if(position == readTheme){
                imageView.setMDynamicAngle(360f)
            }
        }
    }
}