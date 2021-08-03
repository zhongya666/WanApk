package com.zhongya666.library.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhongya666.library.base.bean.BaseResponse
import com.zhongya666.library.base.bean.DataState
import com.zhongya666.library.network.StateLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


open class BaseViewModel : ViewModel() {

    fun launch(block : suspend () -> Unit,
               error : (Throwable) -> Unit,
               complete : suspend () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block()
            }catch (e : Exception){
                error(e)
            }finally {
                complete()
            }
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
    fun <T> request(
        block: suspend () -> BaseResponse<T>,
        stateLiveData: StateLiveData<T>,
        complete: () -> Unit,
        isShowLoading: Boolean = false,
        loadingMessage: String = "请求网络中..."
    ){

        val baseResponse = BaseResponse<T>()
        var response: BaseResponse<T>? = null

        //如果需要弹窗 通知Activity/fragment弹窗
        viewModelScope.launch {
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
                complete()

            }.onFailure {
                baseResponse.dataState = DataState.STATE_ERROR
                baseResponse.error = it
                stateLiveData.postValue(baseResponse)
            }
        }
    }

}