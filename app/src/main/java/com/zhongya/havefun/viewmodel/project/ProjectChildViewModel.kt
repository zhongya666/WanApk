package com.zhongya.havefun.viewmodel.project

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zhongya.havefun.app.net.repo.PersonalRepo
import com.zhongya666.library.base.viewmodel.BaseViewModel
import com.zhongya666.project.bean.ProjectContent
import com.zhongya.havefun.app.net.repo.ProjectRepository
import com.zhongya666.library.network.StateLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class ProjectChildViewModel(
    val repo : PersonalRepo,
    val repo_project : ProjectRepository,
    ) : BaseViewModel() {


    val collectLiveData = StateLiveData<Any?>()
    val unCollectLiveData = StateLiveData<Any?>()

    fun loadProjectList(id: Int) : Flow<PagingData<ProjectContent>> =
        repo_project.loadProjectList(id).cachedIn(viewModelScope)

    fun collect(
        id: Int,
    ) {
        viewModelScope.launch {
            repo.collect(
                id,
                collectLiveData
            )
        }
    }

    fun unCollect(
        id: Int,
    ) {
        viewModelScope.launch {
            repo.unCollect(
                id,
                unCollectLiveData
            )
        }
    }

}