package com.example.happyghost.showtimeforkotlin.wegit.read

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.R
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookMixATocBean
import com.example.happyghost.showtimeforkotlin.utils.FileUtils
import com.example.happyghost.showtimeforkotlin.utils.ScreenUtils
import com.example.happyghost.showtimeforkotlin.utils.SettingManager
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.io.UnsupportedEncodingException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.nio.charset.Charset
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Zhao Chenping
 * @creat 2017/9/30.
 * @description
 */
public class PageFactory {
    private var mContext: Context? = null
    /**
     * 屏幕宽高
     */
    private var mHeight: Int = 0
    private var mWidth: Int = 0
    /**
     * 文字区域宽高
     */
    private var mVisibleHeight: Int = 0
    private var mVisibleWidth: Int = 0
    /**
     * 间距
     */
    private var marginHeight: Int = 0
    private var marginWidth: Int = 0
    /**
     * 字体大小
     */
    private var mFontSize: Int = 0
    private var mNumFontSize: Int = 0
    /**
     * 每页行数
     */
    private var mPageLineCount: Int = 0
    /**
     * 行间距
     */
    private var mLineSpace: Int = 0
    /**
     * 字节长度
     */
    private var mbBufferLen: Int = 0
    /**
     * MappedByteBuffer：高效的文件内存映射
     */
    private var mbBuff: MappedByteBuffer? = null
    /**
     * 页首页尾的位置
     */
    private var curEndPos = 0
    private var curBeginPos = 0
    private var tempBeginPos:Int = 0
    private var tempEndPos:Int = 0
    private var currentChapter: Int = 0
    private var tempChapter:Int = 0
    private var mLines: Vector<String>? = Vector()

    private lateinit var mPaint: Paint
    private lateinit var mTitlePaint: Paint
    private var mBookPageBg: Bitmap? = null

    private var decimalFormat = DecimalFormat("#0.00")
    private var dateFormat = SimpleDateFormat("HH:mm")
    private var timeLen = 0
    private var percentLen = 0
    private var time: String? = null
    private var battery = 40
    private lateinit var rectF: Rect
    private var batteryView: ProgressBar? = null
    private var batteryBitmap: Bitmap? = null

    private var bookId: String = ""
    private var chaptersList: List<BookMixATocBean.MixTocBean.ChaptersBean>? = null
    private var chapterSize = 0
    private var currentPage = 1

    private var listener: OnReadStateChangeListener? = null
    private var charset = "UTF-8"

    constructor(context: Context, bookId: String, chaptersList: List<BookMixATocBean.MixTocBean.ChaptersBean>):
            this(context, ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight(),
            //SettingManager.getInstance().getReadFontSize(bookId),
            SettingManager.getInstance()!!.getReadFontSize(),
            bookId, chaptersList) {
    }

    constructor(context: Context, width: Int, height: Int, fontSize: Int, bookId: String,
                    chaptersList: List<BookMixATocBean.MixTocBean.ChaptersBean>): super() {
        mContext = context
        mWidth = width
        mHeight = height
        mFontSize = fontSize
        mLineSpace = mFontSize / 5 * 2
        mNumFontSize = ScreenUtils.dpToPxInt(16f)
        marginWidth = ScreenUtils.dpToPxInt(15f)
        marginHeight = ScreenUtils.dpToPxInt(15f)
        mVisibleHeight = mHeight - marginHeight * 2 - mNumFontSize * 2 - mLineSpace * 2
        mVisibleWidth = mWidth - marginWidth * 2
        mPageLineCount = mVisibleHeight / (mFontSize + mLineSpace)
        rectF = Rect(0, 0, mWidth, mHeight)

        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.textSize = mFontSize.toFloat()
        mPaint.textSize = ContextCompat.getColor(context, R.color.chapter_content_day).toFloat()
        mPaint.color = Color.BLACK
        mTitlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTitlePaint.textSize = mNumFontSize.toFloat()
        mTitlePaint.color = ContextCompat.getColor(AppApplication.instance.getContext(), R.color.chapter_title_day)
        timeLen = mTitlePaint.measureText("00:00").toInt()
        percentLen = mTitlePaint.measureText("00.00%").toInt()
        // Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/FZBYSK.TTF");
        // mPaint.setTypeface(typeface);
        // mNumPaint.setTypeface(typeface);

        this.bookId = bookId
        this.chaptersList = chaptersList

        time = dateFormat.format(Date())
    }

    fun getBookFile(chapter: Int): File? {
        val file = FileUtils.getChapterFile(bookId, chapter)
        if (file != null && file!!.length() > 10) {
            // 解决空文件造成编码错误的问题
            charset = FileUtils.getCharset(file!!.absolutePath)
        }
        return file
    }

    fun openBook() {
        openBook(intArrayOf(0, 0))
    }

    fun openBook(position: IntArray) {
        openBook(1, position)
    }

    /**
     * 打开书籍文件
     *
     * @param chapter  阅读章节
     * @param position 阅读位置
     * @return 0：文件不存在或打开失败  1：打开成功
     */
    fun openBook(chapter: Int, position: IntArray): Int {
        this.currentChapter = chapter
        this.chapterSize = chaptersList!!.size
        if (currentChapter > chapterSize)
            currentChapter = chapterSize
        val path = getBookFile(currentChapter)!!.path
        try {
            val file = File(path)
            val length = file.length()
            if (length > 10) {
                mbBufferLen = length.toInt()
                // 创建文件通道，映射为MappedByteBuffer
                mbBuff = RandomAccessFile(file, "r")
                        .channel
                        .map(FileChannel.MapMode.READ_ONLY, 0, length)
                curBeginPos = position[0]
                curEndPos = position[1]
                onChapterChanged(chapter)
                mLines?.clear()
                return 1
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return 0
    }

    /**
     * 绘制阅读页面
     *
     * @param canvas
     */
    @Synchronized
    fun onDraw(canvas: Canvas) {
        if (mLines?.size == 0) {
            curEndPos = curBeginPos
            mLines = pageDown()
        }
        if (mLines?.size!! > 0) {
            var y = marginHeight + (mLineSpace shl 1)
            // 绘制背景
            if (mBookPageBg != null) {
                canvas.drawBitmap(mBookPageBg, null, rectF, null)
            } else {
                canvas.drawColor(Color.WHITE)
            }
            // 绘制标题
            canvas.drawText(chaptersList!!.get(currentChapter - 1).title, marginWidth.toFloat(), y.toFloat(), mTitlePaint)
            y += mLineSpace + mNumFontSize
            // 绘制阅读页面文字
            // 绘制阅读页面文字
            mLines!!.forEach {
                y += mLineSpace
                if (it.endsWith("@")) {
                    canvas.drawText(it.substring(0, it.length - 1), marginWidth.toFloat(), y.toFloat(), mPaint)
                    y += mLineSpace
                } else {
                    canvas.drawText(it, marginWidth.toFloat(), y.toFloat(), mPaint)
                }
                y += mFontSize
            }
            // 绘制提示内容
            if (batteryBitmap != null) {
                canvas.drawBitmap(batteryBitmap, (marginWidth + 2).toFloat(),
                        (mHeight - marginHeight - ScreenUtils.dpToPxInt(12f)).toFloat(), mTitlePaint)
            }

            val percent = currentChapter.toFloat() * 100 / chapterSize
            canvas.drawText(decimalFormat.format(percent.toDouble()) + "%", ((mWidth - percentLen) / 2).toFloat(),
                    (mHeight - marginHeight).toFloat(), mTitlePaint)

            val mTime = dateFormat.format(Date())
            canvas.drawText(mTime, (mWidth - marginWidth - timeLen).toFloat(), (mHeight - marginHeight).toFloat(), mTitlePaint)
            // 保存阅读进度
            SettingManager.getInstance()?.saveReadProgress(bookId, currentChapter, curBeginPos, curEndPos)
        }
    }

    /**
     * 指针移到上一页页首
     */
    private fun pageUp() {
        var strParagraph = ""
        val lines = Vector<String>() // 页面行
        var paraSpace = 0
        mPageLineCount = mVisibleHeight / (mFontSize + mLineSpace)
        while (lines.size < mPageLineCount && curBeginPos > 0) {
            val paraLines = Vector<String>() // 段落行
            val parabuffer = readParagraphBack(curBeginPos) // 1.读取上一个段落

            curBeginPos -= parabuffer.size // 2.变换起始位置指针
            try {
                strParagraph = String(parabuffer, kotlin.text.charset(charset))
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

            strParagraph = strParagraph.replace("\r\n".toRegex(), "  ")
            strParagraph = strParagraph.replace("\n".toRegex(), " ")

            while (strParagraph.length > 0) { // 3.逐行添加到lines
                val paintSize = mPaint.breakText(strParagraph, true, mVisibleWidth.toFloat(), null)
                paraLines.add(strParagraph.substring(0, paintSize))
                strParagraph = strParagraph.substring(paintSize)
            }
            lines.addAll(0, paraLines)

            while (lines.size > mPageLineCount) { // 4.如果段落添加完，但是超出一页，则超出部分需删减
                try {
                    curBeginPos += lines[0].toByteArray(charset(charset)).size // 5.删减行数同时起始位置指针也要跟着偏移
                    lines.removeAt(0)
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }

            }
            curEndPos = curBeginPos // 6.最后结束指针指向下一段的开始处
            paraSpace += mLineSpace
            mPageLineCount = (mVisibleHeight - paraSpace) / (mFontSize + mLineSpace) // 添加段落间距，实时更新容纳行数
        }
    }


    /**
     * 根据起始位置指针，读取一页内容
     *
     * @return
     */
    private fun pageDown(): Vector<String> {
        var strParagraph = ""
        val lines = Vector<String>()
        var paraSpace = 0
        mPageLineCount = mVisibleHeight / (mFontSize + mLineSpace)
        while (lines.size < mPageLineCount && curEndPos < mbBufferLen) {
            val parabuffer = readParagraphForward(curEndPos)
            curEndPos += parabuffer.size
            try {
                strParagraph = String(parabuffer, Charset.forName(charset))
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

            strParagraph = strParagraph.replace("\r\n".toRegex(), "  ")
                    .replace("\n".toRegex(), " ") // 段落中的换行符去掉，绘制的时候再换行

            while (strParagraph.length > 0) {
                val paintSize = mPaint.breakText(strParagraph, true, mVisibleWidth.toFloat(), null)
                lines.add(strParagraph.substring(0, paintSize))
                strParagraph = strParagraph.substring(paintSize)
                if (lines.size >= mPageLineCount) {
                    break
                }
            }
            lines[lines.size - 1] = lines[lines.size - 1] + "@"
            if (strParagraph.length != 0) {
                try {
                    curEndPos -= strParagraph.toByteArray(charset(charset)).size
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }

            }
            paraSpace += mLineSpace
            mPageLineCount = (mVisibleHeight - paraSpace) / (mFontSize + mLineSpace)
        }
        return lines
    }

    /**
     * 获取最后一页的内容。比较繁琐，待优化
     *
     * @return
     */
    fun pageLast(): Vector<String> {
        var strParagraph = ""
        val lines = Vector<String>()
        currentPage = 0
        while (curEndPos < mbBufferLen) {
            var paraSpace = 0
            mPageLineCount = mVisibleHeight / (mFontSize + mLineSpace)
            curBeginPos = curEndPos
            while (lines.size < mPageLineCount && curEndPos < mbBufferLen) {
                val parabuffer = readParagraphForward(curEndPos)
                curEndPos += parabuffer.size
                try {
                    strParagraph = String(parabuffer, kotlin.text.charset(charset))
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }

                strParagraph = strParagraph.replace("\r\n".toRegex(), "  ")
                strParagraph = strParagraph.replace("\n".toRegex(), " ") // 段落中的换行符去掉，绘制的时候再换行

                while (strParagraph.length > 0) {
                    val paintSize = mPaint.breakText(strParagraph, true, mVisibleWidth.toFloat(), null)
                    lines.add(strParagraph.substring(0, paintSize))
                    strParagraph = strParagraph.substring(paintSize)
                    if (lines.size >= mPageLineCount) {
                        break
                    }
                }
                lines[lines.size - 1] = lines[lines.size - 1] + "@"

                if (strParagraph.length != 0) {
                    try {
                        curEndPos -= strParagraph.toByteArray(charset(charset)).size
                    } catch (e: UnsupportedEncodingException) {
                        e.printStackTrace()
                    }

                }
                paraSpace += mLineSpace
                mPageLineCount = (mVisibleHeight - paraSpace) / (mFontSize + mLineSpace)
            }
            if (curEndPos < mbBufferLen) {
                lines.clear()
            }
            currentPage++
        }
        //SettingManager.getInstance().saveReadProgress(bookId, currentChapter, curBeginPos, curEndPos);
        return lines
    }


    /**
     * 读取下一段落
     *
     * @param curEndPos 当前页结束位置指针
     * @return
     */
    private fun readParagraphForward(curEndPos: Int): ByteArray {
        var b0: Byte
        var i = curEndPos
        while (i < mbBufferLen) {
            b0 = mbBuff!!.get(i++)
            if (b0.toInt() == 0x0a) {
                break
            }
        }
        val nParaSize = i - curEndPos
        val buf = ByteArray(nParaSize)
        i = 0
        while (i < nParaSize) {
            buf[i] = mbBuff!!.get(curEndPos + i)
            i++
        }
        return buf
    }
    /**
     * 读取上一段落
     *
     * @param curBeginPos 当前页起始位置指针
     * @return
     */
    private fun readParagraphBack(curBeginPos: Int): ByteArray {
        var b0: Byte
        var i = curBeginPos - 1
        while (i > 0) {
            b0 = mbBuff!!.get(i)
            if (b0.toInt() == 0x0a && i != curBeginPos - 1) {
                i++
                break
            }
            i--
        }
        val nParaSize = curBeginPos - i
        val buf = ByteArray(nParaSize)
        for (j in 0..nParaSize - 1) {
            buf[j] = mbBuff!!.get(i + j)
        }
        return buf
    }

    fun hasNextPage(): Boolean {
        return currentChapter < chaptersList!!.size || curEndPos < mbBufferLen
    }

    fun hasPrePage(): Boolean {
        return currentChapter > 1 || currentChapter == 1 && curBeginPos > 0
    }

    /**
     * 跳转下一页
     */
    fun nextPage(): BookStatus {
        if (!hasNextPage()) { // 最后一章的结束页
            return BookStatus.NO_NEXT_PAGE
        } else {
            tempChapter = currentChapter
            tempBeginPos = curBeginPos
            tempEndPos = curEndPos
            if (curEndPos >= mbBufferLen) { // 中间章节结束页
                currentChapter++
                val ret = openBook(currentChapter, intArrayOf(0, 0)) // 打开下一章
                if (ret == 0) {
                    onLoadChapterFailure(currentChapter)
                    currentChapter--
                    curBeginPos = tempBeginPos
                    curEndPos = tempEndPos
                    return BookStatus.NEXT_CHAPTER_LOAD_FAILURE
                } else {
                    currentPage = 0
                    onChapterChanged(currentChapter)
                }
            } else {
                curBeginPos = curEndPos // 起始指针移到结束位置
            }
            mLines?.clear()
            mLines = pageDown() // 读取一页内容
            onPageChanged(currentChapter, ++currentPage)
        }
        return BookStatus.LOAD_SUCCESS
    }

    /**
     * 跳转上一页
     */
    fun prePage(): BookStatus {
        if (!hasPrePage()) { // 第一章第一页
            return BookStatus.NO_PRE_PAGE
        } else {
            // 保存当前页的值
            tempChapter = currentChapter
            tempBeginPos = curBeginPos
            tempEndPos = curEndPos
            if (curBeginPos <= 0) {
                currentChapter--
                val ret = openBook(currentChapter, intArrayOf(0, 0))
                if (ret == 0) {
                    onLoadChapterFailure(currentChapter)
                    currentChapter++
                    return BookStatus.PRE_CHAPTER_LOAD_FAILURE
                } else { // 跳转到上一章的最后一页
                    mLines?.clear()
                    mLines = pageLast()
                    onChapterChanged(currentChapter)
                    onPageChanged(currentChapter, currentPage)
                    return BookStatus.LOAD_SUCCESS
                }
            }
            mLines?.clear()
            pageUp() // 起始指针移到上一页开始处
            mLines = pageDown() // 读取一页内容
            onPageChanged(currentChapter, --currentPage)
        }
        return BookStatus.LOAD_SUCCESS
    }

    fun cancelPage() {
        currentChapter = tempChapter
        curBeginPos = tempBeginPos
        curEndPos = curBeginPos

        val ret = openBook(currentChapter, intArrayOf(curBeginPos, curEndPos))
        if (ret == 0) {
            onLoadChapterFailure(currentChapter)
            return
        }
        mLines?.clear()
        mLines = pageDown()
    }

    /**
     * 获取当前阅读位置
     *
     * @return index 0：起始位置 1：结束位置
     */
    fun getPosition(): IntArray {
        return intArrayOf(currentChapter, curBeginPos, curEndPos)
    }

    fun getHeadLineStr(): String {
        return if (mLines != null && mLines!!.size > 1) {
            mLines!!.get(0)
        } else ""
    }

    /**
     * 设置字体大小
     *
     * @param fontsize 单位：px
     */
    fun setTextFont(fontsize: Int) {
        mFontSize = fontsize
        mLineSpace = mFontSize / 5 * 2
        mPaint.textSize = mFontSize.toFloat()
        mPageLineCount = mVisibleHeight / (mFontSize + mLineSpace)
        curEndPos = curBeginPos
        nextPage()
    }

    /**
     * 设置字体颜色
     *
     * @param textColor
     * @param titleColor
     */
    fun setTextColor(textColor: Int, titleColor: Int) {
        mPaint.color = textColor
        mTitlePaint.color = titleColor
    }

    fun getTextFont(): Int {
        return mFontSize
    }

    /**
     * 根据百分比，跳到目标位置
     *
     * @param persent
     */
    fun setPercent(persent: Int) {
        val a = (mbBufferLen * persent).toFloat() / 100
        curEndPos = a.toInt()
        if (curEndPos == 0) {
            nextPage()
        } else {
            nextPage()
            prePage()
            nextPage()
        }
    }

    fun setBgBitmap(BG: Bitmap) {
        mBookPageBg = BG
    }


    fun setOnReadStateChangeListener(listener: OnReadStateChangeListener) {
        this.listener = listener
    }

    private fun onChapterChanged(chapter: Int) {
        if (listener != null)
            listener!!.onChapterChanged(chapter)
    }

    private fun onPageChanged(chapter: Int, page: Int) {
        if (listener != null)
            listener!!.onPageChanged(chapter, page)
    }

    private fun onLoadChapterFailure(chapter: Int) {
        if (listener != null)
            listener!!.onLoadChapterFailure(chapter)
    }

    fun convertBetteryBitmap() {
        batteryView = LayoutInflater.from(mContext).inflate(R.layout.layout_battery_progress, null) as ProgressBar
        batteryView!!.setProgressDrawable(ContextCompat.getDrawable(mContext,
                if (SettingManager.getInstance()?.getReadTheme()!! < 4)
                    R.drawable.seekbar_battery_bg
                else
                    R.drawable.seekbar_battery_night_bg))
        batteryView!!.progress = battery
        batteryView!!.setDrawingCacheEnabled(true)
        batteryView!!.measure(View.MeasureSpec.makeMeasureSpec(ScreenUtils.dpToPxInt(26f), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(ScreenUtils.dpToPxInt(14f), View.MeasureSpec.EXACTLY))
        batteryView!!.layout(0, 0, batteryView!!.measuredWidth, batteryView!!.measuredHeight)
        batteryView!!.buildDrawingCache()
        //batteryBitmap = batteryView.getDrawingCache();
        // tips: @link{https://github.com/JustWayward/BookReader/issues/109}
        batteryBitmap = Bitmap.createBitmap(batteryView!!.getDrawingCache())
        batteryView!!.setDrawingCacheEnabled(false)
        batteryView!!.destroyDrawingCache()
    }

    fun setBattery(battery: Int) {
        this.battery = battery
        convertBetteryBitmap()
    }

    fun setTime(time: String) {
        this.time = time
    }

    fun recycle() {
        if (mBookPageBg != null && !mBookPageBg!!.isRecycled) {
            mBookPageBg!!.recycle()
            mBookPageBg = null
        }

        if (batteryBitmap != null && !batteryBitmap!!.isRecycled) {
            batteryBitmap!!.recycle()
            batteryBitmap = null

        }
    }

}