package com.zhongya.havefun.app.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.ScrollView

/**
*作者GitHub:zhongya666
*创建日期：2021/2/26 17:57
**/
class MyScrollView : ScrollView {
    private var d = 0
    private var downX = 0
    private var downY = 0
    private var mTouchSlop: Int

    constructor(context: Context?) : super(context) {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        val action = e.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                downX = e.rawX.toInt()
                downY = e.rawY.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                val moveY = e.rawY.toInt()
                return if (Math.abs(moveY - downY) > mTouchSlop) {
                    if (d == 0) {
//                        Log.e("d == 0",d + "");
//                        return super.onInterceptTouchEvent(e);
                        true //到底了也要拦截
                    } else {
//                        Log.e("d != 0",d + "");
                        true
                    }
                } else {
                    super.onInterceptTouchEvent(e)
                }
            }
        }
        return super.onInterceptTouchEvent(e)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        val view = getChildAt(childCount - 1) as View
        d = view.bottom //成功解决
        d -= height + scrollY
        //        Log.e("d",d + "");
        if (d == 0) {
            //you are at the end of the list in scrollview
            //do what you wanna do here
        } else super.onScrollChanged(l, t, oldl, oldt)
    }
}