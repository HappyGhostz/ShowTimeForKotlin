package com.example.happyghost.showtimeforkotlin.wegit

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

import com.example.happyghost.showtimeforkotlin.R


/**
 * @author Zhao Chenping
 * @creat 2017/7/12.
 * @description
 */

class ProgressCustomView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1) : View(context, attrs, defStyleAttr) {

    private var mDownTime: Int = 0
    private var mText: String? = null
    private var mArcPaint: Paint? = null
    private var mCirPaint: Paint? = null
    private var mTextPaint: Paint? = null
    private var mRadius: Int = 0
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mDynamicAngle: Float = 0.toFloat()
    private var pcOnClickListener: PCviewOnClickListener? = null
    private var mTextRect: Rect? = null


    private var defaultSize: Int = 0


    private var mBackGroundColor: Int = 0
    private var mArcColor: Int = 0
    private var mTextColor: Int = 0
    private var mTextSize: Float = 0.toFloat()
    private var mAnimation: ObjectAnimator? = null
    private var mArcSize: Float = 0.toFloat()

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        //对控件进行自定义属性的设置，提高控件的扩展性，可以自由控制控件的属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressCustomView)
        mDownTime = typedArray.getInt(R.styleable.ProgressCustomView_pc_time, 5)
        mText = typedArray.getString(R.styleable.ProgressCustomView_pc_text)
        mBackGroundColor = typedArray.getColor(R.styleable.ProgressCustomView_pc_background, Color.GRAY)
        mArcColor = typedArray.getColor(R.styleable.ProgressCustomView_pc_arccolor, Color.BLUE)
        mTextColor = typedArray.getColor(R.styleable.ProgressCustomView_pc_textcolor, Color.BLACK)
        mTextSize = typedArray.getDimension(R.styleable.ProgressCustomView_pc_textsize, 20f)
        mArcSize = typedArray.getDimension(R.styleable.ProgressCustomView_pc_arc_size, 3f)
        typedArray.recycle()
        //初始化画笔
        mArcPaint = Paint()
        mArcPaint!!.isAntiAlias = true
        mArcPaint!!.color = mArcColor
        mArcPaint!!.style = Paint.Style.STROKE
        mArcPaint!!.strokeWidth = mArcSize

        mCirPaint = Paint()
        mCirPaint!!.isAntiAlias = true
        mCirPaint!!.color = mBackGroundColor

        mTextPaint = Paint()
        mTextPaint!!.isAntiAlias = true
        mTextPaint!!.color = mTextColor
        mTextPaint!!.textSize = mTextSize
        //对控件设置点击监听
        initClickListener()
    }

    private fun initClickListener() {
        val clickListener = OnClickListener {
            if (pcOnClickListener != null) {
                pcOnClickListener!!.onClick()
                startAnimation()
            }
        }
        setOnClickListener(clickListener)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        defaultSize = 100

        if (widthMode == View.MeasureSpec.AT_MOST && heightMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultSize, defaultSize)
        } else if (widthMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultSize, heightSize)
        } else if (heightMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, defaultSize)
        } else {
            setMeasuredDimension(widthSize, heightSize)
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        //获取控件的宽高
        mWidth = width
        mHeight = height
        //获取圆的半径
        mRadius = Math.min(mWidth, mHeight) / 2
    }

    override fun onDraw(canvas: Canvas) {
        //绘制背景圆
        drawCir(canvas)
        //绘制圆环
        drawArc(canvas)
        //绘制文字
        drawText(canvas)
    }

    private fun drawArc(canvas: Canvas) {
        val rectF = RectF(mWidth / 2 - mRadius + mArcSize, mHeight / 2 - mRadius + mArcSize, mWidth / 2 + mRadius - mArcSize, mHeight / 2 + mRadius - mArcSize)
        mTextRect = Rect()
        //        mDynamicAngle = 360/mDownTime;
        canvas.drawArc(rectF, 0f, mDynamicAngle, false, mArcPaint!!)
    }

    private fun drawText(canvas: Canvas) {
        mTextPaint!!.getTextBounds(mText, 0, mText!!.length, mTextRect)
        canvas.drawText(mText!!, (mWidth / 2 - mTextRect!!.width() / 2).toFloat(), (mHeight / 2 + mTextRect!!.height() / 2).toFloat(), mTextPaint!!)
    }

    private fun drawCir(canvas: Canvas) {
        canvas.drawCircle((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), mRadius.toFloat(), mCirPaint!!)
    }

    //view销毁的时候执行该方法
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancelAnimation()
    }

    /**
     * 开始动画
     */
    fun startAnimation() {
        if (mAnimation == null) {
//            mAnimation = ObjectAnimator.ofFloat(this, "mDynamicAngle", 0, 360)
            mAnimation = ObjectAnimator.ofFloat(this,"mDynamicAngle",0f,360f)
        }
        mAnimation!!.duration = (mDownTime * 1000).toLong()
        mAnimation!!.start()

    }

    private fun cancelAnimation() {
        if (mAnimation != null && mAnimation!!.isRunning) {
            mAnimation!!.removeAllListeners()
            mAnimation!!.cancel()
            mAnimation = null
        }
    }

    fun setMDynamicAngle(downTime: Float) {
        this.mDynamicAngle = downTime
        invalidate()
    }

    fun setmText(text: String) {
        this.mText = text
    }

    fun setmDownTime(time: Int) {
        this.mDownTime = time
        invalidate()
    }

    fun setDefaultSize(defaultSize: Int) {
        this.defaultSize = defaultSize
        invalidate()
    }

    fun getDefaultSize(): Int {
        return defaultSize
    }

    fun setmBackGroundColor(mBackGroundColor: Int) {
        this.mBackGroundColor = mBackGroundColor
        invalidate()
    }

    fun setmArcColor(mArcColor: Int) {
        this.mArcColor = mArcColor
        invalidate()
    }

    fun setmTextColor(mTextColor: Int) {
        this.mTextColor = mTextColor
        invalidate()
    }

    fun setmTextSize(mTextSize: Float) {
        this.mTextSize = mTextSize
        invalidate()
    }

    fun getmDownTime(): Int {
        return mDownTime
    }

    fun getmText(): String? {
        return mText
    }

    fun getmBackGroundColor(): Int {
        return mBackGroundColor
    }

    fun getmArcColor(): Int {
        return mArcColor
    }

    fun getmTextColor(): Int {
        return mTextColor
    }

    fun getmTextSize(): Float {
        return mTextSize
    }

    fun setPCviewOnClickListener(pCviewOnClickListener: PCviewOnClickListener) {
        this.pcOnClickListener = pCviewOnClickListener
    }

    interface PCviewOnClickListener {
        fun onClick()
    }
}
