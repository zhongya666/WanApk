package com.zhongya666.library.loadsir

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback
import com.zhongya666.library.R


class LoadingCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_loading
    }

    override fun onViewCreate(context: Context?, view: View?) {
        super.onViewCreate(context, view)

    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }

}