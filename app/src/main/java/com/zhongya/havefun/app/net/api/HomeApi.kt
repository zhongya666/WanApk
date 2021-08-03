package com.zhongya.havefun.app.net.api

import com.zhongya.havefun.bean.home.ArticleData
import com.zhongya.havefun.bean.home.BannerData
import com.zhongya666.library.base.bean.BasePagingResponse
import com.zhongya666.library.base.bean.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeApi {


    @GET("banner/json")
    suspend fun getBanner(): BaseResponse<List<BannerData>>

    @GET("article/list/{page}/json")
    suspend fun articleList(@Path("page") page: Int):
            BaseResponse<BasePagingResponse<List<ArticleData>>>

    @GET("wenda/list/{page}/json")
    suspend fun getDailyQuestion(@Path("page") page: Int):
            BaseResponse<BasePagingResponse<List<ArticleData>>>

    @GET("user_article/list/{page}/json")
    suspend fun getSquareData(@Path("page") page: Int):
            BaseResponse<BasePagingResponse<List<ArticleData>>>
}
