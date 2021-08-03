package com.zhongya666.library.util

import android.text.TextUtils
import java.text.NumberFormat

object StringUtils {
    /**
     * 删除小数位多于0
     * 24.00
     * 24
     * is 是否显示千分位逗号
     *
     * @param str
     * @param is
     * @return
     */
    fun strDeleteDecimalPoint(str: String, `is`: Boolean): String {
        if (TextUtils.isEmpty(str)) {
            return ""
        }
        var returnStr = ""
        try {
            val d = str.toDouble()
            val nf = NumberFormat.getInstance()
            nf.isGroupingUsed = `is`
            nf.maximumFractionDigits = 2
            returnStr = nf.format(d).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return returnStr
    }

    /**
     * 删除小数位多于0
     * 24.00
     * 24
     * is 是否显示千分位逗号
     *
     * @param str
     * @param is
     * @return weishu 小数点后几位
     */
    fun strDeleteDecimalPoint(str: String, `is`: Boolean, weishu: Int): String {
        if (TextUtils.isEmpty(str)) {
            return ""
        }
        var returnStr = ""
        try {
            val d = str.toDouble()
            val nf = NumberFormat.getInstance()
            nf.isGroupingUsed = `is`
            nf.maximumFractionDigits = weishu
            returnStr = nf.format(d).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return returnStr
    }

    /**
     * 删除小数位多于0
     * 24.00
     * 24
     * is 是否显示千分位逗号
     *
     * @param d
     * @param is
     * @return
     */
    fun strDeleteDecimalPoint(d: Double?, `is`: Boolean): String {
        var returnStr = ""
        try {
            val nf = NumberFormat.getInstance()
            nf.isGroupingUsed = `is`
            returnStr = nf.format(d).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return returnStr
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    fun subZeroAndDot(s: String): String {
        var s = s
        if (s.indexOf(".") > 0) {
            s = s.replace("0+?$".toRegex(), "") //去掉多余的0
            s = s.replace("[.]$".toRegex(), "") //如最后一位是.则去掉
        }
        return s
    }

    //格式化银行卡号
    fun formatBankNum(banknum: String): String {
        var formatString: String? = ""
        val regex = "(.{4})"
        //        formatString = banknum.replaceAll(regex,"$1\t\t");
        formatString = banknum.replace(regex.toRegex(), "$1 ")
        return formatString
    }

    /**
     * 每4位添加一个空格
     *
     * @param content
     * @return
     */
    fun addSpaceByCredit(content: String): String {
        var content = content
        if (TextUtils.isEmpty(content)) {
            return ""
        }
        content = content.replace(" ".toRegex(), "")
        if (TextUtils.isEmpty(content)) {
            return ""
        }
        val newString = StringBuilder()
        for (i in 1..content.length) {
            if (i % 4 == 0 && i != content.length) {
                newString.append(content[i - 1].toString() + " ")
            } else {
                newString.append(content[i - 1])
            }
        }
        return newString.toString()
    }

    /*
     * 想去倒数第几个后面的字符串就把num设成几，str是原来的字符串，返回得到字符串
     * */
    fun getSubStr(str: String, num: Int): String {
        var str = str
        var result = ""
        var i = 0
        while (i < num) {
            val lastFirst = str.lastIndexOf('/')
            result = str.substring(lastFirst) + result
            str = str.substring(0, lastFirst)
            i++
        }
        return result.substring(1)
    }

    /*
     * 把一个数组以逗号的形式分割开
     * */
    fun listChangeDotString(list: List<String>): String {
        var s = ""
        for (i in list.indices) {
            s = if (i == list.size - 1) {
                s + list[i]
            } else {
                s + list[i] + ","
            }
        }
        return s
    }

    /*
     * 截取“，”字符之前的字符  如果没有“，” 截取所有的
     * */
    fun subBeforeDotString(s: String): String {
        var s = s
        if (s.contains(",")) {
            s = s.substring(0, s.indexOf(","))
        }
        return s
    }

    fun isChineseChar(c: String): Boolean {
        val regex = "[\u4e00-\u9fa5]"
        return c.matches(regex as Regex)
    }
}