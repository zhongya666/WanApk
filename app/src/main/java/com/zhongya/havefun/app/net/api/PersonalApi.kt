package com.zhongya.havefun.app.net.api

import com.zhongya.havefun.bean.personal.Weather
import com.zhongya.havefun.bean.personal.CollectResponse
import com.zhongya666.library.base.bean.BaseResponse
import com.zhongya.havefun.bean.personal.LoginResp
import com.zhongya.havefun.bean.personal.ScoreHistoryResponse
import com.zhongya.havefun.bean.personal.ScoreResponse
import com.zhongya666.library.base.bean.BasePagingResponse
import retrofit2.http.*

/**
 * GitHub/Gitee：zhongya666 2021/7/9 11:05
 *
 */
interface PersonalApi {

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): BaseResponse<LoginResp>

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): BaseResponse<LoginResp>

    /**
     * 获取积分排行榜
     */
    @GET("coin/rank/{page}/json")
    suspend fun getScoreRank(
        @Path("page") page: Int
    ): BaseResponse<BasePagingResponse<ArrayList<ScoreResponse>>>

    /**
     * 获取当前账户的个人积分
     */
    @GET("lg/coin/userinfo/json")
    suspend fun getMyScore(): BaseResponse<ScoreResponse>

    /**
     * 获取积分历史
     */
    @GET("lg/coin/list/{page}/json")
    suspend fun getScoreHistory(
        @Path("page") page: Int
    ): BaseResponse<BasePagingResponse<ArrayList<ScoreHistoryResponse>>>

    /**
     * 获取收藏文章数据
     */
    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectData(@Path("page") pageNo: Int): BaseResponse<BasePagingResponse<ArrayList<CollectResponse>>>

    /**
     * 收藏文章
     */
    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int): BaseResponse<Any?>

    /**
     * 取消收藏文章
     */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollect(@Path("id") id: Int): BaseResponse<Any?>

    /**
     * 天气数据
     */
    @GET
    suspend fun getWeatherInfo(@Url url : String): Weather


}