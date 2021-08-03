package com.zhongya.havefun.app.net.api

import com.zhongya666.library.base.bean.BasePagingResponse
import com.zhongya666.library.base.bean.BaseResponse
import com.zhongya666.project.bean.ProjectContent
import com.zhongya666.project.bean.ProjectTree
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * GitHub/Gitee：zhongya666 2021/6/28 09:24
 *
 */
interface ProjectApi {

    @GET("project/tree/json")
    suspend fun loadProjectTree() : BaseResponse<List<ProjectTree>>

    @GET("project/list/{path}/json")
    suspend fun loadProjectList(
            @Path("path") path: Int,
            @Query("cid") cid: Int
    ): BaseResponse<BasePagingResponse<List<ProjectContent>>>
}