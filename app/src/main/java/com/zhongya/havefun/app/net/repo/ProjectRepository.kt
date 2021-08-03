package com.zhongya.havefun.app.net.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zhongya.havefun.app.net.api.ProjectApi
import com.zhongya.havefun.app.net.pagging.project.ProjectDataSource
import com.zhongya666.library.network.BaseRepository
import com.zhongya666.library.network.StateLiveData
import com.zhongya666.project.bean.ProjectContent
import com.zhongya666.project.bean.ProjectTree
import kotlinx.coroutines.flow.Flow

/**
 * GitHub/Gitee：zhongya666 2021/6/28 09:33
 *
 */
class ProjectRepository(val service: ProjectApi) : BaseRepository() {

    private val pageSize = 10

    suspend fun loadProjectTree(
        stateLiveData: StateLiveData<List<ProjectTree>>
    ) {
        return executeResp(
            { service.loadProjectTree() },
            stateLiveData
        )
    }

    /**
     * 通过项目分类的ID，利用Paging3+Flow请求项目详细列表。
     *
     */
    fun loadProjectList(id: Int): Flow<PagingData<ProjectContent>> {

        val config = PagingConfig(
            pageSize = pageSize,
            prefetchDistance = 5,
            initialLoadSize = 10/*,
                maxSize = pageSize * 2*/
        )

        return Pager(config) {
            ProjectDataSource(service, id)
        }.flow
    }
}