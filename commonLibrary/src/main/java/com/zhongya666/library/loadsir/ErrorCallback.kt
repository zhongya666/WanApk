package com.zhongya666.library.loadsir
import com.kingja.loadsir.callback.Callback
import com.zhongya666.library.R


class ErrorCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_error
    }

}