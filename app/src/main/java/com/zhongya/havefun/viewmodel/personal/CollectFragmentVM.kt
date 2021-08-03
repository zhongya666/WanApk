package com.zhongya.havefun.viewmodel.personal

import androidx.lifecycle.viewModelScope
import com.zhongya.havefun.app.net.repo.PersonalRepo
import com.zhongya.havefun.bean.personal.CollectResponse
import com.zhongya666.library.base.bean.BasePagingResponse
import com.zhongya666.library.base.viewmodel.BaseViewModel
import com.zhongya666.library.network.StateLiveData
import kotlinx.coroutines.launch

/**
 * GitHub/Gitee：zhongya666 2021/7/23 09:45
 *
 */
class CollectFragmentVM(val repo: PersonalRepo) : BaseViewModel() {

    val collectLiveData = StateLiveData<Any?>()
    val unCollectLiveData = StateLiveData<Any?>()

    var collectListLiveData = StateLiveData<BasePagingResponse<ArrayList<CollectResponse>>>()

    fun getCollectData(pageNo: Int, isShowLoading: Boolean = false) {

        viewModelScope.launch {
            repo.getCollectData(
                pageNo,
                collectListLiveData,
                isShowLoading
            )
        }
    }

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