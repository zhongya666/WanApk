package com.zhongya.havefun.app.net.pagging.project

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zhongya666.project.bean.ProjectContent

/**
 * GitHub/Gitee：zhongya666 2021/6/30 13:33
 *
 */
class ProjectDataSource(private val service: com.zhongya.havefun.app.net.api.ProjectApi, private val id: Int) : PagingSource<Int, ProjectContent>() {

    override fun getRefreshKey(state: PagingState<Int, ProjectContent>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProjectContent> {

        try {
            //页码未定义置为1
            var currentPage = params.key ?: 1
            //仓库层请求数据
            var response = service.loadProjectList(currentPage,id)
            //当前页码 小于 总页码 页面加1
            var nextPage = if (currentPage < response.data?.pageCount ?: 0) {
                currentPage + 1
            } else {
                //没有更多数据
                null
            }
            return LoadResult.Page(
                    data = response.data!!.datas,
                    prevKey = null,// 仅向后翻页
                    nextKey = nextPage
            )
        } catch (e: Exception) {
            return LoadResult.Error(throwable = e)
        }


    }


}