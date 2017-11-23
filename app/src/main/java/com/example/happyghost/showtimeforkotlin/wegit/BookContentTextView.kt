package com.example.happyghost.showtimeforkotlin.wegit

import android.content.Context
import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.ui.book.search.BookSearchActivity

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
class BookContentTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : TextView(context, attrs, defStyleAttr) {

    fun setSpannedText(text: String) {
        var text = text
        val startIndex = 0
        while (true) {

            val start = text.indexOf("《")
            val end = text.indexOf("》")
            if (start < 0 || end < 0) {
                append(text.substring(startIndex))
                break
            }

            append(text.substring(startIndex, start))

            val spanableInfo = SpannableString(text.substring(start, end + 1))
            spanableInfo.setSpan(Clickable(spanableInfo.toString()), 0, end + 1 - start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            append(spanableInfo)
            //setMovementMethod()该方法必须调用，否则点击事件不响应
            movementMethod = LinkMovementMethod.getInstance()
            text = text.substring(end + 1)
        }
    }

    internal inner class Clickable(private val name: String) : ClickableSpan() {

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = ContextCompat.getColor(context, R.color.light_coffee)
            ds.isUnderlineText = false
        }

        override fun onClick(v: View) {
            BookSearchActivity.open(context, name.replace("》".toRegex(), "").replace("《".toRegex(), ""))
        }
    }
}
