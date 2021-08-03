package com.zhongya.havefun.app.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.TextView

/**
*作者GitHub:zhongya666
*创建日期：2021/2/26 17:58
**/
@SuppressLint("AppCompatCustomView")
class PaomadengTextView : TextView {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        //设置单行
        setSingleLine()
        //设置Ellipsize
        ellipsize = TextUtils.TruncateAt.MARQUEE
        //获取焦点
        isFocusable = true
        //走马灯的重复次数，-1代表无限重复
        marqueeRepeatLimit = -1
        //强制获得焦点
        isFocusableInTouchMode = true
    }

    /*
*这个属性这个View得到焦点,在这里我们设置为true,这个View就永远是有焦点的
*/
    override fun isFocused(): Boolean {
        return true
    }

    /*
* 用于EditText抢注焦点的问题
* */

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
//        super.onFocusChanged(focused, direction, previouslyFocusedRect)

        if (focused) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect)
        }
    }



}