package com.zhongya.havefun.bean.personal

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@SuppressLint("ParcelCreator")
@Parcelize
data class Weather(
        var HeWeather: List<HeWeatherBean> = arrayListOf()
) : Parcelable {

    @Parcelize

    data class HeWeatherBean(
        var basic: @RawValue BasicBean,
        var update: @RawValue UpdateBean,
        var status: String = "",
        var now: @RawValue NowBean,
        var daily_forecast: List<DailyForecastBean> = arrayListOf(),
        var aqi: @RawValue AqiBean,
        var suggestion: @RawValue SuggestionBean,
        var msg: String = ""
    ) : Parcelable {
        @Parcelize
        data class BasicBean(
                var cid: String = "",
                var location: String = "",
                var parent_city: String = "",
                var admin_area: String = "",
                var cnty: String = "",
                var lat: String = "",
                var lon: String = "",
                var tz: String = "",
                var city: String = "",
                var id: String = "",
                var update: @RawValue UpdateBean
        ) : Parcelable {
            @Parcelize
            data class UpdateBean(
                    var loc: String = "",
                    var utc: String = ""
            ) : Parcelable
        }

        @Parcelize
        data class UpdateBean(
                var loc: String = "",
                var utc: String = ""
        ) : Parcelable

        @Parcelize
        data class NowBean(
                var cloud: String = "",
                var cond_code: String = "",
                var cond_txt: String = "",
                var fl: String = "",
                var hum: String = "",
                var pcpn: String = "",
                var pres: String = "",
                var tmp: String = "",
                var vis: String = "",
                var wind_deg: String = "",
                var wind_dir: String = "",
                var wind_sc: String = "",
                var wind_spd: String = "",
                var cond: @RawValue CondBean
        ) : Parcelable {
            @Parcelize
            data class CondBean(
                    var code: String = "",
                    var txt: String = ""
            ) : Parcelable
        }

        @Parcelize
        data class AqiBean(
                var city: @RawValue CityBean
        ) : Parcelable {
            @Parcelize
            data class CityBean(
                    var aqi: String = "",
                    var pm25: String = "",
                    var qlty: String = ""
            ) : Parcelable
        }

        @Parcelize
        data class SuggestionBean(
            var comf: @RawValue ComfBean,
            var sport: @RawValue SportBean,
            var cw: @RawValue CwBean
        ) : Parcelable {
            @Parcelize
            data class ComfBean(
                    var type: String = "",
                    var brf: String = "",
                    var txt: String = ""
            ) : Parcelable

            @Parcelize
            data class SportBean(
                    var type: String = "",
                    var brf: String = "",
                    var txt: String = ""
            ) : Parcelable

            @Parcelize
            data class CwBean(
                    var type: String = "",
                    var brf: String = "",
                    var txt: String = ""
            ) : Parcelable
        }

        @Parcelize
        data class DailyForecastBean(
            var date: String = "",
            var cond: @RawValue CondBean,
            var tmp: @RawValue TmpBean
        ) : Parcelable {
            @Parcelize
            data class CondBean(
                    var txt_d: String = ""
            ): Parcelable

            @Parcelize
            class TmpBean(
                    var max: String = "",
                    var min: String = ""
            ): Parcelable
        }
    }
}