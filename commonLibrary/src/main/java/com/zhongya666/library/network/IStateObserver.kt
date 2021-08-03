package com.zhongya666.library.network

import android.net.ParseException
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.google.gson.JsonParseException
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.Convertor
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.zhongya666.library.base.bean.BaseResponse
import com.zhongya666.library.base.bean.DataState
import com.zhongya666.library.loadsir.EmptyCallback
import com.zhongya666.library.loadsir.ErrorCallback
import com.zhongya666.library.loadsir.LoadingCallback
import com.zhongya666.library.util.TAG
import com.zhongya666.library.util.ToastUtil
import org.json.JSONException
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException


/**
 * GitHub/Gitee　: zhongya666
 * 时间　: 2021/6/28 23:30
 * 描述　: LiveData Observer的一个类，
 * 主要结合LoadSir，根据BaseResp里面的State分别加载不同的UI，如Loading，Error
 * 同时重写onChanged回调，分为onDataChange，onDataEmpty，onError，
 * 开发者可以在UI层，每个接口请求时，直接创建IStateObserver，重写相应callback。
 *
 */

abstract class IStateObserver<T>(view: View?) : Observer<BaseResponse<T>>, Callback.OnReloadListener {
    private var mLoadService: LoadService<Any>? = null

    init {
        if (view != null) {
            mLoadService = LoadSir.getDefault().register(view, this,
                Convertor<BaseResponse<T>> {
                    var resultCode: Class<out Callback> = SuccessCallback::class.java

                    when (it.dataState) {
                        //数据刚开始请求，loading
                        DataState.STATE_CREATE, DataState.STATE_LOADING -> resultCode = LoadingCallback::class.java
                        //请求成功
                        DataState.STATE_SUCCESS -> resultCode = SuccessCallback::class.java
                        //数据为空
                        DataState.STATE_EMPTY -> resultCode = EmptyCallback::class.java

                        DataState.STATE_FAILED, DataState.STATE_ERROR -> {
                            val error: Throwable? = it.error
                            onError(error)
                            //可以根据不同的错误类型，设置错误界面时的UI
                            if (error is HttpException) {
                                //网络错误
                            } else if (error is ConnectException) {
                                //无网络连接
                            } else if (error is InterruptedIOException) {
                                //连接超时
                            } else if (error is JsonParseException
                                || error is JSONException
                                || error is ParseException) {
                                //解析错误
                            } else {
                                //未知错误
                            }
                            resultCode = ErrorCallback::class.java
                        }

                        DataState.STATE_COMPLETED, DataState.STATE_UNKNOWN -> { }

                        else -> {}
                    }
                    resultCode
                }
            )
        }
    }

    override fun onChanged(t: BaseResponse<T>?) {

        when (t?.dataState){

            DataState.STATE_SUCCESS -> {
                //请求成功，数据不为null
                onDataChange(t.data)
            }

            DataState.STATE_EMPTY -> {
                //数据为空
                onDataEmpty()
            }

            DataState.STATE_FAILED, DataState.STATE_ERROR -> {
                //请求错误
                t.error?.let { onError(it) }
            }

            else -> {}
        }

        //加载不同状态界面
        Log.d(TAG, "onChanged: mLoadService $mLoadService")

        //回调的时候直接传入转换器指定的数据类型。
        mLoadService?.showWithConvertor(t)
    }

    /**
     * 请求数据且数据不为空
     */
    open fun onDataChange(data: T?) {

    }

    /**
     * 请求成功，但数据为空
     */
    open fun onDataEmpty() {

    }

    /**
     * 请求错误
     */
    open fun onError(e: Throwable?) {

    }

    /**
     * 弹Toast
     */
    fun showToast(msg: String) {
        ToastUtil.show(msg)
    }
}