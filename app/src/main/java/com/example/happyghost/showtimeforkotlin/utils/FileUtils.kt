package com.example.happyghost.showtimeforkotlin.utils

import com.example.happyghost.showtimeforkotlin.AppApplication
import com.example.happyghost.showtimeforkotlin.bean.bookdata.ChapterReadBean
import java.io.*





/**
 * @author Zhao Chenping
 * @creat 2017/9/29.
 * @description
 */
class FileUtils {
    companion object {
        //获取文件夹下的所有文件
        fun getFileNum(bookId: String):Int{
            val path = AppApplication.instance.getContext()
                    .getExternalFilesDir(null)
                    .absolutePath + "/book/" + bookId
            var fileCount = 0
            var folderCount = 0
            val d = File(path)
            val list = d.listFiles()
            if(list!=null){
                for (i in list.indices) {
                    if (list[i].isFile) {
                        fileCount++
                    } else {
                        folderCount++
                    }
                }
            }
            return fileCount
        }
        fun getChapterPath(bookId:String , chapter:Int ):String{
            return AppApplication.instance.getContext()
                    .getExternalFilesDir(null)
                    .absolutePath+"/book/"+bookId + File.separator + chapter + ".txt"
        }
        fun getBookPath(bookId:String):String{
            return AppApplication.instance.getContext()
                    .getExternalFilesDir(null)
                    .absolutePath+"/book/"+bookId
        }
        fun getChapterFile(bookId:String , chapter:Int ):File{
            val file = File(getChapterPath(bookId, chapter))
            if(!file.exists()){
                creatFilr(file)
            }
            return file
        }
        fun hasChapterFile(bookId:String , chapter:Int): File? {
            val file = getChapterFile(bookId, chapter)
            if(file!=null&&file.length()>50){
                return file
            }
            return null
        }

        /**
         * 保存章节内容
         */
        fun saveChapterFile( bookId: String, chapter :Int,  data: ChapterReadBean.Chapter){
            val file = getChapterFile(bookId, chapter)
            writeFile(file, StringUtils.formatChapterBody(data.body))
        }
        /**
         * 将内容写入文件
         *
         * @param filePath eg:/mnt/sdcard/demo.txt
         * @param content  内容
         */
        fun writeFile(filePath:File , content:String ){
            try {
                filePath.writeBytes(content.toByteArray())
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        //删除文件夹和文件夹里面的文件
        fun deleteDir(bookId: String) {
            val dir = File(getBookPath(bookId))
            deleteDirWihtFile(dir)
        }

        fun deleteDirWihtFile(dir: File?) {
            if (dir == null || !dir.exists() || !dir.isDirectory)
                return
            for (file in dir.listFiles()) {
                if (file.isFile)
                    file.delete() // 删除所有文件
                else if (file.isDirectory)
                    deleteDirWihtFile(file) // 递规的方式删除文件夹
            }
            dir.delete()// 删除目录本身
        }
        /**
         * 递归创建文件夹
         *
         * @param file
         * @return 创建失败返回""
         */
        fun creatFilr(file: File):String{
            try {
                if(file.parentFile.exists()){
                    file.createNewFile()
                    return file.absolutePath
                }else{
                    creatDir(file.parentFile.absolutePath)
                    file.createNewFile()
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
            return ""
        }
        fun creatDir(absolutePath: String) :String{
            try {
                val file = File(absolutePath)
                if(file.parentFile.exists()){
                    file.mkdir()
                    return file.absolutePath
                }else{
                    creatDir(file.parentFile.absolutePath)
                    file.mkdir()
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
            return absolutePath
        }

        fun getCharset(fileName:String):String{
            var bis: BufferedInputStream? = null
            var charset = "GBK"
            val first3Bytes = ByteArray(3)
            try {
                var checked = false
                bis = BufferedInputStream(FileInputStream(fileName))
                bis.mark(0)
                var read = bis.read(first3Bytes, 0, 3)
                if (read == -1)
                    return charset
                if (first3Bytes[0] == 0xFF.toByte() && first3Bytes[1] == 0xFE.toByte()) {
                    charset = "UTF-16LE"
                    checked = true
                } else if (first3Bytes[0] == 0xFE.toByte() && first3Bytes[1] == 0xFF.toByte()) {
                    charset = "UTF-16BE"
                    checked = true
                } else if (first3Bytes[0] == 0xEF.toByte()
                        && first3Bytes[1] == 0xBB.toByte()
                        && first3Bytes[2] == 0xBF.toByte()) {
                    charset = "UTF-8"
                    checked = true
                }
                bis.mark(0)
                if (!checked) {
                    read = bis.read()
                    while (read != -1) {
                        if (read >= 0xF0)
                            break
                        if (0x80 <= read && read <= 0xBF)
                        // 单独出现BF以下的，也算是GBK
                            break
                        if (0xC0 <= read && read <= 0xDF) {
                            read = bis.read()
                            if (0x80 <= read && read <= 0xBF)
                            // 双字节 (0xC0 - 0xDF)
                            // (0x80 - 0xBF),也可能在GB编码内
                                continue
                            else
                                break
                        } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                            read = bis.read()
                            if (0x80 <= read && read <= 0xBF) {
                                read = bis.read()
                                if (0x80 <= read && read <= 0xBF) {
                                    charset = "UTF-8"
                                    break
                                } else
                                    break
                            } else
                                break
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (bis != null) {
                    try {
                        bis.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }

            return charset
        }
    }
}