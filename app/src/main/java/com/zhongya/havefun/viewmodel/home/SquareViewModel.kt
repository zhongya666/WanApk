package com.zhongya.havefun.viewmodel.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zhongya.havefun.app.net.repo.HomeRepo
import com.zhongya.havefun.app.net.repo.PersonalRepo
import com.zhongya.havefun.bean.home.ArticleData
import com.zhongya666.library.base.viewmodel.BaseViewModel
import com.zhongya666.library.network.StateLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class SquareViewModel(
    val repo: HomeRepo,
    val personalRepo: PersonalRepo
) : BaseViewModel() {

    val collectLiveData = StateLiveData<Any?>()
    val unCollectLiveData = StateLiveData<Any?>()

    /**
     * 查询广场数据
     */
    fun squarePagingFlow(): Flow<PagingData<ArticleData>> =
        repo.getSquareData().cachedIn(viewModelScope)

    fun collect(
        id: Int,
    ) {
        viewModelScope.launch {
            personalRepo.collect(
                id,
                collectLiveData
            )
        }
    }

    fun unCollect(
        id: Int,
    ) {
        viewModelScope.launch {
            personalRepo.unCollect(
                id,
                unCollectLiveData
            )
        }
    }


}