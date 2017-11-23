package com.example.happyghost.showtimeforkotlin.wegit.read

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Region
import android.graphics.drawable.GradientDrawable
import com.example.happyghost.showtimeforkotlin.bean.bookdate.BookMixATocBean
import com.example.happyghost.showtimeforkotlin.ui.book.read.ReadActivity
import com.example.happyghost.showtimeforkotlin.utils.SettingManager
import com.example.happyghost.showtimeforkotlin.utils.ThemeManager

/**
 * @author Zhao Chenping
 * @creat 2017/10/10.
 * @description
 */
class OverlappedWidget(context: Context, mBookId: String,
                       chapters: ArrayList<BookMixATocBean.MixTocBean.ChaptersBean>,
                       readListener: ReadActivity.ReadListener) : ReadView(context,mBookId,chapters,
        readListener) {
    var mPath0: Path

    var mBackShadowDrawableLR: GradientDrawable
    var mBackShadowDrawableRL: GradientDrawable
    init {
        mTouch.x = 0.01f
        mTouch.y = 0.01f

        mPath0 = Path()

        val mBackShadowColors = intArrayOf(0xaa666666.toInt(), 0x666666)
        mBackShadowDrawableRL = GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, mBackShadowColors)
        mBackShadowDrawableRL.gradientType = GradientDrawable.LINEAR_GRADIENT

        mBackShadowDrawableLR = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, mBackShadowColors)
        mBackShadowDrawableLR.gradientType = GradientDrawable.LINEAR_GRADIENT
    }
    override fun drawNextPageAreaAndShadow(canvas: Canvas) {
        canvas.save()
        if (actiondownX > mScreenWidth shr 1) {
            canvas.clipPath(mPath0)
            canvas.drawBitmap(mNextPageBitmap, 0f, 0f, null)
        } else {
            canvas.clipPath(mPath0, Region.Op.XOR)
            canvas.drawBitmap(mNextPageBitmap, 0f, 0f, null)
        }
        try {
            canvas.restore()
        } catch (e: Exception) {

        }

    }

    override fun drawCurrentPageShadow(canvas: Canvas) {
        canvas.save()
        val shadow: GradientDrawable
        if (actiondownX > mScreenWidth shr 1) {
            shadow = mBackShadowDrawableLR
            shadow.setBounds((mScreenWidth + touch_down - 5).toInt() , 0, (mScreenWidth + touch_down + 5).toInt() , mScreenHeight)

        } else {
            shadow = mBackShadowDrawableRL
            shadow.setBounds((touch_down - 5).toInt() , 0, (touch_down + 5).toInt() , mScreenHeight)
        }
        shadow.draw(canvas)
        try {
            canvas.restore()
        } catch (e: Exception) {

        }

    }

    override fun drawCurrentBackArea(canvas: Canvas) {

    }

    override fun drawCurrentPageArea(canvas: Canvas) {
        mPath0.reset()

        canvas.save()
        if (actiondownX > mScreenWidth shr 1) {
            mPath0.moveTo(mScreenWidth + touch_down, 0f)
            mPath0.lineTo(mScreenWidth + touch_down, mScreenHeight.toFloat())
            mPath0.lineTo(mScreenWidth.toFloat(), mScreenHeight.toFloat())
            mPath0.lineTo(mScreenWidth.toFloat(), 0f)
            mPath0.lineTo(mScreenWidth + touch_down, 0f)
            mPath0.close()
            canvas.clipPath(mPath0, Region.Op.XOR)
            canvas.drawBitmap(mCurPageBitmap, touch_down, 0f, null)
        } else {
            mPath0.moveTo(touch_down, 0f)
            mPath0.lineTo(touch_down, mScreenHeight.toFloat())
            mPath0.lineTo(mScreenWidth.toFloat(), mScreenHeight.toFloat())
            mPath0.lineTo(mScreenWidth.toFloat(), 0f)
            mPath0.lineTo(touch_down, 0f)
            mPath0.close()
            canvas.clipPath(mPath0)
            canvas.drawBitmap(mCurPageBitmap, touch_down, 0f, null)
        }
        try {
            canvas.restore()
        } catch (e: Exception) {

        }

    }

    override fun calcPoints() {

    }

    override fun calcCornerXY(x: Float, y: Float) {

    }

    override fun computeScroll() {
        super.computeScroll()
        if (mScroller.computeScrollOffset()) {
            val x = mScroller.currX
            val y = mScroller.currY
            if (actiondownX > mScreenWidth shr 1) {
                touch_down = -(mScreenWidth.toFloat() - x)
            } else {
                touch_down = x.toFloat()
            }
            mTouch.y = y.toFloat()
            //touch_down = mTouch.x - actiondownX;
            postInvalidate()
        }
    }

    override fun startAnimation() {
        var dx=0
        if (actiondownX > mScreenWidth / 2) {
            dx = -(mScreenWidth + touch_down).toInt()
            mScroller.startScroll((mScreenWidth + touch_down) .toInt(), mTouch.y.toInt(), dx, 0, 700)
        } else {
            dx = (mScreenWidth - touch_down).toInt()
            mScroller.startScroll(touch_down .toInt(), mTouch.y.toInt(), dx, 0, 700)
        }
    }

    override fun abortAnimation() {
        if (!mScroller.isFinished) {
            mScroller.abortAnimation()
        }
    }

    override fun restoreAnimation() {
        var dx=0
        if (actiondownX > mScreenWidth / 2) {
            dx = (mScreenWidth - mTouch.x).toInt()
        } else {
            dx = -mTouch.x.toInt()
        }
        mScroller.startScroll(mTouch.x.toInt(), mTouch.y.toInt(), dx, 0, 300)
    }

    override fun setBitmaps(bm1: Bitmap?, bm2: Bitmap?) {
        mCurPageBitmap = bm1
        mNextPageBitmap = bm2
    }

    override fun setTheme(theme: Int) {
        resetTouchPoint()
        val bg = ThemeManager.getThemeDrawable(theme)
        if (bg != null) {
            pagefactory?.setBgBitmap(bg)
            if (isPrepared) {
                pagefactory?.onDraw(mCurrentPageCanvas)
                pagefactory?.onDraw(mNextPageCanvas)
                postInvalidate()
            }
        }
        if (theme < 5) {
            SettingManager.getInstance()?.saveReadTheme(theme)
        }
    }

}