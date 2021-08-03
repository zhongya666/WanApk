package com.zhongya.havefun.viewmodel.personal

import androidx.lifecycle.viewModelScope
import com.zhongya666.library.base.viewmodel.BaseViewModel
import com.zhongya666.library.network.StateLiveData
import com.zhongya.havefun.app.net.repo.PersonalRepo
import com.zhongya.havefun.bean.personal.LoginResp
import kotlinx.coroutines.launch

/**
 * GitHub/Gitee：zhongya666 2021/6/3 11:07
 *
 */
class LoginFragmentVM(val repo: PersonalRepo) : BaseViewModel() {

    val loginLiveData = StateLiveData<LoginResp>()
    val registerLiveData = StateLiveData<LoginResp>()

    fun login(userName: String, password: String) {

        viewModelScope.launch {
            repo.login(userName, password, loginLiveData)
        }

    }

    fun register(
        userName: String,
        password: String,
        rePassword: String,
    ) {
        viewModelScope.launch {
            repo.register(userName, password, rePassword, registerLiveData)
        }

    }
}