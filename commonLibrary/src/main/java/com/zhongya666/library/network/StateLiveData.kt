package com.zhongya666.library.network

import androidx.lifecycle.MutableLiveData
import com.zhongya666.library.base.bean.BaseResponse


/**
 * 一个既包含请求返回结果又包含不同状态值的LiveData
 * 用于将请求状态分发给UI
 */
class StateLiveData<T> : MutableLiveData<BaseResponse<T>>() {
}