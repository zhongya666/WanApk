package com.zhongya666.library.network

import android.util.Log
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.zhongya666.library.util.AppUtil
import com.zhongya666.library.util.TAG
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * GitHub/Gitee：zhongya666 2021/6/28 09:52
 *
 */

object RetrofitManager {

    private var mRetrofit: Retrofit? = null

    fun initRetrofit(): RetrofitManager {
        mRetrofit = Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com")
                .client(mOkClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return this
    }

    fun <T> getService(serviceClass: Class<T>): T {
        if (mRetrofit == null) {
            throw UninitializedPropertyAccessException("Retrofit必须初始化")
        } else {
            return mRetrofit!!.create(serviceClass)
        }
    }

    val cookieJar: PersistentCookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(AppUtil.getApplication()))

    private val mOkClient = OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .followRedirects(false)
//            .cookieJar(LocalCookieJar())
            .cookieJar(cookieJar)
            .addInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.e(TAG, "log: $message")
                }

            }).setLevel(HttpLoggingInterceptor.Level.BODY)).build()


}