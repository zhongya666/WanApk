package com.zhongya666.library.base

import android.app.Application
import com.tencent.mmkv.MMKV

open class BaseApp : Application() {

    companion object {
        lateinit var instance: BaseApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        MMKV.initialize(this.filesDir.absolutePath + "/zhongya666/mmkv");
    }
}