package com.zhongya.havefun.app.net.repo

import com.zhongya.havefun.app.net.api.PersonalApi
import com.zhongya.havefun.bean.personal.CollectResponse
import com.zhongya.havefun.bean.personal.LoginResp
import com.zhongya.havefun.bean.personal.ScoreHistoryResponse
import com.zhongya.havefun.bean.personal.ScoreResponse
import com.zhongya666.library.base.bean.BasePagingResponse
import com.zhongya666.library.network.BaseRepository
import com.zhongya666.library.network.RetrofitManager
import com.zhongya666.library.network.StateLiveData

/**
 * GitHub/Gitee：zhongya666 2021/7/9 11:25
 * 好多地方用,这个需要单例模式
 */
class PersonalRepo(var service : PersonalApi) : BaseRepository() {


    suspend fun login(userName: String, password: String, stateLiveData: StateLiveData<LoginResp>) {
        executeResp({ service.login(userName, password) }, stateLiveData)
    }

    suspend fun register(
        userName: String,
        password: String,
        rePassword: String,
        stateLiveData: StateLiveData<LoginResp>
    ) {
        executeResp({ service.register(userName, password, rePassword) }, stateLiveData)
    }

    suspend fun getScoreRank(
        pageNo: Int,
        stateLiveData: StateLiveData<BasePagingResponse<ArrayList<ScoreResponse>>>,
        isShowLoading : Boolean = false
    ) {
        request(
            { service.getScoreRank(pageNo) },
            stateLiveData,
            isShowLoading
        )
    }

    suspend fun getMyScore(
        stateLiveData: StateLiveData<ScoreResponse>
    ) {
        request(
            { service.getMyScore() },
            stateLiveData,
            true
        )
    }

    suspend fun getScoreHistory(
        pageNo: Int,
        stateLiveData: StateLiveData<BasePagingResponse<ArrayList<ScoreHistoryResponse>>>,
        isShowLoading : Boolean = false
    ) {
        request(
            { service.getScoreHistory(pageNo) },
            stateLiveData,
            isShowLoading
        )
    }

    suspend fun getCollectData(
        pageNo: Int,
        stateLiveData: StateLiveData<BasePagingResponse<ArrayList<CollectResponse>>>,
        isShowLoading : Boolean = false
    ) {
        request(
            { service.getCollectData(pageNo) },
            stateLiveData,
            isShowLoading
        )
    }

    suspend fun collect(
        id: Int,
        stateLiveData: StateLiveData<Any?>,
        isShowLoading : Boolean = false
    ) {
        request(
            { service.collect(id) },
            stateLiveData,
            isShowLoading
        )
    }

    suspend fun unCollect(
        id: Int,
        stateLiveData: StateLiveData<Any?>,
        isShowLoading : Boolean = false
    ) {
        request(
            { service.unCollect(id) },
            stateLiveData,
            isShowLoading
        )
    }

}