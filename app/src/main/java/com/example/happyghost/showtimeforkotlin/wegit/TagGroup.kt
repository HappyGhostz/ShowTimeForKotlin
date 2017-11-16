package com.example.happyghost.showtimeforkotlin.wegit

import android.content.Context
import android.graphics.*
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.ArrowKeyMovementMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnectionWrapper
import android.widget.TextView
import com.example.happyghost.showtimeforkotlin.R
import java.util.ArrayList

/**
 * @author Zhao Chenping
 * @creat 2017/11/15.
 * @description
 */
class TagGroup : ViewGroup {
    private val default_border_color = Color.rgb(0x49, 0xC1, 0x20)
    private val default_text_color = Color.rgb(0x49, 0xC1, 0x20)
    private val default_background_color = Color.WHITE
    private val default_dash_border_color = Color.rgb(0xAA, 0xAA, 0xAA)
    private val default_input_hint_color = Color.argb(0x80, 0x00, 0x00, 0x00)
    private val default_input_text_color = Color.argb(0xDE, 0x00, 0x00, 0x00)
    private val default_checked_border_color = Color.rgb(0x49, 0xC1, 0x20)
    private val default_checked_text_color = Color.WHITE
    private val default_checked_marker_color = Color.WHITE
    private val default_checked_background_color = Color.rgb(0x49, 0xC1, 0x20)
    private val default_pressed_background_color = Color.rgb(0xED, 0xED, 0xED)
    private val default_border_stroke_width: Float
    private val default_text_size: Float
    private val default_horizontal_spacing: Float
    private val default_vertical_spacing: Float
    private val default_horizontal_padding: Float
    private val default_vertical_padding: Float

    /** Indicates whether this TagGroup is set up to APPEND mode or DISPLAY mode. Default is false.  */
    private var isAppendMode: Boolean = false

    /** The text to be displayed when the text of the INPUT tag is empty.  */
    private var inputHint: CharSequence? = null

    /** The tag outline border color.  */
    private var borderColor: Int = 0

    /** The tag text color.  */
    private var textColor: Int = 0

    /** The tag background color.  */
    private var backgroundColorForTag: Int = 0

    /** The dash outline border color.  */
    private var dashBorderColor: Int = 0

    /** The  input tag hint text color.  */
    private var inputHintColor: Int = 0

    /** The input tag type text color.  */
    private var inputTextColor: Int = 0

    /** The checked tag outline border color.  */
    private var checkedBorderColor: Int = 0

    /** The check text color  */
    private var checkedTextColor: Int = 0

    /** The checked marker color.  */
    private var checkedMarkerColor: Int = 0

    /** The checked tag background color.  */
    private var checkedBackgroundColor: Int = 0

    /** The tag background color, when the tag is being pressed.  */
    private var pressedBackgroundColor: Int = 0

    /** The tag outline border stroke width, default is 0.5dp.  */
    private var borderStrokeWidth: Float = 0.toFloat()

    /** The tag text size, default is 13sp.  */
    private var textSize: Float = 0.toFloat()

    /** The horizontal tag spacing, default is 8.0dp.  */
    private var horizontalSpacing: Int = 0

    /** The vertical tag spacing, default is 4.0dp.  */
    private var verticalSpacing: Int = 0

    /** The horizontal tag padding, default is 12.0dp.  */
    private var horizontalPadding: Int = 0

    /** The vertical tag padding, default is 3.0dp.  */
    private var verticalPadding: Int = 0

    /** Listener used to dispatch tag change event.  */
    private var mOnTagChangeListener: OnTagChangeListener? = null

    /** Listener used to dispatch tag click event.  */
    private var mOnTagClickListener: OnTagClickListener? = null

    /** Listener used to handle tag click event.  */
    private val mInternalTagClickListener = InternalTagClickListener()

    constructor(context: Context) : this(context,null) {}
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,-1) {}
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        default_border_stroke_width = dp2px(0.5f)
        default_text_size = sp2px(13.0f)
        default_horizontal_spacing = dp2px(8.0f)
        default_vertical_spacing = dp2px(4.0f)
        default_horizontal_padding = dp2px(12.0f)
        default_vertical_padding = dp2px(3.0f)

        // Load styled attributes.
        val a = context.obtainStyledAttributes(attrs, R.styleable.TagGroup, defStyle, R.style.TagGroup)
        try {
            isAppendMode = a.getBoolean(R.styleable.TagGroup_atg_isAppendMode, false)
            inputHint = a.getText(R.styleable.TagGroup_atg_inputHint)
            borderColor = a.getColor(R.styleable.TagGroup_atg_borderColor, default_border_color)
            textColor = a.getColor(R.styleable.TagGroup_atg_textColor, default_text_color)
            backgroundColorForTag = a.getColor(R.styleable.TagGroup_atg_backgroundColor, default_background_color)
            dashBorderColor = a.getColor(R.styleable.TagGroup_atg_dashBorderColor, default_dash_border_color)
            inputHintColor = a.getColor(R.styleable.TagGroup_atg_inputHintColor, default_input_hint_color)
            inputTextColor = a.getColor(R.styleable.TagGroup_atg_inputTextColor, default_input_text_color)
            checkedBorderColor = a.getColor(R.styleable.TagGroup_atg_checkedBorderColor, default_checked_border_color)
            checkedTextColor = a.getColor(R.styleable.TagGroup_atg_checkedTextColor, default_checked_text_color)
            checkedMarkerColor = a.getColor(R.styleable.TagGroup_atg_checkedMarkerColor, default_checked_marker_color)
            checkedBackgroundColor = a.getColor(R.styleable.TagGroup_atg_checkedBackgroundColor, default_checked_background_color)
            pressedBackgroundColor = a.getColor(R.styleable.TagGroup_atg_pressedBackgroundColor, default_pressed_background_color)
            borderStrokeWidth = a.getDimension(R.styleable.TagGroup_atg_borderStrokeWidth, default_border_stroke_width)
            textSize = a.getDimension(R.styleable.TagGroup_atg_textSize, default_text_size)
            horizontalSpacing = a.getDimension(R.styleable.TagGroup_atg_horizontalSpacing, default_horizontal_spacing).toInt()
            verticalSpacing = a.getDimension(R.styleable.TagGroup_atg_verticalSpacing, default_vertical_spacing).toInt()
            horizontalPadding = a.getDimension(R.styleable.TagGroup_atg_horizontalPadding, default_horizontal_padding).toInt()
            verticalPadding = a.getDimension(R.styleable.TagGroup_atg_verticalPadding, default_vertical_padding).toInt()
        } finally {
            a.recycle()
        }

        if (isAppendMode) {
            // Append the initial INPUT tag.
            appendInputTag()

            // Set the click listener to detect the end-input event.
            setOnClickListener { submitTag() }
        }
    }
    /**
     * Returns the INPUT tag view in this group.
     *
     * @return the INPUT state tag view or null if not exists
     */
    fun getInputTag():TagView?{
        if (isAppendMode) {
            val inputTagIndex = childCount - 1
            val inputTag = getTagAt(inputTagIndex)
            return if (inputTag != null && inputTag.mState == STATE_INPUT) {
                inputTag
            } else {
                null
            }
        } else {
            return null
        }
    }
    /**
     * Returns the INPUT state tag in this group.
     *
     * @return the INPUT state tag view or null if not exists
     */
    val inputTagText: String?
        get() {
            val inputTagView = getInputTag()
            return inputTagView?.text?.toString()
        }

    /**
     * Return the last NORMAL state tag view in this group.
     *
     * @return the last NORMAL state tag view or null if not exists
     */
    protected fun getLastNormalTagView(): TagView {
        val lastNormalTagIndex = if (isAppendMode) childCount - 2 else childCount - 1
        return getTagAt(lastNormalTagIndex)!!
    }

    /**
     * Returns the tag array in group, except the INPUT tag.
     *
     * @return the tag array.
     */
    val tags: Array<String>
        get() {
            val count = childCount
            val tagList = ArrayList<String>()
            for (i in 0 until count) {
                val tagView = getTagAt(i)
                if (tagView!!.mState == STATE_NORMAL) {
                    tagList.add(tagView.text.toString())
                }
            }

            return tagList.toTypedArray()
        }

    /**
     * Returns the checked tag view in the group.
     *
     * @return the checked tag view or null if not exists.
     */
    protected fun getCheckedTag(): TagView? {
        val checkedTagIndex = getCheckedTagIndex()
        return if (checkedTagIndex != -1) {
            getTagAt(checkedTagIndex)
        } else null
    }

    /**
     * Return the checked tag index.
     *
     * @return the checked tag index, or -1 if not exists.
     */
    protected fun getCheckedTagIndex(): Int {
        val count = childCount
        for (i in 0 until count) {
            val tag = getTagAt(i)
            if (tag!!.isChecked) {
                return i
            }
        }
        return -1
    }

    /**
     * Call this to submit the INPUT tag.
     */
    fun submitTag() {
        val inputTag = getInputTag()
        if (inputTag != null && inputTag.isInputAvailable()) {
            inputTag.endInput()

            if (mOnTagChangeListener != null) {
                mOnTagChangeListener!!.onAppend(this@TagGroup, inputTag.text.toString())
            }
            appendInputTag()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        measureChildren(widthMeasureSpec, heightMeasureSpec)

        var width = 0
        var height = 0

        var row = 0 // The row counter.
        var rowWidth = 0 // Calc the current row width.
        var rowMaxHeight = 0 // Calc the max tag height, in current row.

        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            if (child.visibility != View.GONE) {
                rowWidth += childWidth
                if (rowWidth > widthSize) { // Next line.
                    rowWidth = childWidth // The next row width.
                    height += rowMaxHeight + verticalSpacing
                    rowMaxHeight = childHeight // The next row max height.
                    row++
                } else { // This line.
                    rowMaxHeight = Math.max(rowMaxHeight, childHeight)
                }
                rowWidth += horizontalSpacing
            }
        }
        // Account for the last row height.
        height += rowMaxHeight

        // Account for the padding too.
        height += paddingTop + paddingBottom

        // If the tags grouped in one row, set the width to wrap the tags.
        if (row == 0) {
            width = rowWidth
            width += paddingLeft + paddingRight
        } else {// If the tags grouped exceed one line, set the width to match the parent.
            width = widthSize
        }

        setMeasuredDimension(if (widthMode == View.MeasureSpec.EXACTLY) widthSize else width,
                if (heightMode == View.MeasureSpec.EXACTLY) heightSize else height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val parentLeft = paddingLeft
        val parentRight = r - l - paddingRight
        val parentTop = paddingTop
        val parentBottom = b - t - paddingBottom

        var childLeft = parentLeft
        var childTop = parentTop

        var rowMaxHeight = 0

        val count = childCount
        for (i in 0 until count) {
            val child = getChildAt(i)
            val width = child.measuredWidth
            val height = child.measuredHeight

            if (child.visibility != View.GONE) {
                if (childLeft + width > parentRight) { // Next line
                    childLeft = parentLeft
                    childTop += rowMaxHeight + verticalSpacing
                    rowMaxHeight = height
                } else {
                    rowMaxHeight = Math.max(rowMaxHeight, height)
                }
                child.layout(childLeft, childTop, childLeft + width, childTop + height)

                childLeft += width + horizontalSpacing
            }
        }
    }


    public override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val ss = SavedState(superState)
        ss.tags = tags
        ss.checkedPosition = getCheckedTagIndex()
        if (getInputTag() != null) {
            ss.input = getInputTag()!!.text.toString()
        }
        return ss
    }

    public override fun onRestoreInstanceState(state: Parcelable) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        val ss = SavedState(state)
        super.onRestoreInstanceState(state.superState)

        setTags(*ss.tags)
        val checkedTagView = getTagAt(state.checkedPosition)
        checkedTagView?.setCheckedTagView(true)
        if (getInputTag() != null) {
            getInputTag()!!.text = state.input
        }
    }

    /**
     * @see .setTags
     */
    fun setTags(tagList: List<String>) {
        setTags(*tagList.toTypedArray())
    }

    /**
     * Set the tags. It will remove all previous tags first.
     *
     * @param tags the tag list to set.
     */
    fun setTags(vararg tags: String) {
        setTags(null, *tags)
    }

    fun setTags(colors: List<TagColor>?, vararg tags: String) {
        removeAllViews()
        var i = 0
        for (tag in tags) {
            var color: TagColor? = null
            if (colors != null) {
                color = colors[i++]
            }
            appendTag(color, tag)
        }

        if (isAppendMode) {
            appendInputTag()
        }
    }

    /**
     * Returns the tag view at the specified position in the group.
     *
     * @param index the position at which to get the tag view from.
     * @return the tag view at the specified position or null if the position
     * does not exists within this group.
     */
    protected fun getTagAt(index: Int): TagView? {
        return getChildAt(index) as TagView
    }

    /**
     * Register a callback to be invoked when this tag group is changed.
     *
     * @param l the callback that will run
     */
    fun setOnTagChangeListener(l: OnTagChangeListener) {
        mOnTagChangeListener = l
    }

    /**
     * Append a INPUT tag to this group. It will throw an exception if there has a previous INPUT tag.
     *
     * @param tag the tag text.
     */
    @JvmOverloads protected fun appendInputTag(tag: String? = null) {
        appendInputTag(null, tag)
    }

    protected fun appendInputTag(color: TagColor?, tag: String?) {
        val previousInputTag = getInputTag()
        if (previousInputTag != null) {
            throw IllegalStateException("Already has a INPUT tag in group.")
        }

        val newInputTag = TagView(context, STATE_INPUT, color!!, tag!!)
        newInputTag.setOnClickListener(mInternalTagClickListener)
        addView(newInputTag)
    }

    /**
     * Append tag to this group.
     *
     * @param tag the tag to append.
     */
    protected fun appendTag(color: TagColor?, tag: CharSequence) {
        val newTag = TagView(context, STATE_NORMAL, color!!, tag)
        newTag.setOnClickListener(mInternalTagClickListener)
        addView(newTag)
    }

    fun dp2px(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                resources.displayMetrics)
    }

    fun sp2px(sp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                resources.displayMetrics)
    }

    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams {
        return TagGroup.LayoutParams(context, attrs)
    }

    /**
     * Register a callback to be invoked when a tag is clicked.
     *
     * @param l the callback that will run.
     */
    fun setOnTagClickListener(l: OnTagClickListener) {
        mOnTagClickListener = l
    }

    protected fun deleteTag(tagView: TagView) {
        removeView(tagView)
        if (mOnTagChangeListener != null) {
            mOnTagChangeListener!!.onDelete(this@TagGroup, tagView.text.toString())
        }
    }

    /**
     * Interface definition for a callback to be invoked when a tag group is changed.
     */
    interface OnTagChangeListener {
        /**
         * Called when a tag has been appended to the group.
         *
         * @param tag the appended tag.
         */
        fun onAppend(tagGroup: TagGroup, tag: String)

        /**
         * Called when a tag has been deleted from the the group.
         *
         * @param tag the deleted tag.
         */
        fun onDelete(tagGroup: TagGroup, tag: String)
    }

    /**
     * Interface definition for a callback to be invoked when a tag is clicked.
     */
    interface OnTagClickListener {
        /**
         * Called when a tag has been clicked.
         *
         * @param tag The tag text of the tag that was clicked.
         */
        fun onTagClick(tag: String)
    }

    /**
     * Per-child layout information for layouts.
     */
    class LayoutParams : ViewGroup.LayoutParams {
        constructor(c: Context, attrs: AttributeSet) : super(c, attrs) {}

        constructor(width: Int, height: Int) : super(width, height) {}
    }

    /**
     * For [TagGroup] save and restore state.
     */
    internal class SavedState : View.BaseSavedState {
        var tagCount: Int = 0
        lateinit var tags: Array<String>
        var checkedPosition: Int = 0
        var input: String? = null

        constructor(source: Parcel):super(source){
            tagCount = source!!.readInt()
            tags = source.createStringArray()
            checkedPosition = source.readInt()
            input = source.readString()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)
            parcel.writeInt(tagCount)
            parcel.writeStringArray(tags)
            parcel.writeInt(checkedPosition)
            parcel.writeString(input)
        }

        constructor(superState: Parcelable):super(superState) {
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }


    /**
     * The tag view click listener for internal use.
     */
    internal inner class InternalTagClickListener : View.OnClickListener {
        override fun onClick(v: View) {
            val tag = v as TagView
            if (isAppendMode) {
                if (tag.mState == STATE_INPUT) {
                    // If the clicked tag is in INPUT state, uncheck the previous checked tag if exists.
                    val checkedTag = getCheckedTag()
                    checkedTag?.setCheckedTagView(false)
                } else {
                    // If the clicked tag is currently checked, delete the tag.
                    if (tag.isChecked) {
                        deleteTag(tag)
                    } else {
                        // If the clicked tag is unchecked, uncheck the previous checked tag if exists,
                        // then check the clicked tag.
                        val checkedTag = getCheckedTag()
                        checkedTag?.setCheckedTagView(false)
                        tag.setCheckedTagView(true)
                    }
                }
            } else {
                if (mOnTagClickListener != null) {
                    mOnTagClickListener!!.onTagClick(tag.text.toString())
                }
            }
        }
    }
    public var STATE_NORMAL = 1
    public var STATE_INPUT = 2

    /** The offset to the text.  */
    public val CHECKED_MARKER_OFFSET = 3
    /** The stroke width of the checked marker  */
    public val CHECKED_MARKER_STROKE_WIDTH = 4
    /**
     * The tag view which has two states can be either NORMAL or INPUT.
     */
    inner class TagView: TextView {

        /** Indicates the tag if checked.  */
        var isChecked = false
        /** The current state.  */
        var mState: Int = 0

        /** Indicates the tag if pressed.  */
        var isPress:Boolean = false

        private val mBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        private val mBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        private val mCheckedMarkerPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        /** The rect for the tag's left corner drawing.  */
        private val mLeftCornerRectF = RectF()

        /** The rect for the tag's right corner drawing.  */
        private val mRightCornerRectF = RectF()

        /** The rect for the tag's horizontal blank fill area.  */
        private val mHorizontalBlankFillRectF = RectF()

        /** The rect for the tag's vertical blank fill area.  */
        private val mVerticalBlankFillRectF = RectF()

        /** The rect for the checked mark draw bound.  */
        private val mCheckedMarkerBound = RectF()

        /** Used to detect the touch event.  */
        private val mOutRect = Rect()

        /** The path for draw the tag's outline border.  */
        private val mBorderPath = Path()
        /** The path effect provide draw the dash border.  */
        private var mPathEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
        init{
            mBorderPaint.style = Paint.Style.STROKE
            mBorderPaint.strokeWidth = borderStrokeWidth
            mBackgroundPaint.style = Paint.Style.FILL
            mCheckedMarkerPaint.style = Paint.Style.FILL
            mCheckedMarkerPaint.strokeWidth = CHECKED_MARKER_STROKE_WIDTH.toFloat()
            mCheckedMarkerPaint.color = checkedMarkerColor
        }

        private val color: TagColor?
        /**
         * Indicates whether the input content is available.
         *
         * @return True if the input content is available, false otherwise.
         */
        fun isInputAvailable(): Boolean {
            return text != null && text.isNotEmpty()
        }
        constructor(context: Context, state: Int, color: TagColor, text: CharSequence):super(context) {
            this.color = color
            setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)
            layoutParams = TagGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)

            gravity = Gravity.CENTER
            setText(text)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)

            mState = state

            isClickable = isAppendMode
            isFocusable = state == STATE_INPUT
            isFocusableInTouchMode = state == STATE_INPUT
            hint = if (state == STATE_INPUT) inputHint else null
            movementMethod = if (state == STATE_INPUT) ArrowKeyMovementMethod.getInstance() else null

            // Interrupted long click event to avoid PAUSE popup.
            setOnLongClickListener { state != STATE_INPUT }

            if (state == STATE_INPUT) {
                requestFocus()

                // Handle the ENTER key down.
                setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
                    if (actionId == EditorInfo.IME_NULL && (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER
                            && event.action == KeyEvent.ACTION_DOWN)) {
                        if (isInputAvailable()) {
                            // If the input content is available, end the input and dispatch
                            // the event, then append a new INPUT state tag.
                            endInput()
                            if (mOnTagChangeListener != null) {
                                mOnTagChangeListener!!.onAppend(this@TagGroup, getText().toString())
                            }
                            appendInputTag()
                        }
                        return@OnEditorActionListener true
                    }
                    false
                })

                // Handle the BACKSPACE key down.
                setOnKeyListener(OnKeyListener { v, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                        // If the input content is empty, check or remove the last NORMAL state tag.
                        if (TextUtils.isEmpty(getText().toString())) {
                            val lastNormalTagView = getLastNormalTagView()
                            if (lastNormalTagView != null) {
                                if (lastNormalTagView!!.isChecked) {
                                    removeView(lastNormalTagView)
                                    if (mOnTagChangeListener != null) {
                                        mOnTagChangeListener!!.onDelete(this@TagGroup, lastNormalTagView!!.text.toString())
                                    }
                                } else {
                                    val checkedTagView = getCheckedTag()
                                    if (checkedTagView != null) {
                                        checkedTagView!!.setCheckedTagView(false)
                                    }
                                    lastNormalTagView!!.setCheckedTagView(true)
                                }
                                return@OnKeyListener true
                            }
                        }
                    }
                    false
                })

                // Handle the INPUT tag content changed.
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                        // When the INPUT state tag changed, uncheck the checked tag if exists.
                        val checkedTagView = getCheckedTag()
                        if (checkedTagView != null) {
                            checkedTagView!!.setCheckedTagView(false)
                        }
                    }

                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

                    override fun afterTextChanged(s: Editable) {}
                })
            }

            invalidatePaint()
        }

        /**
         * Set whether this tag view is in the checked state.
         *
         * @param checked true is checked, false otherwise
         */
        fun setCheckedTagView(checked: Boolean) {
            isChecked = checked
            // Make the checked mark drawing region.
            setPadding(horizontalPadding,
                    verticalPadding,
                    if (isChecked)
                        (horizontalPadding.toFloat() + height / 2.5f + CHECKED_MARKER_OFFSET.toFloat()).toInt()
                    else
                        horizontalPadding,
                    verticalPadding)
            invalidatePaint()
        }

        /**
         * Call this method to end this tag's INPUT state.
         */
        fun endInput() {
            // Make the view not focusable.
            isFocusable = false
            isFocusableInTouchMode = false
            // Set the hint empty, make the TextView measure correctly.
            hint = null
            // Take away the cursor.
            movementMethod = null

            mState = STATE_NORMAL
            invalidatePaint()
            requestLayout()
        }

        override fun getDefaultEditable(): Boolean {
            return true
        }

        private fun invalidatePaint() {
            if (isAppendMode) {
                if (mState == STATE_INPUT) {
                    mBorderPaint.color = dashBorderColor
                    mBorderPaint.pathEffect = mPathEffect
                    mBackgroundPaint.color = backgroundColorForTag
                    setHintTextColor(inputHintColor)
                    setTextColor(inputTextColor)
                } else {
                    mBorderPaint.pathEffect = null
                    if (isChecked) {
                        mBorderPaint.color = checkedBorderColor
                        mBackgroundPaint.color = checkedBackgroundColor
                        setTextColor(checkedTextColor)
                    } else {
                        mBorderPaint.color = borderColor
                        mBackgroundPaint.color = backgroundColorForTag
                        setTextColor(textColor)
                    }
                }
            } else {
                if (color != null) {
                    mBorderPaint.color = color!!.borderColor
                    mBackgroundPaint.color = color!!.backgroundColor
                    setTextColor(color!!.textColor)
                } else {
                    mBorderPaint.color = borderColor
                    mBackgroundPaint.color = backgroundColorForTag
                    setTextColor(textColor)
                }
            }

            if (isPress) {
                mBackgroundPaint.color = pressedBackgroundColor
            }
        }

        override fun onDraw(canvas: Canvas) {
            //canvas.drawArc(mLeftCornerRectF, -180, 90, true, mBackgroundPaint);
            //canvas.drawArc(mLeftCornerRectF, -270, 90, true, mBackgroundPaint);
            //canvas.drawArc(mRightCornerRectF, -90, 90, true, mBackgroundPaint);
            //canvas.drawArc(mRightCornerRectF, 0, 90, true, mBackgroundPaint);
            canvas.drawRect(mHorizontalBlankFillRectF, mBackgroundPaint)
            canvas.drawRect(mVerticalBlankFillRectF, mBackgroundPaint)

            if (isChecked) {
                canvas.save()
                canvas.rotate(45f, mCheckedMarkerBound.centerX(), mCheckedMarkerBound.centerY())
                canvas.drawLine(mCheckedMarkerBound.left, mCheckedMarkerBound.centerY(),
                        mCheckedMarkerBound.right, mCheckedMarkerBound.centerY(), mCheckedMarkerPaint)
                canvas.drawLine(mCheckedMarkerBound.centerX(), mCheckedMarkerBound.top,
                        mCheckedMarkerBound.centerX(), mCheckedMarkerBound.bottom, mCheckedMarkerPaint)
                canvas.restore()
            }
            canvas.drawPath(mBorderPath, mBorderPaint)
            super.onDraw(canvas)
        }

        override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
            var h = h
            super.onSizeChanged(w, h, oldw, oldh)
            val left = borderStrokeWidth.toInt()
            val top = borderStrokeWidth.toInt()
            val right = (left + w - borderStrokeWidth * 2).toInt()
            val bottom = (top + h - borderStrokeWidth * 2).toInt()

            val d = 0//bottom - top;

            mLeftCornerRectF.set(left.toFloat(), top.toFloat(), (left + d).toFloat(), (top + d).toFloat())
            mRightCornerRectF.set((right - d).toFloat(), top.toFloat(), right.toFloat(), (top + d).toFloat())

            mBorderPath.reset()
            //mBorderPath.addArc(mLeftCornerRectF, -180, 90);
            //mBorderPath.addArc(mLeftCornerRectF, -270, 90);
            //mBorderPath.addArc(mRightCornerRectF, -90, 90);
            //mBorderPath.addArc(mRightCornerRectF, 0, 90);

            val l = (d / 2.0f).toInt()
            mBorderPath.moveTo((left + l).toFloat(), top.toFloat())
            mBorderPath.lineTo((right - l).toFloat(), top.toFloat())

            mBorderPath.moveTo((left + l).toFloat(), bottom.toFloat())
            mBorderPath.lineTo((right - l).toFloat(), bottom.toFloat())

            mBorderPath.moveTo(left.toFloat(), (top + l).toFloat())
            mBorderPath.lineTo(left.toFloat(), (bottom - l).toFloat())

            mBorderPath.moveTo(right.toFloat(), (top + l).toFloat())
            mBorderPath.lineTo(right.toFloat(), (bottom - l).toFloat())

            mHorizontalBlankFillRectF.set(left.toFloat(), (top + l).toFloat(), right.toFloat(), (bottom - l).toFloat())
            mVerticalBlankFillRectF.set((left + l).toFloat(), top.toFloat(), (right - l).toFloat(), bottom.toFloat())

            val m = (h / 2.5f).toInt()
            h = bottom - top
            mCheckedMarkerBound.set((right - m - horizontalPadding + CHECKED_MARKER_OFFSET).toFloat(),
                    (top + h / 2 - m / 2).toFloat(),
                    (right - horizontalPadding + CHECKED_MARKER_OFFSET).toFloat(),
                    (bottom - h / 2 + m / 2).toFloat())

            // Ensure the checked mark drawing region is correct across screen orientation changes.
            if (isChecked) {
                setPadding(horizontalPadding,
                        verticalPadding,
                        (horizontalPadding.toFloat() + h / 2.5f + CHECKED_MARKER_OFFSET.toFloat()).toInt(),
                        verticalPadding)
            }
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            if (mState == STATE_INPUT) {
                // The INPUT tag doesn't change background color on the touch event.
                return super.onTouchEvent(event)
            }

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    getDrawingRect(mOutRect)
                    isPress = true
                    invalidatePaint()
                    invalidate()
                }
                MotionEvent.ACTION_MOVE -> {
                    if (!mOutRect.contains(event.x.toInt(), event.y.toInt())) {
                        isPress = false
                        invalidatePaint()
                        invalidate()
                    }
                }
                MotionEvent.ACTION_UP -> {
                    isPress = false
                    invalidatePaint()
                    invalidate()
                }
            }
            return super.onTouchEvent(event)
        }

        override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
            return ZanyInputConnection(super.onCreateInputConnection(outAttrs), true)
        }

        /**
         * Solve edit text delete(backspace) key detect, see[
 * Android: Backspace in WebView/BaseInputConnection](http://stackoverflow.com/a/14561345/3790554)
         */
        private inner class ZanyInputConnection(target: android.view.inputmethod.InputConnection, mutable: Boolean) : InputConnectionWrapper(target, mutable) {

            override fun deleteSurroundingText(beforeLength: Int, afterLength: Int): Boolean {
                // magic: in latest Android, deleteSurroundingText(1, 0) will be called for backspace
                return if (beforeLength == 1 && afterLength == 0) {
                    // backspace
                    sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL)) && sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL))
                } else super.deleteSurroundingText(beforeLength, afterLength)
            }
        }
    }
}
/**
 * @see .appendInputTag
 */
