package com.zhongya666.library.util

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.webkit.WebView

//dp px sp之间的转换
object ScreenUtils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val r = context.resources
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, r.displayMetrics)
        return px.toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px
     */
    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * 获取dialog宽度
     */
	@JvmStatic
	fun getDialogW(aty: Context): Int {
        var dm = DisplayMetrics()
        dm = aty.resources.displayMetrics
        // int w = aty.getWindowManager().getDefaultDisplay().getWidth() - 100;
        return dm.widthPixels - 100
    }

    /**
     * 获取屏幕宽度
     */
	@JvmStatic
	fun getScreenW(aty: Context): Int {
        var dm = DisplayMetrics()
        dm = aty.resources.displayMetrics
        // int w = aty.getWindowManager().getDefaultDisplay().getWidth();
        return dm.widthPixels
    }

    /**
     * 获取屏幕高度
     */
	@JvmStatic
	fun getScreenH(aty: Context): Int {
        // int h = aty.getWindowManager().getDefaultDisplay().getHeight();
        return aty.resources.displayMetrics.heightPixels
    }

    /**
     * 测量View的宽高
     *
     * @param view View
     */
    fun measureWidthAndHeight(view: View) {
        val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(widthMeasureSpec, heightMeasureSpec)
    }

    fun handWebView(context: Context, view: WebView) {
        val metric = context.resources.displayMetrics
        val density = metric.density //密度（0.75 / 1.0 / 1.5）


        // 设置WebView属性，能够执行JavaScript脚本
        view.settings.javaScriptEnabled = true
        // 设置可以支持缩放
        view.settings.setSupportZoom(true)
        // 设置出现缩放工具
        view.settings.builtInZoomControls = true
        // 为图片添加放大缩小功能
        view.settings.useWideViewPort = true

//		view.setInitialScale(150);   //100代表不缩放  不能用  放大后内容显示不全
        val settings = view.settings
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        //        settings1.setTextSize(WebSettings.TextSize.LARGEST);
        if (density.toDouble() == 2.0) {
            settings.textZoom = 80
        } else if (density.toDouble() == 3.0) {
            settings.textZoom = 250
        } else {
            settings.textZoom = 200
        }
    }

    fun setAscept(context: Context) {
        var applicationInfo: ApplicationInfo? = null
        try {
            applicationInfo = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        requireNotNull(applicationInfo) { " get application info = null, has no meta data! " }
        //		applicationInfo.metaData.putString("android.max_aspect", "2.1");
        applicationInfo.metaData.putString("android.max_aspect", "ratio_float")
    }
}