package com.example.happyghost.showtimeforkotlin.wegit

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.Toast
import com.example.happyghost.showtimeforkotlin.R

/**
 * @author Zhao Chenping
 * @creat 2017/11/6.
 * @description
 */
class SelectCricleImageView :ImageView{
    val SCALE_TYPE = ImageView.ScaleType.CENTER_CROP

    val BITMAP_CONFIG = Bitmap.Config.ARGB_8888
    val COLORDRAWABLE_DIMENSION = 2

    val DEFAULT_BORDER_WIDTH = 0
    val DEFAULT_BORDER_COLOR = Color.BLACK
    val DEFAULT_FILL_COLOR = Color.TRANSPARENT
    val DEFAULT_BORDER_OVERLAY = false
    val DEFAULT_ANIMATION_END = 1

    val mDrawableRect = RectF()
    val mBorderRect = RectF()

    val mShaderMatrix = Matrix()
    val mBitmapPaint = Paint()
    val mBorderPaint = Paint()
    val mFillPaint = Paint()

    var mBorderColor = DEFAULT_BORDER_COLOR
    var mBorderWidth = DEFAULT_BORDER_WIDTH
    var mFillColor = DEFAULT_FILL_COLOR

    var mBitmap: Bitmap? = null
    var mBitmapShader: BitmapShader? = null
    var mBitmapWidth: Int = 0
    var mBitmapHeight: Int = 0

    var mDrawableRadius: Float = 0.toFloat()
    var mBorderRadius: Float = 0.toFloat()

    var mColorFilter: ColorFilter? = null

    var mReady: Boolean = false
    var mSetupPending: Boolean = false
    var mBorderOverlay: Boolean = false
    var mDisableCircularTransformation: Boolean = false
    var objectAnimator: ObjectAnimator? = null
    private var mDynamicAngle: Float = 0.toFloat()
    var mTimeEnd = 0

    constructor(context: Context): super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet): this(context, attrs, 0) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle) {


        val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyle, 0)

        mBorderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_civ_border_width, DEFAULT_BORDER_WIDTH)
        mBorderColor = a.getColor(R.styleable.CircleImageView_civ_border_color, DEFAULT_BORDER_COLOR)
        mBorderOverlay = a.getBoolean(R.styleable.CircleImageView_civ_border_overlay, DEFAULT_BORDER_OVERLAY)
        mFillColor = a.getColor(R.styleable.CircleImageView_civ_fill_color, DEFAULT_FILL_COLOR)
        mTimeEnd = a.getInt(R.styleable.CircleImageView_civ_animation_end,DEFAULT_ANIMATION_END)
        a.recycle()

        init()
        initClickListener(context)
    }

    private fun initClickListener(context: Context) {
        val onClickListener = OnClickListener {
            if(mBorderWidth>0&&mDynamicAngle<360){
                startAnimation()
            }else{
                Toast.makeText(context,"已选择!", Toast.LENGTH_SHORT).show()
            }
        }
        setOnClickListener(onClickListener)
    }

    private fun init() {
        super.setScaleType(SCALE_TYPE)
        mReady = true

        if (mSetupPending) {
            setup()
            mSetupPending = false
        }
    }

    override fun getScaleType(): ImageView.ScaleType {
        return SCALE_TYPE
    }

    override fun setScaleType(scaleType: ImageView.ScaleType) {
        if (scaleType != SCALE_TYPE) {
            throw IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType))
        }
    }

    override fun setAdjustViewBounds(adjustViewBounds: Boolean) {
        if (adjustViewBounds) {
            throw IllegalArgumentException("adjustViewBounds not supported.")
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (mDisableCircularTransformation) {
            super.onDraw(canvas)
            return
        }

        if (mBitmap == null) {
            return
        }


        if (mFillColor != Color.TRANSPARENT) {
            canvas.drawCircle(mDrawableRect.centerX(), mDrawableRect.centerY(), mDrawableRadius-mBorderWidth*2, mFillPaint)
        }
        canvas.drawCircle(mDrawableRect.centerX(), mDrawableRect.centerY(), mDrawableRadius-mBorderWidth*2, mBitmapPaint)
        if (mBorderWidth > 0) {
//            canvas.drawCircle(mBorderRect.centerX(), mBorderRect.centerY(), mBorderRadius, mBorderPaint)
            val rectF = RectF(mBorderWidth.toFloat(), mBorderWidth.toFloat(),
                    mBorderRadius*2 , mBorderRadius*2)
            canvas.drawArc(rectF,0f,mDynamicAngle,false,mBorderPaint)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setup()
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
        setup()
    }

    override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int) {
        super.setPaddingRelative(start, top, end, bottom)
        setup()
    }

    fun getBorderColor(): Int {
        return mBorderColor
    }

    fun setBorderColor(@ColorInt borderColor: Int) {
        if (borderColor == mBorderColor) {
            return
        }

        mBorderColor = borderColor
        mBorderPaint.color = mBorderColor
        invalidate()
    }


    @Deprecated("Use {@link #setBorderColor(int)} instead")
    fun setBorderColorResource(@ColorRes borderColorRes: Int) {
        setBorderColor(context.resources.getColor(borderColorRes))
    }

    /**
     * Return the color drawn behind the circle-shaped drawable.
     *
     * @return The color drawn behind the drawable
     *
     */
    @Deprecated("Fill color support is going to be removed in the future")
    fun getFillColor(): Int {
        return mFillColor
    }

    /**
     * Set a color to be drawn behind the circle-shaped drawable. Note that
     * this has no effect if the drawable is opaque or no drawable is set.
     *
     * @param fillColor The color to be drawn behind the drawable
     *
     */
    @Deprecated("Fill color support is going to be removed in the future")
    fun setFillColor(@ColorInt fillColor: Int) {
        if (fillColor == mFillColor) {
            return
        }

        mFillColor = fillColor
        mFillPaint.color = fillColor
        invalidate()
    }

    /**
     * Set a color to be drawn behind the circle-shaped drawable. Note that
     * this has no effect if the drawable is opaque or no drawable is set.
     *
     * @param fillColorRes The color resource to be resolved to a color and
     * drawn behind the drawable
     *
     */
    @Deprecated("Fill color support is going to be removed in the future")
    fun setFillColorResource(@ColorRes fillColorRes: Int) {
        setFillColor(context.resources.getColor(fillColorRes))
    }

    fun getBorderWidth(): Int {
        return mBorderWidth
    }

    fun setBorderWidth(borderWidth: Int) {
        if (borderWidth == mBorderWidth) {
            return
        }

        mBorderWidth = borderWidth
        setup()
    }

    fun isBorderOverlay(): Boolean {
        return mBorderOverlay
    }

    fun setBorderOverlay(borderOverlay: Boolean) {
        if (borderOverlay == mBorderOverlay) {
            return
        }

        mBorderOverlay = borderOverlay
        setup()
    }

    fun isDisableCircularTransformation(): Boolean {
        return mDisableCircularTransformation
    }

    fun setDisableCircularTransformation(disableCircularTransformation: Boolean) {
        if (mDisableCircularTransformation == disableCircularTransformation) {
            return
        }

        mDisableCircularTransformation = disableCircularTransformation
        initializeBitmap()
    }

    override fun setImageBitmap(bm: Bitmap) {
        super.setImageBitmap(bm)
        initializeBitmap()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        initializeBitmap()
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resId)
        initializeBitmap()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        initializeBitmap()
    }

    override fun setColorFilter(cf: ColorFilter) {
        if (cf === mColorFilter) {
            return
        }

        mColorFilter = cf
        applyColorFilter()
        invalidate()
    }

    override fun getColorFilter(): ColorFilter? {
        return mColorFilter
    }

    private fun applyColorFilter() {
        if (mBitmapPaint != null) {
            mBitmapPaint.colorFilter = mColorFilter
        }
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }

        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        try {
            val bitmap: Bitmap

            if (drawable is ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG)
            } else {
                bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, BITMAP_CONFIG)
            }

            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    private fun initializeBitmap() {
        if (mDisableCircularTransformation) {
            mBitmap = null
        } else {
            mBitmap = getBitmapFromDrawable(drawable)
        }
        setup()
    }

    private fun setup() {
        if (!mReady) {
            mSetupPending = true
            return
        }

        if (width == 0 && height == 0) {
            return
        }

        if (mBitmap == null) {
            invalidate()
            return
        }

        mBitmapShader = BitmapShader(mBitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        mBitmapPaint.isAntiAlias = true
        mBitmapPaint.shader = mBitmapShader

        mBorderPaint.style = Paint.Style.STROKE
        mBorderPaint.isAntiAlias = true
        mBorderPaint.color = mBorderColor
        mBorderPaint.strokeWidth = mBorderWidth.toFloat()

        mFillPaint.style = Paint.Style.FILL
        mFillPaint.isAntiAlias = true
        mFillPaint.color = mFillColor

        mBitmapHeight = mBitmap!!.height
        mBitmapWidth = mBitmap!!.width

        mBorderRect.set(calculateBounds())
        mBorderRadius = Math.min((mBorderRect.height() - mBorderWidth) / 2.0f, (mBorderRect.width() - mBorderWidth) / 2.0f)

        mDrawableRect.set(mBorderRect)
        if (!mBorderOverlay && mBorderWidth > 0) {
            mDrawableRect.inset(mBorderWidth - 1.0f, mBorderWidth - 1.0f)
        }
        mDrawableRadius = Math.min(mDrawableRect.height() / 2.0f, mDrawableRect.width() / 2.0f)

        applyColorFilter()
        updateShaderMatrix()
        invalidate()
    }

    private fun calculateBounds(): RectF {
        val availableWidth = width - paddingLeft - paddingRight
        val availableHeight = height - paddingTop - paddingBottom

        val sideLength = Math.min(availableWidth, availableHeight)

        val left = paddingLeft + (availableWidth - sideLength) / 2f
        val top = paddingTop + (availableHeight - sideLength) / 2f

        return RectF(left, top, left + sideLength, top + sideLength)
    }

    private fun updateShaderMatrix() {
        val scale: Float
        var dx = 0f
        var dy = 0f

        mShaderMatrix.set(null)

        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            scale = mDrawableRect.height() / mBitmapHeight.toFloat()
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f
        } else {
            scale = mDrawableRect.width() / mBitmapWidth.toFloat()
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f
        }

        mShaderMatrix.setScale(scale, scale)
        mShaderMatrix.postTranslate((dx + 0.5f).toInt() + mDrawableRect.left, (dy + 0.5f).toInt() + mDrawableRect.top)

        mBitmapShader!!.setLocalMatrix(mShaderMatrix)
    }
    /**
     * 开始动画
     */
    fun startAnimation() {
        if (objectAnimator == null) {
//            mAnimation = ObjectAnimator.ofFloat(this, "mDynamicAngle", 0, 360)
            objectAnimator = ObjectAnimator.ofFloat(this,"mDynamicAngle",0f,360f)
        }
        objectAnimator!!.duration = (mTimeEnd * 1000).toLong()
        objectAnimator!!.start()

    }
    fun setMDynamicAngle(downTime: Float) {
        this.mDynamicAngle = downTime
        invalidate()
    }
    fun getMDynamicAngle():Float {
       return mDynamicAngle
    }
    //view销毁的时候执行该方法
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancelAnimation()
    }

    private fun cancelAnimation() {
        if (objectAnimator != null && objectAnimator!!.isRunning) {
            objectAnimator!!.removeAllListeners()
            objectAnimator!!.cancel()
        }
    }
}