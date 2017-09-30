package com.example.happyghost.showtimeforkotlin.utils

import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.bean.bookdata.BookMark


/**
 * @author Zhao Chenping
 * @creat 2017/9/30.
 * @description
 */
public class SettingManager {
    companion object {
        @Volatile private var manager: SettingManager? = null

        fun getInstance(): SettingManager? {
            if(manager!=null){
                return manager
            }else{
                manager = SettingManager()
            }
            return manager
        }
    }
        /**
         * 保存书籍阅读字体大小
         *
         * @param bookId     需根据bookId对应，避免由于字体大小引起的分页不准确
         * @param fontSizePx
         * @return
         */
        fun saveFontSize(bookId: String, fontSizePx: Int) {
            // 书籍对应
            PreferencesUtils.putInt(getFontSizeKey(bookId), fontSizePx)
        }

        /**
         * 保存全局生效的阅读字体大小
         *
         * @param fontSizePx
         */
        fun saveFontSize(fontSizePx: Int) {
            saveFontSize("", fontSizePx)
        }

        fun getReadFontSize(bookId: String): Int {
            return PreferencesUtils.getInt(getFontSizeKey(bookId), ScreenUtils.dpToPxInt(16f))
        }

        fun getReadFontSize(): Int {
            return getReadFontSize("")
        }

        private fun getFontSizeKey(bookId: String): String {
            return bookId + "-readFontSize"
        }

        fun getReadBrightness(): Int {
            return PreferencesUtils.getInt(getLightnessKey(),
                    ScreenUtils.getScreenBrightness(AppApplication.instance.getContext()) as Int)
        }

        /**
         * 保存阅读界面屏幕亮度
         *
         * @param percent 亮度比例 0~100
         */
        fun saveReadBrightness(percent: Int) {
            PreferencesUtils.putInt(getLightnessKey(), percent)
        }

        private fun getLightnessKey(): String {
            return "readLightness"
        }

        @Synchronized
        fun saveReadProgress(bookId: String, currentChapter: Int, m_mbBufBeginPos: Int, m_mbBufEndPos: Int) {
            PreferencesUtils.putInt(getChapterKey(bookId), currentChapter)
            PreferencesUtils .putInt(getStartPosKey(bookId), m_mbBufBeginPos)
            PreferencesUtils .putInt(getEndPosKey(bookId), m_mbBufEndPos)
        }

        /**
         * 获取上次阅读章节及位置
         *
         * @param bookId
         * @return
         */
        fun getReadProgress(bookId: String): IntArray {
            val lastChapter = PreferencesUtils.getInt(getChapterKey(bookId), 1)
            val startPos = PreferencesUtils.getInt(getStartPosKey(bookId), 0)
            val endPos = PreferencesUtils.getInt(getEndPosKey(bookId), 0)

            return intArrayOf(lastChapter, startPos, endPos)
        }

        fun removeReadProgress(bookId: String) {
            PreferencesUtils.remove(getChapterKey(bookId))
            PreferencesUtils.remove(getStartPosKey(bookId))
            PreferencesUtils.remove(getEndPosKey(bookId))
        }

        private fun getChapterKey(bookId: String): String {
            return bookId + "-chapter"
        }

        private fun getStartPosKey(bookId: String): String {
            return bookId + "-startPos"
        }

        private fun getEndPosKey(bookId: String): String {
            return bookId + "-endPos"
        }


        fun addBookMark(bookId: String, mark: BookMark): Boolean {
            val arrayList = ArrayList<BookMark>()
            var marks = PreferencesUtils.getObject(getBookMarksKey(bookId), arrayList.javaClass)
            if (marks != null && marks!!.size > 0) {
                for (item in marks!!) {
                    if (item.chapter === mark.chapter && item.startPos === mark.startPos) {
                        return false
                    }
                }
            } else {
                marks = ArrayList<BookMark>()
            }
            marks!!.add(mark)
            PreferencesUtils.putObject(getBookMarksKey(bookId), marks)
            return true
        }

        fun getBookMarks(bookId: String): ArrayList<BookMark>?{
            return PreferencesUtils.getObject(getBookMarksKey(bookId), ArrayList<BookMark>().javaClass)
        }

        fun clearBookMarks(bookId: String) {
            PreferencesUtils.remove(getBookMarksKey(bookId))
        }

        private fun getBookMarksKey(bookId: String): String {
            return bookId + "-marks"
        }

        fun saveReadTheme(theme: Int) {
            PreferencesUtils.putInt("readTheme", theme)
        }

        fun getReadTheme(): Int {
            return if (PreferencesUtils.getBoolean(ConsTantUtils.ISNIGHT, false)) {
                ThemeManager.NIGHT
            } else PreferencesUtils.getInt("readTheme", 3)
        }

        /**
         * 是否可以使用音量键翻页
         *
         * @param enable
         */
        fun saveVolumeFlipEnable(enable: Boolean) {
            PreferencesUtils.putBoolean("volumeFlip", enable)
        }

        fun isVolumeFlipEnable(): Boolean {
            return PreferencesUtils.getBoolean("volumeFlip", true)
        }

        fun saveAutoBrightness(enable: Boolean) {
            PreferencesUtils.putBoolean("autoBrightness", enable)
        }

        fun isAutoBrightness(): Boolean {
            return PreferencesUtils.getBoolean("autoBrightness", false)
        }

        /**
         * 保存用户选择的性别
         *
         * @param sex male female
         */
        fun saveUserChooseSex(sex: String) {
            PreferencesUtils.putString("userChooseSex", sex)
        }

        /**
         * 获取用户选择性别
         *
         * @return
         */
        fun getUserChooseSex(): String {
            return PreferencesUtils.getString("userChooseSex", "male")
        }

        fun isUserChooseSex(): Boolean {
            return PreferencesUtils.exists("userChooseSex")
        }

        fun isNoneCover(): Boolean {
            return PreferencesUtils.getBoolean("isNoneCover", false)
        }

        fun saveNoneCover(isNoneCover: Boolean) {
            PreferencesUtils.putBoolean("isNoneCover", isNoneCover)
        }


}