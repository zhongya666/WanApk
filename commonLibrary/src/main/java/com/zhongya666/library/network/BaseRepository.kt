package com.zhongya666.library.network

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhongya666.library.base.bean.BaseResponse
import com.zhongya666.library.base.bean.DataState
import kotlinx.coroutines.launch

/**
 * GitHub/Gitee：zhongya666 2021/6/28 16:12
 *
 */
open class BaseRepository() {

    suspend fun <T : Any> executeResp(
            block: suspend () -> BaseResponse<T>,
            stateLiveData: StateLiveData<T>
    ) {

        var baseResponse = BaseResponse<T>()

        try {

            baseResponse.dataState = DataState.STATE_LOADING
            stateLiveData.postValue(baseResponse)
            //开始请求数据
            var invoke = block()
            //将结果复制给baseResp
            baseResponse = invoke
            if (baseResponse.errorCode == 0) {
                //请求成功，判断数据是否为空，
                //因为数据有多种类型，需要自己设置类型进行判断
                if (baseResponse.data == null
                        || baseResponse.data is List<*>
                        && (baseResponse.data as List<*>).size == 0) {
                    //TODO: 数据为空,结构变化时需要修改判空条件
                    baseResponse.dataState = DataState.STATE_EMPTY
                } else {
                    //请求成功并且数据不为空的情况下，为STATE_SUCCESS
                    baseResponse.dataState = DataState.STATE_SUCCESS
                }
            }else {
                //服务器请求错误
                baseResponse.dataState = DataState.STATE_FAILED
            }

        } catch (e: Exception) {
            //非后台返回错误，捕获到的异常
            baseResponse.dataState = DataState.STATE_ERROR
            baseResponse.error = e
        } finally {
            stateLiveData.postValue(baseResponse)
        }
    }

    /**
     * 过滤服务器结果，失败抛异常
     * @param block 请求体方法，必须要用suspend关键字修饰
     * @param success 成功回调
     * @param error 失败回调 可不传
     * @param isShowDialog 是否显示加载框
     * @param loadingMessage 加载框提示内容
     */
    suspend fun <T> request(
        block: suspend () -> BaseResponse<T>,
        stateLiveData: StateLiveData<T>,
        isShowLoading: Boolean = false,
        loadingMessage: String = "请求网络中..."
    ){

        val baseResponse = BaseResponse<T>()
        var response: BaseResponse<T>? = null

        //如果需要弹窗 通知Activity/fragment弹窗
        runCatching {
            if (isShowLoading){
                baseResponse.dataState = DataState.STATE_LOADING
                stateLiveData.postValue(baseResponse)
            }
            //请求体
            response = block()
        }.onSuccess {
            //网络请求完成 关闭弹窗
            when {
                response == null -> {
                    baseResponse.dataState = DataState.STATE_EMPTY
                }
                response?.errorCode == 0 -> {
                    baseResponse.data = response?.data
                    baseResponse.errorCode = 0
                    baseResponse.dataState = DataState.STATE_SUCCESS
                }
                else -> {
                    baseResponse.errorCode = response?.errorCode ?: -1
                    baseResponse.errorMsg = response?.errorMsg
                    baseResponse.dataState = DataState.STATE_FAILED
                }
            }

            stateLiveData.postValue(baseResponse)

        }.onFailure {
            baseResponse.dataState = DataState.STATE_ERROR
            baseResponse.error = it
            stateLiveData.postValue(baseResponse)
        }
    }

}