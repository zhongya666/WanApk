package com.zhongya.havefun.app

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDex
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.launcher.ARouter
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.mob.MobSDK
import com.zhongya.havefun.app.di.appModule
import com.zhongya666.library.BuildConfig
import com.zhongya666.library.base.BaseApp
import com.zhongya666.library.base.viewmodel.AppViewModel
import com.zhongya666.library.loadsir.EmptyCallback
import com.zhongya666.library.loadsir.ErrorCallback
import com.zhongya666.library.loadsir.LoadingCallback
import com.zhongya666.library.util.jetpackMvvmLog
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * GitHub/Gitee：zhongya666 2021/6/25 10:06
 *
 */

class MyApplication : BaseApp(),ViewModelStoreOwner{

    companion object {
        lateinit var appViewModel: AppViewModel
    }


    override fun onCreate() {
        super.onCreate()

        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)
        MobSDK.init(this)
        //如果需要使用MultiDex，需要在此处调用。
        MultiDex.install(this)
        //界面加载管理 初始化
        initLoadSir()
        initARouter()
        initKoin()

        appViewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(this)).get(AppViewModel::class.java)
        jetpackMvvmLog = BuildConfig.DEBUG
    }

    private fun initKoin() {
        startKoin{
            androidLogger()
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }

    private fun initLoadSir() {
        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())//加载
            .addCallback(ErrorCallback())//错误
            .addCallback(EmptyCallback())//空
            .setDefaultCallback(SuccessCallback::class.java)//设置默认加载状态页
            .commit()
    }

    private fun initARouter() {
        ARouter.init(this)
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }

    override fun getViewModelStore(): ViewModelStore {
        return ViewModelStore()
    }

}