package com.zhongya.havefun.app.net.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zhongya.havefun.bean.home.ArticleData
import com.zhongya.havefun.bean.home.BannerData
import com.zhongya.havefun.app.net.pagging.home.ArticlePagingDataSource
import com.zhongya.havefun.app.net.pagging.home.DailyQuestionPagingSource
import com.zhongya.havefun.app.net.pagging.home.SquareDataSource
import com.zhongya666.library.network.BaseRepository
import com.zhongya666.library.network.RetrofitManager
import com.zhongya666.library.network.StateLiveData
import kotlinx.coroutines.flow.Flow

/**
 * GitHub/Gitee：zhongya666 2021/7/7 15:11
 *
 */
class HomeRepo : BaseRepository() {


    companion object {
        private const val PAGE_SIZE = 10
        val config = PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = 10,
            initialLoadSize = 10,
            enablePlaceholders = false,
//            maxSize = PAGE_SIZE * 5
        )
    }


    private var service: com.zhongya.havefun.app.net.api.HomeApi = RetrofitManager.initRetrofit().getService(
        com.zhongya.havefun.app.net.api.HomeApi::class.java)

    /**
     * 请求首页banner
     */
    suspend fun getBanner(bannerLiveData: StateLiveData<List<BannerData>>) {
        executeResp({ service.getBanner() }, bannerLiveData)
    }

    /**
     * 请求首页文章，
     * Room+network进行缓存
     */
    fun articleList(): Flow<PagingData<ArticleData>> {

        return Pager(config) {
            ArticlePagingDataSource(service)
        }.flow
    }

    /**
     * 请求每日一问
     */
    fun getDailyQuestion(): Flow<PagingData<ArticleData>> {

        return Pager(config) {
            DailyQuestionPagingSource(service)
        }.flow
    }

    /**
     * 请求广场数据
     */
    fun getSquareData(): Flow<PagingData<ArticleData>> {
        return Pager(config) {
            SquareDataSource(service)
        }.flow
    }

}