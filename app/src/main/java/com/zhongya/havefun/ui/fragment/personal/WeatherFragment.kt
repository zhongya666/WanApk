package com.zhongya.havefun.ui.fragment.personal

import android.view.LayoutInflater
import android.widget.TextView
import com.zhongya.havefun.R
import com.zhongya.havefun.databinding.PersonalFragmentWeatherBinding
import com.zhongya.havefun.viewmodel.personal.WeatherVM
import com.zhongya.havefun.app.BaseAppFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * GitHub/Gitee：zhongya666 2021/6/3 13:44
 *
 */
class WeatherFragment :
    BaseAppFragment<PersonalFragmentWeatherBinding>(R.layout.personal_fragment_weather) {

    private val viewModel: WeatherVM by viewModel()

    override fun initView() {

    }

    override fun createObserver() {
        viewModel.weatherLiveData.observe(this, {
            mDatabind.weather = it

//        val updateTime = weather.heWeather[0].basic.update.loc.split(" ").toTypedArray()[1] //正则表达式
            mDatabind.forecastLayout.removeAllViews()
            val daily_forecast = it?.HeWeather?.get(0)?.daily_forecast
            for (i in daily_forecast?.indices!!) {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.forecast_item, mDatabind.forecastLayout, false)
                val dateText = view.findViewById<TextView>(R.id.date_text)
                val infoText = view.findViewById<TextView>(R.id.info_text)
                val maxText = view.findViewById<TextView>(R.id.max_text)
                val minText = view.findViewById<TextView>(R.id.min_text)
                dateText.text = daily_forecast?.get(i).date
                infoText.text = daily_forecast?.get(i).cond.txt_d
                maxText.text = daily_forecast?.get(i).tmp.max
                minText.text = daily_forecast?.get(i).tmp.min
                mDatabind.forecastLayout.addView(view)
            }
        })
    }

    override fun lazyLoadData() {
        //CN101180201 安阳
        //CN101180101 郑州
        //CN101020100 上海
        val weatherUrl =
            "http://guolin.tech/api/weather?cityid=" + "CN101020100" + "&key=bc0418b57b2d4918819d3974ac1285d9"
        viewModel.getWeatherInfo(weatherUrl)
    }
}