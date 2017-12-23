package com.example.happyghost.showtimeforkotlin.wegit

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View
import com.example.happyghost.showtimeforkotlin.R
import java.lang.ref.WeakReference

/**
 * @author Zhao Chenping
 * @creat 2017/12/18.
 * @description
 */
class LoadingView:View {
    private var mOuterCircleRadius: Int = 0
    private val mOuterCircleColor: Int
    private var mInnerTriangleRadius: Int = 0
    private val mInnerTriangleColor: Int
    private val mBackgroundColor: Int
    private var mStrokeWidth: Int = 0
    private val mIsNeedBackground: Boolean

    lateinit var mPaint:Paint
    lateinit var mTrianglePaint:Paint
    lateinit var mBackGroundPaint:Paint

    private var mRectF: RectF? = null
    lateinit var mRoundRectF: RectF
    lateinit var mPath: Path
    lateinit var mRotateCenter: PointF
    private var isReverse = false

    private var mProgress = 0
    private var mStartAngle = -90
    private var mRotateAngle = 0
    private val mDel = 30
    lateinit var mHandler: MyHandler
    var REFRESH_VIEW = 0
    constructor(context: Context):this(context,null) {}

    constructor(context: Context, attrs: AttributeSet?):this(context, attrs, 0) {
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):super(context, attrs, defStyleAttr) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingView)
        mOuterCircleRadius = typedArray.getDimensionPixelSize(R.styleable.LoadingView_outerCircleRadius, 50)
        mOuterCircleColor = typedArray.getColor(R.styleable.LoadingView_outerCircleColor, -0xdd74de)
        mInnerTriangleRadius = typedArray.getDimensionPixelSize(R.styleable.LoadingView_innerTriangleRadius, 25)
        mInnerTriangleColor = typedArray.getColor(R.styleable.LoadingView_innerTriangleColor, -0xdd74de)
        mIsNeedBackground = typedArray.getBoolean(R.styleable.LoadingView_isNeedBackground, true)
        mBackgroundColor = typedArray.getColor(R.styleable.LoadingView_backgroundColor, -0x44ddddde)
        mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.LoadingView_strokeWidth, 5)
        typedArray.recycle()
        init()
    }
    fun init(){
        //初始化圆环进度画笔
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.style = Paint.Style.STROKE
        mPaint.color = mOuterCircleColor
        mPaint.strokeWidth = mStrokeWidth.toFloat()
        //初始化三角行画笔
        mTrianglePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTrianglePaint.color = mInnerTriangleColor
        //初始化背景画笔
        mBackGroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mBackGroundPaint.color = mBackgroundColor

        mPath = Path()
        mRoundRectF = RectF()
        mRotateCenter = PointF()
        mHandler = MyHandler(this)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //判断外圆的半径
        mOuterCircleRadius = Math.min(mOuterCircleRadius,(Math.min(w-paddingRight-paddingLeft,
                h-paddingTop-paddingBottom)-4*mPaint.strokeWidth).toInt()/2)
        if(mOuterCircleRadius<0){
            mStrokeWidth = Math.min(w-paddingRight-paddingLeft,h-paddingTop-paddingBottom)/2
            mOuterCircleRadius = Math.min(w-paddingRight-paddingLeft,h-paddingTop-paddingBottom)/4
        }
        var left =((w-2*mOuterCircleRadius)/2).toFloat()
        var top = ((h-2*mOuterCircleRadius)/2).toFloat()
        var diameter = 2*mOuterCircleRadius
        mRectF = RectF(left,top,left+diameter,top+diameter)
        //判断内圆的半径大小
        if(mInnerTriangleRadius<mOuterCircleRadius){
            mInnerTriangleRadius =mInnerTriangleRadius
        }else{
            mInnerTriangleRadius = 3 * mOuterCircleRadius / 5
        }
        if(mInnerTriangleRadius<0){
            mInnerTriangleRadius =0
        }
        //计算内圆的圆心，圆心应该和外圆圆心相同
        val centerX = left + mOuterCircleRadius
        val centerY = top + mOuterCircleRadius
        //计算内圆的内接三角形的三个定点组成的path
        mPath.moveTo(centerX - mInnerTriangleRadius / 2, (centerY - Math.sqrt(3.0) * mInnerTriangleRadius / 2).toFloat())
        mPath.lineTo(centerX + mInnerTriangleRadius, centerY)
        mPath.lineTo(centerX - mInnerTriangleRadius / 2, (centerY + Math.sqrt(3.0) * mInnerTriangleRadius / 2).toFloat())
        mPath.close()
        mRotateCenter.set((measuredWidth / 2).toFloat(), (measuredHeight / 2).toFloat())
        mRoundRectF.left = 0f
        mRoundRectF.top = 0f
        mRoundRectF.right = w.toFloat()
        mRoundRectF.bottom = h.toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measureSize(widthMeasureSpec, 140), measureSize(heightMeasureSpec, 140))
    }
    fun measureSize(defaultMeasureSpec: Int, defaultMeasure: Int): Int {
        val mode = MeasureSpec.getMode(defaultMeasureSpec)
        val size = MeasureSpec.getSize(defaultMeasureSpec)
        var resultSize = defaultMeasure
        when(mode){
            MeasureSpec.EXACTLY->resultSize = size
            MeasureSpec.AT_MOST->resultSize = Math.min(size, resultSize)
            MeasureSpec.UNSPECIFIED->resultSize = Math.min(size, resultSize)
        }
        return resultSize
    }

    override fun onDraw(canvas: Canvas?) {
        if (mIsNeedBackground) {
            canvas?.drawRoundRect(mRoundRectF, 8f, 8f, mBackGroundPaint)
        }

        if (isReverse) {
            mProgress -= mDel
            mStartAngle += mDel
            if (mStartAngle >= 270) {
                mStartAngle = -90
                isReverse = false
            }
            mRotateAngle += mDel
            if (mRotateAngle >= 360) {
                mRotateAngle = 0
            }
            canvas?.save()
            canvas?.rotate(mRotateAngle.toFloat(), mRotateCenter.x, mRotateCenter.y)
            canvas?.drawPath(mPath, mTrianglePaint)
            canvas?.restore()
        } else {
            mProgress += mDel
            if (mProgress >= 360) {
                isReverse = true
            }
            canvas?.drawPath(mPath, mTrianglePaint)
        }

        canvas?.drawArc(mRectF, mStartAngle.toFloat(), mProgress.toFloat(), false, mPaint)
        mHandler.sendEmptyMessageDelayed(REFRESH_VIEW, 80)
    }

    inner class MyHandler(loadingView: LoadingView) : Handler() {
        private var mLoadingViewWeakReference: WeakReference<LoadingView> = WeakReference(loadingView)

        override fun handleMessage(msg: Message) {
            if (mLoadingViewWeakReference.get() != null) {
                when (msg.what) {
                    REFRESH_VIEW -> mLoadingViewWeakReference.get()!!.postInvalidate()
                }
            }
        }
    }
}