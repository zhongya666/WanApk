package com.zhongya.havefun.viewmodel.personal

import androidx.lifecycle.viewModelScope
import com.zhongya.havefun.app.net.repo.PersonalRepo
import com.zhongya.havefun.bean.personal.ScoreResponse
import com.zhongya666.library.base.bean.BasePagingResponse
import com.zhongya666.library.base.viewmodel.BaseViewModel
import com.zhongya666.library.network.StateLiveData
import kotlinx.coroutines.launch

class ScoreVM(val repo: PersonalRepo) : BaseViewModel() {

    var scoreLiveData = StateLiveData<BasePagingResponse<ArrayList<ScoreResponse>>>()

    fun getScoreRank(pageNo:Int,isShowLoading : Boolean = false) {

        viewModelScope.launch {
            repo.getScoreRank(
                pageNo,
                scoreLiveData,
                isShowLoading
            )
        }
    }
}