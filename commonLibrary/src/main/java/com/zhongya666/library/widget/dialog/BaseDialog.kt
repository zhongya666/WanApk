package com.zhongya666.library.widget.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.zhongya666.library.util.ScreenUtil

/**
 * @author vondear
 */
open class BaseDialog : Dialog {
    protected lateinit var mContext: Context
    lateinit var layoutParams: WindowManager.LayoutParams
        protected set

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {
        initView(context)
    }

    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener) {
        initView(context)
    }

    constructor(context: Context) : super(context) {
        initView(context)
    }

    /**
     * @param context
     * @param alpha   透明度 0.0f--1f(不透明)
     * @param gravity 方向(Gravity.BOTTOM,Gravity.TOP,Gravity.LEFT,Gravity.RIGHT)
     */
    constructor(context: Context, alpha: Float, gravity: Int) : super(context) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //        this.getWindow().setBackgroundDrawableResource(R.drawable.transparent_bg);
        mContext = context
        val window = this.window
        layoutParams = window?.attributes!!
        layoutParams.alpha = 1f
        window.attributes = layoutParams
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.gravity = gravity
    }

    private fun initView(context: Context) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //        this.getWindow().setBackgroundDrawableResource(R.drawable.transparent_bg);
        mContext = context
        val window = this.window
        layoutParams = window?.attributes!!
        layoutParams.alpha = 1f
        window.attributes = layoutParams
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.gravity = Gravity.CENTER
    }

    /**
     * 隐藏头部导航栏状态栏
     */
    fun skipTools() {
        if (Build.VERSION.SDK_INT < 19) {
            return
        }
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    /**
     * 设置全屏显示
     */
    fun setFullScreen() {
        val window = window
        window?.decorView?.setPadding(0, 0, 0, 0)
        val lp = window?.attributes!!
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        window.attributes = lp
    }

    /**
     * 设置宽度match_parent
     */
    fun setFullScreenWidth() {
        val window = window
        window?.decorView?.setPadding(0, 0, 0, 0)
        val lp = window?.attributes!!
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

    }

    /**
     * 设置
     * 宽度 1 - 10
     * 高度自适应
     */
    fun setScreenWidth(int: Int) {
        window?.decorView?.setPadding(0, 0, 0, 0)
        val lp = window?.attributes!!
        layoutParams.width = ScreenUtil.getScreenWidth(context) * int / 10
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        window?.attributes = lp
    }

    /**
     * 设置高度为match_parent
     */
    fun setFullScreenHeight() {
        val window = window
        window?.decorView?.setPadding(0, 0, 0, 0)
        val lp = window?.attributes!!
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        window.attributes = lp
    }
}