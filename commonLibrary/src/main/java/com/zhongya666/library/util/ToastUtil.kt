package com.zhongya666.library.util

import android.content.Context
import android.widget.Toast

object ToastUtil {

    private var time: Long = 0
    private var oldMsg: String? = null


    // Toast对象
    private var toast: Toast? = null

    fun show(msg: String) {
        if (msg != oldMsg) {
            create(msg)
            time = System.currentTimeMillis()
        } else {
            if (System.currentTimeMillis() - time > 2000) {
                create(msg)
                time = System.currentTimeMillis()
            }
        }
        oldMsg = msg
    }

    private fun create(massage: String) {
        val context: Context? = AppUtil.getApplication()?.applicationContext

        if (toast == null) {
            toast = Toast.makeText(context, massage, Toast.LENGTH_SHORT)
        }

        //设置显示时间
        toast?.setText(massage)
        toast?.duration = Toast.LENGTH_SHORT
        toast?.show()
    }
}