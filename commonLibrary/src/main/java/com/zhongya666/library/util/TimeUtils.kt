package com.zhongya666.library.util

import android.util.Log
import java.io.File
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Time工具类
 * 2016年3月16日 19:07:06
 */
class TimeUtils private constructor() {


    companion object {
        val DEFAULT_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val DATE_FORMAT_DATE = SimpleDateFormat("yyyy-MM-dd")

        /**
         * 把timeInMillis转化成"yyyy-MM-dd HH:mm:ss"格式的时间字符串返回
         *
         * @param timeInMillis 毫秒级时间
         * @return
         */
        fun getTimeString(timeInMillis: Long): String {
            return getTimeString(timeInMillis, DEFAULT_DATE_FORMAT)
        }

        fun getTimeNianYueRi(timeInMillis: Long): String {
            return getTimeString(timeInMillis, DATE_FORMAT_DATE)
        }

        /**
         * 把timeInMillis转化成dateFormat格式的时间字符串返回
         *
         * @param timeInMillis 毫秒级时间
         * @param dateFormat
         * @return
         */
        fun getTimeString(timeInMillis: Long, dateFormat: SimpleDateFormat): String {
            return dateFormat.format(Date(timeInMillis))
        }

        /**
         * 把字符串按照"yyyy-MM-dd HH:mm:ss"格式 转化成时间Date对象返回
         *
         * @param time 时间字符串
         * @return
         */
        fun getTimeDate(time: String?): Date? {
            var date: Date? = null
            try {
                date = DEFAULT_DATE_FORMAT.parse(time)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return date
        }

        /****
         * 把字符串按照指定格式 转化成时间Date对象返回
         * @param time  时间字符串
         * @param dateFormat  时间格式对象
         * @return
         */
        fun getTimeDate(time: String?, dateFormat: SimpleDateFormat): Date? {
            var date: Date? = null
            try {
                date = dateFormat.parse(time)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return date
        }

        /**
         * get current time in milliseconds
         *
         * @return
         */
        val currentTimeInLong: Long
            get() = System.currentTimeMillis()

        /**
         * get current time in milliseconds, format is [.DEFAULT_DATE_FORMAT]
         *
         * @return
         */
        val currentTimeInString: String
            get() = getTimeString(currentTimeInLong)

        /**
         * get current time in milliseconds
         *
         * @return
         */
        fun getCurrentTimeInString(dateFormat: SimpleDateFormat): String {
            return getTimeString(currentTimeInLong, dateFormat)
        }

        fun formatDateTime(mss: Long): String? {
            if (mss < 0) return "00-00-00 00:00:00"
            var DateTimes: String? = null
            val days = mss / (60 * 60 * 24)
            val hours = mss % (60 * 60 * 24) / (60 * 60)
            val minutes = mss % (60 * 60) / 60
            val seconds = mss % 60
            if (days > 0) {
                DateTimes = days.toString() + "天" + hours + "小时" + minutes + "分钟"
            } else if (hours > 0) {
                DateTimes = hours.toString() + "小时" + minutes + "分钟"
            } else if (minutes > 0) {
                DateTimes = minutes.toString() + "分钟"
            } else {
//                DateTimes = seconds + "秒";
            }
            if (minutes < 1 && seconds > 0) {
                DateTimes = seconds.toString() + "秒"
            }
            return DateTimes
        }

        fun formatDateTime(mss: Long, type: String?): String {
            if (mss < 0) return "00-00-00 00:00:00"
            val DateTimes: String? = null
            val days = mss / (60 * 60 * 24)
            val hours = mss % (60 * 60 * 24) / (60 * 60)
            val minutes = mss % (60 * 60) / 60
            val seconds = mss % 60
            val mHours: String
            val mMinutes: String
            val mSeconds: String
            mHours = if (hours < 10) {
                "0$hours"
            } else {
                "" + hours
            }
            mMinutes = if (minutes < 10) {
                "0$minutes"
            } else {
                "" + minutes
            }
            mSeconds = if (seconds < 10) {
                "0$seconds"
            } else {
                "" + seconds
            }
            return "$mHours:$mMinutes:$mSeconds"
        }

        /**
         * 获取目录下所有文件(按时间排序)
         *
         * @param path
         * @return
         */
        fun listFileSortByModifyTime(path: String?): List<File>? {
            val list = getFiles(path, ArrayList())
            if (list != null && list.size > 0) {
                Collections.sort(list) { file, newFile ->
                    if (file.lastModified() < newFile.lastModified()) {
                        -1
                    } else if (file.lastModified() == newFile.lastModified()) {
                        0
                    } else {
                        1
                    }
                }
            }
            return list
        }

        /**
         * 获取目录下所有文件
         *
         * @param realpath
         * @param files
         * @return
         */
        fun getFiles(realpath: String?, files: MutableList<File>): List<File> {
            val realFile = File(realpath)
            if (realFile.isDirectory) {
                val subfiles = realFile.listFiles()
                for (file in subfiles) {
                    if (file.isDirectory) {
                        getFiles(file.absolutePath, files)
                    } else {
                        files.add(file)
                    }
                }
            }
            return files
        }



        /** 
         * wzy add
         *  两个时间相差距离多少天多少小时多少分多少秒 
         *  @param str1 时间参数 1 格式：1990-01-01 12:00:00 
         *  @param str2 时间参数 2 格式：2009-01-01 12:00:00 
         *  @return long[] 返回值为：{天, 时, 分, 秒} 
         */
        fun getDistanceTimes(str1: String?, str2: String?): LongArray {
            val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val one: Date
            val two: Date
            var day: Long = 0
            var hour: Long = 0
            var min: Long = 0
            var sec: Long = 0
            try {
                one = df.parse(str1)
                two = df.parse(str2)
                val time1 = one.time
                val time2 = two.time
                val diff: Long
                diff = if (time1 < time2) {
                    time2 - time1
                } else {
                    time1 - time2
                }
                day = diff / (24 * 60 * 60 * 1000)
                hour = diff / (60 * 60 * 1000) - day * 24
                min = diff / (60 * 1000) - day * 24 * 60 - hour * 60
                sec = diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return longArrayOf(day, hour, min, sec)
        }

        /**
         * wzy add
         * 两个时间相差距离多少天多少小时多少分多少秒
         * @param str1 时间参数 1 格式：1990-01-01 12:00:00
         * @param str2 时间参数 2 格式：2009-01-01 12:00:00
         * @return String 返回值为：xx天xx小时xx分xx秒
         */
        //    public static String getDistanceTime(String str1, String str2) {
        fun getDistanceTime(str1: String?, str2: String?): Array<String> {
            val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val one: Date
            val two: Date
            var day: Long = 0
            var hour: Long = 0
            var min: Long = 0
            var sec: Long = 0
            try {
                one = df.parse(str1)
                two = df.parse(str2)
                val time1 = one.time
                val time2 = two.time
                val diff: Long
                diff = if (time1 < time2) {
                    time2 - time1
                } else {
                    time1 - time2
                }
                day = diff / (24 * 60 * 60 * 1000)
                hour = diff / (60 * 60 * 1000) - day * 24
                min = diff / (60 * 1000) - day * 24 * 60 - hour * 60
                sec = diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            //        return day + "天" + hour + "小时" + min + "分" + sec + "秒";
            return arrayOf(day.toString() + "", hour.toString() + "", min.toString() + "", sec.toString() + "")
        }


        //将时间转换为时间戳
        fun dateToStamp(s: String?): Long {
            var time: Long = 0
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            //        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                val date = simpleDateFormat.parse(s)
                time = date.time
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return time
        }

        fun formatTime(time : Long) : String{
            return DEFAULT_DATE_FORMAT.format(Date(time))
        }


        /*
    * 两个时间相减后  计算出  天  时  分  秒
    *
    * */
        fun getReduceDayHourMinitusSecond(different: Long): LongArray {
            var different = different
            val secondsInMilli: Long = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60
            val daysInMilli = hoursInMilli * 24
            val day = different / daysInMilli
            different = different % daysInMilli
            val hour = different / hoursInMilli
            different = different % hoursInMilli
            val min = different / minutesInMilli
            different = different % minutesInMilli
            val sec = different / secondsInMilli
            return longArrayOf(day, hour, min, sec)
        }
    }

    init {
        throw AssertionError()
    }
}