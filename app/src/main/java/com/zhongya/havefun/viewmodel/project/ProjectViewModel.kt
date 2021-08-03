package com.zhongya.havefun.viewmodel.project

import androidx.lifecycle.viewModelScope
import com.zhongya.havefun.app.net.repo.ProjectRepository
import com.zhongya666.library.base.viewmodel.BaseViewModel
import com.zhongya666.library.network.StateLiveData
import com.zhongya666.project.bean.ProjectTree
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProjectViewModel(val repo: ProjectRepository) : BaseViewModel() {

    val mProjectTreeLiveData = StateLiveData<List<ProjectTree>>()

    fun loadProjectTree() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.loadProjectTree(mProjectTreeLiveData)
        }
    }
}