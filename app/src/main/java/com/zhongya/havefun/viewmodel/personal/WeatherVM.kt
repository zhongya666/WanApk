package com.zhongya.havefun.viewmodel.personal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zhongya.havefun.app.net.api.PersonalApi
import com.zhongya.havefun.bean.personal.Weather
import com.zhongya666.library.base.viewmodel.BaseViewModel
import com.zhongya666.library.network.RetrofitManager
import com.zhongya666.library.util.ToastUtil
import kotlinx.coroutines.launch

class WeatherVM(val service: PersonalApi) : BaseViewModel() {

    var weatherLiveData = MutableLiveData<Weather>()

    fun getWeatherInfo(url:String) {

        viewModelScope.launch {
            var weather : Weather? = null

            runCatching {
                weather = service.getWeatherInfo(url)
            }.onSuccess {
                //网络请求完成 关闭弹窗
                weatherLiveData.postValue(weather!!)

            }.onFailure {
                ToastUtil.show("暂无数据")
            }
        }
    }
}