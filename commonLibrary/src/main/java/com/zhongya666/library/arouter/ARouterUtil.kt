package com.zhongya666.library.arouter

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter

/**
 * GitHub/Gitee　: zhongya666
 * 时间　: 2021/7/11 8:29
 * 描述　: 路由跳转工具类
 */
object ARouterUtil {

    fun navigate(path: String, bundle: Bundle? = null): Any? {

        val build = ARouter.getInstance().build(path)


        var any : Any? = null

        any = if (bundle == null) {
            build.navigation()
        }else{
            build.with(bundle).navigation()
        }

        return any
    }

    fun navigate(context: Context ,path: String, bundle: Bundle? = null): Any? {

        val build = ARouter.getInstance().build(path)

        val callback = object : NavigationCallback {

            override fun onFound(postcard: Postcard?) {
                Log.e("wzy","")
            }

            override fun onLost(postcard: Postcard?) {
                Log.e("wzy","")
            }

            override fun onArrival(postcard: Postcard?) {
                Log.e("wzy","")
            }

            override fun onInterrupt(postcard: Postcard?) {
                Log.e("wzy","")
            }
        }

        var any : Any? = null

        any = if (bundle == null) {
            build.navigation(context,callback)
        }else{
            build.with(bundle).navigation(context,callback)
        }

        return any
    }


}