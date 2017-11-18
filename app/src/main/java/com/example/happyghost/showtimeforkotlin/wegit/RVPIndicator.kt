package com.example.happyghost.showtimeforkotlin.wegit

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.NinePatchDrawable
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.happyghost.showtimeforkotlin.R

/**
 * @author Zhao Chenping
 * @creat 2017/11/17.
 * @description
 */
class RVPIndicator @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    /**
     * tab上的内容
     */
    private var mTabTitles: List<String>? = null

    /**
     * 可见tab数量
     */
    private var mTabVisibleCount = D_TAB_COUNT

    /**
     * 与之绑定的ViewPager
     */
    lateinit var mViewPager: ViewPager

    /**
     * 文字大小
     */
    private var mTextSize = 16

    /**
     * 文字正常时的颜色
     */
    private var mTextColorNormal = D_TEXT_COLOR_NORMAL

    /**
     * 文字选中时的颜色
     */
    private var mTextColorHighlight = D_TEXT_COLOR_HIGHLIGHT

    /**
     * 指示器正常时的颜色
     */
    private var mIndicatorColor = D_INDICATOR_COLOR

    /**
     * 画笔
     */
    private val mPaint: Paint

    /**
     * 矩形
     */
    private var mRectF: Rect? = null

    /**
     * bitmap
     */
    private var mBitmap: Bitmap? = null

    /**
     * 指示器高
     */
    private var mIndicatorHeight = 5

    /**
     * 指示器宽
     */
    private var mIndicatorWidth = width / mTabVisibleCount

    /**
     * 手指滑动时的偏移量
     */
    private var mTranslationX: Float = 0.toFloat()

    /**
     * 指示器风格
     */
    private var mIndicatorStyle = STYLE_LINE

    /**
     * 曲线path
     */
    private var mPath: Path? = null

    /**
     * viewPage初始下标
     */
    private var mPosition = 0

    // 对外的ViewPager的回调接口
    private var onPageChangeListener: PageChangeListener? = null

    init {
        // 获得自定义属性
        val a = context.obtainStyledAttributes(attrs,
                R.styleable.RVPIndicator)

        mTabVisibleCount = a.getInt(R.styleable.RVPIndicator_item_count,
                D_TAB_COUNT)
        mTextColorNormal = a
                .getColor(R.styleable.RVPIndicator_text_color_normal,
                        D_TEXT_COLOR_NORMAL)
        mTextColorHighlight = a.getColor(
                R.styleable.RVPIndicator_text_color_hightlight,
                D_TEXT_COLOR_HIGHLIGHT)
        mTextSize = a.getDimensionPixelSize(R.styleable.RVPIndicator_text_size,
                16)
        mIndicatorColor = a.getColor(R.styleable.RVPIndicator_indicator_color,
                D_INDICATOR_COLOR)
        mIndicatorStyle = a.getInt(R.styleable.RVPIndicator_indicator_style,
                STYLE_LINE)

        val drawable = a
                .getDrawable(R.styleable.RVPIndicator_indicator_src)

        if (drawable != null) {
            if (drawable is BitmapDrawable) {
                mBitmap = drawable.bitmap
            } else if (drawable is NinePatchDrawable) {
                // .9图处理
                val bitmap = Bitmap.createBitmap(
                        drawable.intrinsicWidth,
                        drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
                mBitmap = bitmap

            }
        } else {
            mBitmap = BitmapFactory.decodeResource(resources,
                    R.mipmap.heart_love)
        }

        a.recycle()

        /**
         * 设置画笔
         */
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.color = mIndicatorColor
        mPaint.style = Paint.Style.FILL

    }

    /**
     * 初始化指示器
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        when (mIndicatorStyle) {

            STYLE_LINE -> {

                /*
			 * 下划线指示器:宽与item相等,高是item的1/10
			 */
                mIndicatorWidth = w / mTabVisibleCount
                mIndicatorHeight = h / 10
                mTranslationX = 0f
                mRectF = Rect(0, 0, mIndicatorWidth, mIndicatorHeight)
            }
            STYLE_SQUARE, STYLE_BITMAP -> {

                /*
			 * 方形/图标指示器:宽,高与item相等
			 */
                mIndicatorWidth = w / mTabVisibleCount
                mIndicatorHeight = h
                mTranslationX = 0f
                mRectF = Rect(0, 0, mIndicatorWidth, mIndicatorHeight)
            }
            STYLE_TRIANGLE -> {

                /*
			 * 三角形指示器:宽与item(1/4)相等,高是item的1/4
			 */
                //mIndicatorWidth = w / mTabVisibleCount / 4;
                // mIndicatorHeight = h / 4;
                mIndicatorWidth = (w / mTabVisibleCount * RADIO_TRIANGEL).toInt()// 1/6 of  width  ;
                mIndicatorHeight = (mIndicatorWidth.toDouble() / 2.0 / Math.sqrt(2.0)).toInt()
                mTranslationX = 0f
            }
        }
        // 初始化tabItem
        initTabItem()

        // 高亮
        setHighLightTextView(mPosition)
    }

    /**
     * 绘制指示器(子view)
     */
    override fun dispatchDraw(canvas: Canvas) {
        // 保存画布
        canvas.save()

        when (mIndicatorStyle) {

            STYLE_BITMAP -> {

                canvas.translate(mTranslationX, 0f)
                canvas.drawBitmap(mBitmap!!, null, mRectF!!, mPaint)
            }
            STYLE_LINE -> {

                canvas.translate(mTranslationX, (height - mIndicatorHeight).toFloat())
                canvas.drawRect(mRectF!!, mPaint)
            }
            STYLE_SQUARE -> {

                canvas.translate(mTranslationX, 0f)
                canvas.drawRect(mRectF!!, mPaint)
            }
            STYLE_TRIANGLE -> {

                canvas.translate(mTranslationX, 0f)
                // 笔锋圆滑度
                // mPaint.setPathEffect(new CornerPathEffect(10));
                mPath = Path()
                val midOfTab = width / mTabVisibleCount / 2
                mPath!!.moveTo(midOfTab.toFloat(), (height - mIndicatorHeight).toFloat())
                mPath!!.lineTo((midOfTab - mIndicatorWidth / 2).toFloat(), height.toFloat())
                mPath!!.lineTo((midOfTab + mIndicatorWidth / 2).toFloat(), height.toFloat())
                mPath!!.close()
                canvas.drawPath(mPath!!, mPaint)
            }
        }

        // 恢复画布
        canvas.restore()
        super.dispatchDraw(canvas)
    }

    /**
     * 设置tab上的内容
     *
     * @param datas
     */
    fun setTabItemTitles(datas: List<String>) {
        this.mTabTitles = datas
    }

    /**
     * 设置关联viewPager
     *
     * @param viewPager
     * @param pos
     */
    fun setViewPager(viewPager: ViewPager, pos: Int) {
        this.mViewPager = viewPager

        mViewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                // 设置文本高亮
                setHighLightTextView(position)

                // 回调
                if (onPageChangeListener != null) {
                    onPageChangeListener!!.onPageSelected(position)
                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float,
                                        positionOffsetPixels: Int) {

                // scoll
                onScoll(position, positionOffset)

                // 回调
                if (onPageChangeListener != null) {
                    onPageChangeListener!!.onPageScrolled(position,
                            positionOffset, positionOffsetPixels)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                // 回调
                if (onPageChangeListener != null) {
                    onPageChangeListener!!.onPageScrollStateChanged(state)
                }
            }
        })

        // 设置当前页
        mViewPager.currentItem = pos
        mPosition = pos

    }

    /**
     *
     * 初始化tabItem
     *
     * @author Ruffian
     */
    private fun initTabItem() {

        if (mTabTitles != null && mTabTitles!!.size > 0) {
            if (this.childCount != 0) {
                this.removeAllViews()
            }

            for (title in mTabTitles!!) {
                addView(createTextView(title))
            }

            // 设置点击事件
            setItemClickEvent()
        }

    }

    /**
     * 设置点击事件
     */
    private fun setItemClickEvent() {
        val cCount = childCount
        for (i in 0 until cCount) {
            val view = getChildAt(i)
            view.setOnClickListener { mViewPager.currentItem = i }
        }
    }

    /**
     * 设置文本高亮
     *
     * @param position
     */
    private fun setHighLightTextView(position: Int) {

        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (view is TextView) {
                if (i == position) {
                    view.setTextColor(mTextColorHighlight)
                } else {
                    view.setTextColor(mTextColorNormal)
                }
            }
        }
    }

    /**
     * 滚动
     *
     * @param position
     * @param offset
     */
    private fun onScoll(position: Int, offset: Float) {

        // 不断改变偏移量，invalidate
        mTranslationX = width / mTabVisibleCount * (position + offset)

        val tabWidth = width / mTabVisibleCount

        // 容器滚动，当移动到倒数第二个的时候，开始滚动
        if (offset > 0 && position >= mTabVisibleCount - 2
                && childCount > mTabVisibleCount
                && position < childCount - 2) {
            if (mTabVisibleCount != 1) {

                val xValue = (position - (mTabVisibleCount - 2)) * tabWidth + (tabWidth * offset).toInt()
                this.scrollTo(xValue, 0)

            } else {
                // 为count为1时 的特殊处理
                this.scrollTo(position * tabWidth + (tabWidth * offset).toInt(),
                        0)
            }
        }

        invalidate()
    }

    /**
     * 创建标题view
     *
     * @param text
     * @return
     */
    private fun createTextView(text: String): TextView {
        val tv = TextView(context)
        val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        lp.width = width / mTabVisibleCount
        tv.gravity = Gravity.CENTER
        tv.setTextColor(mTextColorNormal)
        tv.text = text
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize.toFloat())
        tv.layoutParams = lp
        return tv
    }

    /**
     * 对外的ViewPager的回调接口
     *
     * @author Ruffian
     */
    interface PageChangeListener {
        fun onPageScrolled(position: Int, positionOffset: Float,
                           positionOffsetPixels: Int)

        fun onPageSelected(position: Int)

        fun onPageScrollStateChanged(state: Int)
    }

    // 对外的ViewPager的回调接口的设置
    fun setOnPageChangeListener(pageChangeListener: PageChangeListener) {
        this.onPageChangeListener = pageChangeListener
    }

    companion object {

        // 指示器风格-图标
        private val STYLE_BITMAP = 0
        // 指示器风格-下划线
        private val STYLE_LINE = 1
        // 指示器风格-方形背景
        private val STYLE_SQUARE = 2
        // 指示器风格-三角形
        private val STYLE_TRIANGLE = 3

        /*
     * 系统默认:Tab数量
     */
        private val D_TAB_COUNT = 3

        /*
     * 系统默认:文字正常时颜色
     */
        private val D_TEXT_COLOR_NORMAL = Color.parseColor("#000000")

        /*
     * 系统默认:文字选中时颜色
     */
        private val D_TEXT_COLOR_HIGHLIGHT = Color
                .parseColor("#FF0000")

        /*
     * 系统默认:指示器颜色
     */
        private val D_INDICATOR_COLOR = Color.parseColor("#f29b76")

        /**
         * 三角形的宽度为单个Tab的1/6
         */
        private val RADIO_TRIANGEL = 1.0f / 6
    }

}
