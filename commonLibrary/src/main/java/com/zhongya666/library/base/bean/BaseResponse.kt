package com.zhongya666.library.base.bean

/**
 * 作者　: zhongya666
 * 时间　: 2021/06/05
 * 描述　: 服务器返回数据的基类
 */

class BaseResponse<T> {

    var data: T? = null
    var errorCode = -1
    var errorMsg: String? = ""
    var error: Throwable? = null
    var dataState: DataState? = null
}

enum class DataState {
    STATE_CREATE,//创建
    STATE_LOADING,//加载中
    STATE_SUCCESS,//成功
    STATE_COMPLETED,//完成
    STATE_EMPTY,//数据为null
    STATE_FAILED,//接口请求成功但是服务器返回error
    STATE_ERROR,//请求失败
    STATE_UNKNOWN//未知
}