package com.zhongya.havefun.app.util

import android.os.Environment
import okhttp3.ResponseBody
import java.io.*

/**
 * 作者：zhongya666 on 2019/8/8 13:38
 * 邮箱：997934916@qq.com
 */
object FileSaveUtil {
    /*
    * 判断文件是否存在
    * */
    fun checkFileExist(localPath: String): Boolean {
        val path = Environment.getExternalStorageDirectory().toString() + "/zhongya666/" + localPath
        val files = File(path)
        return files.exists()
    }

    /**
     * 下载到本地
     *
     * @param body 内容
     * @param localFolder 文件夹
     * @param name 文件名
     * @return 成功或者失败
     */
    fun writeResponseBodyToDisk(body: ResponseBody, localFolder: String, name: String, listener: (Long) -> Unit): Boolean {
        return try {
            val path = Environment.getExternalStorageDirectory().toString() + "/zhongya666/" + localFolder
            //判断文件夹是否存在
            val files = File(path) //跟目录一个文件夹
            if (!files.exists()) {
                //不存在就创建出来
                files.mkdirs()
            }

            //检查改文件是否存在
            val theFilePatch = Environment.getExternalStorageDirectory().toString() + "/zhongya666/" + localFolder + "/" + name
            val theFile = File(theFilePatch)
            if (theFile.exists() && body.contentLength() == theFile.length()) {
                return true
            }

            //创建一个文件
            val futureStudioIconFile = File("$path/$name")
            //初始化输入流
            var inputStream: InputStream? = null
            //初始化输出流
            var outputStream: OutputStream? = null
            try {
                //设置每次读写的字节
                val fileReader = ByteArray(4096)
                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0
                //请求返回的字节流
                inputStream = body.byteStream()
                //创建输出流
                outputStream = FileOutputStream(futureStudioIconFile)
                //进行读取操作
                while (true) {
                    val read = inputStream.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    //进行写入操作
                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                    listener(fileSizeDownloaded)
                }

                //刷新
                outputStream.flush()
                true
            } catch (e: IOException) {
                false
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            false
        }
    }
}