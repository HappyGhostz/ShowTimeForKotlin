package com.example.happyghost.showtimeforkotlin.wegit.read

import android.content.Context
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookMixATocBean
import com.example.happyghost.showtimeforkotlin.utils.SettingManager
import com.example.happyghost.showtimeforkotlin.utils.ThemeManager

/**
 * @author Zhao Chenping
 * @creat 2017/9/30.
 * @description
 */
class PageWidget:ReadView {
    var mCornerX = 1 // 拖拽点对应的页脚
    var mCornerY = 1
    var mPath0: Path
    var mPath1: Path

    var mBezierStart1 = PointF() // 贝塞尔曲线起始点
    var mBezierControl1 = PointF() // 贝塞尔曲线控制点
    var mBeziervertex1 = PointF() // 贝塞尔曲线顶点
    var mBezierEnd1 = PointF() // 贝塞尔曲线结束点

    var mBezierStart2 = PointF() // 另一条贝塞尔曲线
    var mBezierControl2 = PointF()
    var mBeziervertex2 = PointF()
    var mBezierEnd2 = PointF()

    var mMiddleX: Float = 0.toFloat()
    var mMiddleY: Float = 0.toFloat()
    var mDegrees: Float = 0.toFloat()
    var mTouchToCornerDis: Float = 0.toFloat()
    var mColorMatrixFilter: ColorMatrixColorFilter
    var mMatrix: Matrix
    var mMatrixArray = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 1.0f)

    var mIsRTandLB: Boolean = false // 是否属于右上左下
    var mMaxLength: Float
    lateinit var mBackShadowColors: IntArray// 背面颜色组
    lateinit var mFrontShadowColors: IntArray// 前面颜色组

    lateinit var mBackShadowDrawableLR: GradientDrawable // 有阴影的GradientDrawable
    lateinit var mBackShadowDrawableRL: GradientDrawable
    lateinit var mFolderShadowDrawableLR: GradientDrawable
    lateinit var mFolderShadowDrawableRL: GradientDrawable

    lateinit var mFrontShadowDrawableHBT: GradientDrawable
    lateinit var mFrontShadowDrawableHTB: GradientDrawable
    lateinit var mFrontShadowDrawableVLR: GradientDrawable
    lateinit var mFrontShadowDrawableVRL: GradientDrawable

    lateinit var mPaint: Paint

    constructor(context: Context, bookId: String,
                chaptersList: List<BookMixATocBean.MixTocBean.ChaptersBean>,
                listener: OnReadStateChangeListener): super(context, bookId, chaptersList, listener) {
        mPath0 = Path()
        mPath1 = Path()
        mMaxLength = Math.hypot(mScreenWidth.toDouble(), mScreenHeight.toDouble()).toFloat()
        mPaint = Paint()
        mPaint.style = Paint.Style.FILL

        createDrawable()

        val cm = ColorMatrix()//设置颜色数组
        val array = floatArrayOf(0.55f, 0f, 0f, 0f, 80.0f, 0f, 0.55f, 0f, 0f, 80.0f, 0f, 0f, 0.55f, 0f, 80.0f, 0f, 0f, 0f, 0.2f, 0f)
        cm.set(array)
        mColorMatrixFilter = ColorMatrixColorFilter(cm)
        mMatrix = Matrix()

        mTouch.x = 0.01f // 不让x,y为0,否则在点计算时会有问题
        mTouch.y = 0.01f
    }

    /**
     * 计算拖拽点对应的拖拽脚
     *
     * @param x 触摸点x坐标
     * @param y 触摸点y坐标
     */
    override fun calcCornerXY(x: Float, y: Float) {
        if (x <= mScreenWidth / 2)
            mCornerX = 0
        else
            mCornerX = mScreenWidth
        if (y <= mScreenHeight / 2)
            mCornerY = 0
        else
            mCornerY = mScreenHeight
        mIsRTandLB = mCornerX == 0 && mCornerY == mScreenHeight || mCornerX == mScreenWidth && mCornerY == 0
    }

    /**
     * 求解直线P1P2和直线P3P4的交点坐标
     */
    fun getCross(P1: PointF, P2: PointF, P3: PointF, P4: PointF): PointF {
        val CrossP = PointF()
        val a1 = (P2.y - P1.y) / (P2.x - P1.x)
        val b1 = (P1.x * P2.y - P2.x * P1.y) / (P1.x - P2.x)
        val a2 = (P4.y - P3.y) / (P4.x - P3.x)
        val b2 = (P3.x * P4.y - P4.x * P3.y) / (P3.x - P4.x)
        CrossP.x = (b2 - b1) / (a1 - a2)
        CrossP.y = a1 * CrossP.x + b1
        return CrossP
    }

    protected override fun calcPoints() {
        mMiddleX = (mTouch.x + mCornerX) / 2
        mMiddleY = (mTouch.y + mCornerY) / 2
        mBezierControl1.x = mMiddleX - (mCornerY - mMiddleY) * (mCornerY - mMiddleY) / (mCornerX - mMiddleX)
        mBezierControl1.y = mCornerY.toFloat()
        mBezierControl2.x = mCornerX.toFloat()
        //mBezierControl2.y = mMiddleY - (mCornerX - mMiddleX) * (mCornerX - mMiddleX) / (mCornerY - mMiddleY);

        val f4 = mCornerY - mMiddleY
        if (f4 == 0f) {
            mBezierControl2.y = mMiddleY - (mCornerX - mMiddleX) * (mCornerX - mMiddleX) / 0.1f
        } else {
            mBezierControl2.y = mMiddleY - (mCornerX - mMiddleX) * (mCornerX - mMiddleX) / (mCornerY - mMiddleY)
        }

        mBezierStart1.x = mBezierControl1.x - (mCornerX - mBezierControl1.x) / 2
        mBezierStart1.y = mCornerY.toFloat()

        // 当mBezierStart1.x < 0或者mBezierStart1.x > 480时
        // 如果继续翻页，会出现BUG故在此限制
        if (mTouch.x > 0 && mTouch.x < mScreenWidth) {
            if (mBezierStart1.x < 0 || mBezierStart1.x > mScreenWidth) {
                if (mBezierStart1.x < 0)
                    mBezierStart1.x = mScreenWidth - mBezierStart1.x

                val f1 = Math.abs(mCornerX - mTouch.x)
                val f2 = mScreenWidth * f1 / mBezierStart1.x
                mTouch.x = Math.abs(mCornerX - f2)

                val f3 = Math.abs(mCornerX - mTouch.x) * Math.abs(mCornerY - mTouch.y) / f1
                mTouch.y = Math.abs(mCornerY - f3)

                mMiddleX = (mTouch.x + mCornerX) / 2
                mMiddleY = (mTouch.y + mCornerY) / 2

                mBezierControl1.x = mMiddleX - (mCornerY - mMiddleY) * (mCornerY - mMiddleY) / (mCornerX - mMiddleX)
                mBezierControl1.y = mCornerY.toFloat()

                mBezierControl2.x = mCornerX.toFloat()
                //mBezierControl2.y = mMiddleY - (mCornerX - mMiddleX) * (mCornerX - mMiddleX) / (mCornerY - mMiddleY);

                val f5 = mCornerY - mMiddleY
                if (f5 == 0f) {
                    mBezierControl2.y = mMiddleY - (mCornerX - mMiddleX) * (mCornerX - mMiddleX) / 0.1f
                } else {
                    mBezierControl2.y = mMiddleY - (mCornerX - mMiddleX) * (mCornerX - mMiddleX) / (mCornerY - mMiddleY)
                }
                mBezierStart1.x = mBezierControl1.x - (mCornerX - mBezierControl1.x) / 2
            }
        }
        mBezierStart2.x = mCornerX.toFloat()
        mBezierStart2.y = mBezierControl2.y - (mCornerY - mBezierControl2.y) / 2

        mTouchToCornerDis = Math.hypot(mTouch.x - mCornerX.toDouble(), mTouch.y - mCornerY.toDouble()).toFloat()

        mBezierEnd1 = getCross(mTouch, mBezierControl1, mBezierStart1, mBezierStart2)
        mBezierEnd2 = getCross(mTouch, mBezierControl2, mBezierStart1, mBezierStart2)

        /*
         * mBeziervertex1.x 推导
		 * ((mBezierStart1.x+mBezierEnd1.x)/2+mBezierControl1.x)/2 化简等价于
		 * (mBezierStart1.x+ 2*mBezierControl1.x+mBezierEnd1.x) / 4
		 */
        mBeziervertex1.x = (mBezierStart1.x + 2 * mBezierControl1.x + mBezierEnd1.x) / 4
        mBeziervertex1.y = (2 * mBezierControl1.y + mBezierStart1.y + mBezierEnd1.y) / 4
        mBeziervertex2.x = (mBezierStart2.x + 2 * mBezierControl2.x + mBezierEnd2.x) / 4
        mBeziervertex2.y = (2 * mBezierControl2.y + mBezierStart2.y + mBezierEnd2.y) / 4
    }

    protected override fun drawCurrentPageArea(canvas: Canvas) {
        mPath0.reset()
        mPath0.moveTo(mBezierStart1.x, mBezierStart1.y)
        mPath0.quadTo(mBezierControl1.x, mBezierControl1.y, mBezierEnd1.x, mBezierEnd1.y)
        mPath0.lineTo(mTouch.x, mTouch.y)
        mPath0.lineTo(mBezierEnd2.x, mBezierEnd2.y)
        mPath0.quadTo(mBezierControl2.x, mBezierControl2.y, mBezierStart2.x, mBezierStart2.y)
        mPath0.lineTo(mCornerX.toFloat(), mCornerY.toFloat())
        mPath0.close()

        canvas.save()
        canvas.clipPath(mPath0, Region.Op.XOR)
        canvas.drawBitmap(mCurPageBitmap, 0f, 0f, null)
        try {
            canvas.restore()
        } catch (e: Exception) {

        }

    }

    protected override fun drawNextPageAreaAndShadow(canvas: Canvas) {
        mPath1.reset()
        mPath1.moveTo(mBezierStart1.x, mBezierStart1.y)
        mPath1.lineTo(mBeziervertex1.x, mBeziervertex1.y)
        mPath1.lineTo(mBeziervertex2.x, mBeziervertex2.y)
        mPath1.lineTo(mBezierStart2.x, mBezierStart2.y)
        mPath1.lineTo(mCornerX.toFloat(), mCornerY.toFloat())
        mPath1.close()

        mDegrees = Math.toDegrees(Math.atan2((mBezierControl1.x - mCornerX).toDouble(), (mBezierControl2.y - mCornerY).toDouble())).toFloat()
        val leftx: Int
        val rightx: Int
        val mBackShadowDrawable: GradientDrawable
        if (mIsRTandLB) {  //左下及右上
            leftx = mBezierStart1.x.toInt()
            rightx = (mBezierStart1.x + mTouchToCornerDis / 4).toInt()
            mBackShadowDrawable = mBackShadowDrawableLR
        } else {
            leftx = (mBezierStart1.x - mTouchToCornerDis / 4).toInt()
            rightx = mBezierStart1.x.toInt()
            mBackShadowDrawable = mBackShadowDrawableRL
        }
        canvas.save()
        try {
            canvas.clipPath(mPath0)
            canvas.clipPath(mPath1, Region.Op.INTERSECT)
        } catch (e: Exception) {
        }


        canvas.drawBitmap(mNextPageBitmap, 0f, 0f, null)
        canvas.rotate(mDegrees, mBezierStart1.x, mBezierStart1.y)
        mBackShadowDrawable.setBounds(leftx, mBezierStart1.y.toInt(),
                rightx, (mMaxLength + mBezierStart1.y).toInt())//左上及右下角的xy坐标值,构成一个矩形
        mBackShadowDrawable.draw(canvas)
        canvas.restore()
    }

    override fun setBitmaps(bm1: Bitmap?, bm2: Bitmap?) {
        mCurPageBitmap = bm1
        mNextPageBitmap = bm2
    }

    /**
     * 创建阴影的GradientDrawable
     */
    private fun createDrawable() {
        val color = intArrayOf(0x333333, 0xb0333333.toInt())
        mFolderShadowDrawableRL = GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, color)
        mFolderShadowDrawableRL.gradientType = GradientDrawable.LINEAR_GRADIENT

        mFolderShadowDrawableLR = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, color)
        mFolderShadowDrawableLR.gradientType = GradientDrawable.LINEAR_GRADIENT

        mBackShadowColors = intArrayOf(0xff111111.toInt(), 0x111111)
        mBackShadowDrawableRL = GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, mBackShadowColors)
        mBackShadowDrawableRL.gradientType = GradientDrawable.LINEAR_GRADIENT

        mBackShadowDrawableLR = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, mBackShadowColors)
        mBackShadowDrawableLR.gradientType = GradientDrawable.LINEAR_GRADIENT

        mFrontShadowColors = intArrayOf(0x80111111.toInt(), 0x111111)
        mFrontShadowDrawableVLR = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, mFrontShadowColors)
        mFrontShadowDrawableVLR.gradientType = GradientDrawable.LINEAR_GRADIENT
        mFrontShadowDrawableVRL = GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, mFrontShadowColors)
        mFrontShadowDrawableVRL.gradientType = GradientDrawable.LINEAR_GRADIENT

        mFrontShadowDrawableHTB = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, mFrontShadowColors)
        mFrontShadowDrawableHTB.gradientType = GradientDrawable.LINEAR_GRADIENT

        mFrontShadowDrawableHBT = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, mFrontShadowColors)
        mFrontShadowDrawableHBT.gradientType = GradientDrawable.LINEAR_GRADIENT
    }

    /**
     * 绘制翻起页的阴影
     *
     * @param canvas
     */
    //问题在这里
    override fun drawCurrentPageShadow(canvas: Canvas) {
        val degree: Double
        if (mIsRTandLB) {
            degree = Math.PI / 4 - Math.atan2((mBezierControl1.y - mTouch.y).toDouble(), (mTouch.x - mBezierControl1.x).toDouble())
        } else {
            degree = Math.PI / 4 - Math.atan2((mTouch.y - mBezierControl1.y).toDouble(), (mTouch.x - mBezierControl1.x).toDouble())
        }
        // 翻起页阴影顶点与touch点的距离
        val d1 = 25.toFloat().toDouble() * 1.414 * Math.cos(degree)
        val d2 = 25.toFloat().toDouble() * 1.414 * Math.sin(degree)
        val x = (mTouch.x + d1.toFloat())
        val y: Float
        if (mIsRTandLB) {
            y = (mTouch.y + d2.toFloat())
        } else {
            y = (mTouch.y - d2.toFloat())
        }
        mPath1.reset()
        mPath1.moveTo(x, y)
        mPath1.lineTo(mTouch.x, mTouch.y)
        mPath1.lineTo(mBezierControl1.x, mBezierControl1.y)
        mPath1.lineTo(mBezierStart1.x, mBezierStart1.y)
        mPath1.close()
        var rotateDegrees: Float
        canvas.save()
        try {
            canvas.clipPath(mPath0, Region.Op.XOR)
            canvas.clipPath(mPath1, Region.Op.INTERSECT)
        } catch (e: Exception) {
        }

        var leftx =0
        var rightx=0
        var mCurrentPageShadow: GradientDrawable
        if (mIsRTandLB) {
            leftx = mBezierControl1.x.toInt()
            rightx = mBezierControl1.x.toInt() + 25
            mCurrentPageShadow = mFrontShadowDrawableVLR
        } else {
            leftx = (mBezierControl1.x - 25).toInt()
            rightx = mBezierControl1.x.toInt() + 1
            mCurrentPageShadow = mFrontShadowDrawableVRL
        }

        rotateDegrees = Math.toDegrees(Math.atan2((mTouch.x - mBezierControl1.x).toDouble(),
                (mBezierControl1.y - mTouch.y).toDouble())).toFloat()
        canvas.rotate(rotateDegrees, mBezierControl1.x, mBezierControl1.y)
        mCurrentPageShadow.setBounds(leftx, (mBezierControl1.y - mMaxLength).toInt(),
                rightx, mBezierControl1.y.toInt())
        mCurrentPageShadow.draw(canvas)
        canvas.restore()

        mPath1.reset()
        mPath1.moveTo(x, y)
        mPath1.lineTo(mTouch.x, mTouch.y)
        mPath1.lineTo(mBezierControl2.x, mBezierControl2.y)
        mPath1.lineTo(mBezierStart2.x, mBezierStart2.y)
        mPath1.close()
        canvas.save()
        try {
            canvas.clipPath(mPath0, Region.Op.XOR)
            canvas.clipPath(mPath1, Region.Op.INTERSECT)
        } catch (e: Exception) {
        }

        if (mIsRTandLB) {
            leftx = mBezierControl2.y.toInt()
            rightx = (mBezierControl2.y + 25).toInt()
            mCurrentPageShadow = mFrontShadowDrawableHTB
        } else {
            leftx = (mBezierControl2.y - 25).toInt()
            rightx = (mBezierControl2.y + 1).toInt()
            mCurrentPageShadow = mFrontShadowDrawableHBT
        }
        rotateDegrees = Math.toDegrees(Math.atan2((mBezierControl2.y - mTouch.y).toDouble(), (mBezierControl2.x - mTouch.x).toDouble())).toFloat()
        canvas.rotate(rotateDegrees, mBezierControl2.x, mBezierControl2.y)
        val temp: Float
        if (mBezierControl2.y < 0)
            temp = mBezierControl2.y - mScreenHeight
        else
            temp = mBezierControl2.y

        val hmg = Math.hypot(mBezierControl2.x.toDouble(), temp.toDouble()).toInt()
        if (hmg > mMaxLength) {
            mCurrentPageShadow.setBounds((mBezierControl2.x - 25).toInt() - hmg, leftx,
                    (mBezierControl2.x + mMaxLength).toInt() - hmg, rightx)
        } else {
            mCurrentPageShadow.setBounds((mBezierControl2.x - mMaxLength).toInt(), leftx,
                    mBezierControl2.x.toInt(), rightx)
        }
        mCurrentPageShadow.draw(canvas)
        canvas.restore()
    }

    protected override fun drawCurrentBackArea(canvas: Canvas) {
        val i = (mBezierStart1.x + mBezierControl1.x).toInt() / 2
        val f1 = Math.abs(i - mBezierControl1.x)
        val i1 = (mBezierStart2.y + mBezierControl2.y).toInt() / 2
        val f2 = Math.abs(i1 - mBezierControl2.y)
        val f3 = Math.min(f1, f2)
        mPath1.reset()
        mPath1.moveTo(mBeziervertex2.x, mBeziervertex2.y)
        mPath1.lineTo(mBeziervertex1.x, mBeziervertex1.y)
        mPath1.lineTo(mBezierEnd1.x, mBezierEnd1.y)
        mPath1.lineTo(mTouch.x, mTouch.y)
        mPath1.lineTo(mBezierEnd2.x, mBezierEnd2.y)
        mPath1.close()
        val mFolderShadowDrawable: GradientDrawable
        val left: Int
        val right: Int
        if (mIsRTandLB) {
            left = (mBezierStart1.x - 1).toInt()
            right = (mBezierStart1.x + f3 + 1f).toInt()
            mFolderShadowDrawable = mFolderShadowDrawableLR
        } else {
            left = (mBezierStart1.x - f3 - 1f).toInt()
            right = (mBezierStart1.x + 1).toInt()
            mFolderShadowDrawable = mFolderShadowDrawableRL
        }
        canvas.save()
        try {
            canvas.clipPath(mPath0)
            canvas.clipPath(mPath1, Region.Op.INTERSECT)
        } catch (e: Exception) {
        }


        mPaint.colorFilter = mColorMatrixFilter

        val dis = Math.hypot((mCornerX - mBezierControl1.x).toDouble(),
                (mBezierControl2.y - mCornerY).toDouble()).toFloat()
        val f8 = (mCornerX - mBezierControl1.x) / dis
        val f9 = (mBezierControl2.y - mCornerY) / dis
        mMatrixArray[0] = 1 - 2f * f9 * f9
        mMatrixArray[1] = 2f * f8 * f9
        mMatrixArray[3] = mMatrixArray[1]
        mMatrixArray[4] = 1 - 2f * f8 * f8
        mMatrix.reset()
        mMatrix.setValues(mMatrixArray)
        mMatrix.preTranslate(-mBezierControl1.x, -mBezierControl1.y)
        mMatrix.postTranslate(mBezierControl1.x, mBezierControl1.y)
        canvas.drawBitmap(mCurPageBitmap, mMatrix, mPaint)
        // canvas.drawBitmap(bitmap, mMatrix, null);
        mPaint.colorFilter = null
        canvas.rotate(mDegrees, mBezierStart1.x, mBezierStart1.y)
        mFolderShadowDrawable.setBounds(left, mBezierStart1.y.toInt(), right,
                (mBezierStart1.y + mMaxLength).toInt())
        mFolderShadowDrawable.draw(canvas)
        canvas.restore()
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mScroller.computeScrollOffset()) {
            val x = mScroller.currX
            val y = mScroller.currY
            mTouch.x = x.toFloat()
            mTouch.y = y.toFloat()
            postInvalidate()
        }
    }

    protected override fun startAnimation() {
        val dx: Int
        val dy: Int
        if (mCornerX > 0) {
            dx = -((mScreenWidth + mTouch.x).toInt() )
        } else {
            dx = (mScreenWidth - mTouch.x + mScreenWidth).toInt()
        }
        if (mCornerY > 0) {
            dy = (mScreenHeight - mTouch.y) .toInt()
        } else {
            dy = (1 - mTouch.y) .toInt() // 防止mTouch.y最终变为0
        }
        mScroller.startScroll(mTouch.x .toInt(), mTouch.y .toInt(), dx, dy, 700)
    }

    override fun abortAnimation() {
        if (!mScroller.isFinished) {
            mScroller.abortAnimation()
        }
    }

    override fun restoreAnimation() {
        val dx: Int
        val dy: Int
        if (mCornerX > 0) {
            dx = (mScreenWidth - mTouch.x) .toInt()
        } else {
            dx = -mTouch.x .toInt()
        }
        if (mCornerY > 0) {
            dy = (mScreenHeight - mTouch.y) .toInt()
        } else {
            dy = (1 - mTouch.y) .toInt()
        }
        mScroller.startScroll(mTouch.x .toInt(), mTouch.y .toInt(), dx, dy, 300)
    }

    @Synchronized
    override fun setTheme(theme: Int) {
        resetTouchPoint()
        calcCornerXY(mTouch.x, mTouch.y)
        val bg = ThemeManager.getThemeDrawable(theme)
        if (bg != null) {
            pagefactory?.setBgBitmap(bg)
            pagefactory?.convertBetteryBitmap()
            if (isPrepared) {
                pagefactory?.onDraw(mCurrentPageCanvas)
                pagefactory?.onDraw(mNextPageCanvas)
                postInvalidate()
//                invalidate()
            }

        }
        if (theme < 5) {
            SettingManager.getInstance()?.saveReadTheme(theme)
        }
    }

    override fun jumpToChapter(chapter: Int) {
        calcCornerXY(mTouch.x, mTouch.y)
        super.jumpToChapter(chapter)
    }
}