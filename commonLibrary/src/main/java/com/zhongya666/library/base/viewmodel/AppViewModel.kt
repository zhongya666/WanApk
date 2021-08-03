package com.zhongya666.library.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zhongya666.library.base.BaseApp
import com.zhongya666.library.util.SettingUtil

/**
 * 在使用viewmodel时,不能将任何类型的context或含有context引用的对象传入viewModel,因为这可能导致内存泄漏.
 * 但如果你希望在viewmodel中使用context时,可以使用Androidviewmodel
 */
class AppViewModel(application: Application) : AndroidViewModel(application) {

    //App主题颜色 中大型项目不推荐以这种方式改变主题颜色，比较繁琐耦合，且容易有遗漏某些控件没有设置主题色
    var appColor = MutableLiveData<Int>()

    init {
        //默认值颜色
        appColor.value = SettingUtil.getColor(getApplication<BaseApp>())
    }

}