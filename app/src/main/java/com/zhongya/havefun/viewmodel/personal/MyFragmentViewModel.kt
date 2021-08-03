package com.zhongya.havefun.viewmodel.personal

import androidx.lifecycle.viewModelScope
import com.zhongya.havefun.app.net.repo.PersonalRepo
import com.zhongya.havefun.bean.personal.ScoreResponse
import com.zhongya666.library.base.viewmodel.BaseViewModel
import com.zhongya666.library.network.StateLiveData
import kotlinx.coroutines.launch

class MyFragmentViewModel(val repo: PersonalRepo) : BaseViewModel() {

    var myScoreLiveData = StateLiveData<ScoreResponse>()

    fun getMyScore() {
        viewModelScope.launch {
            repo.getMyScore(myScoreLiveData)
        }
    }

}