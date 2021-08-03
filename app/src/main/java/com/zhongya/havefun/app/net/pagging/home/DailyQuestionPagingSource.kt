package com.zhongya.havefun.app.net.pagging.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zhongya.havefun.app.net.api.HomeApi
import com.zhongya.havefun.bean.home.ArticleData

/**
 * GitHub/Gitee：zhongya666 2021/7/8 9:41
 * 每日一问数据源，主要配合Paging3进行数据请求与显示
 */

class DailyQuestionPagingSource(private val service: HomeApi) :
    PagingSource<Int, ArticleData>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleData>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleData> {
        return try {
            val pageNum = params.key ?: 1
            val data = service.getDailyQuestion(pageNum)
            val preKey = if (pageNum > 1) pageNum - 1 else null
            LoadResult.Page(data.data?.datas!!, prevKey = preKey, nextKey = pageNum + 1)

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}