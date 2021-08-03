package com.zhongya.havefun.app

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.zhongya666.library.base.activity.BaseDbActivity
import com.zhongya666.library.constant.Constant
import com.zhongya666.library.util.SPUtils

/**
 * GitHub/Gitee：zhongya666 2021/6/3 9:51

 * 根据该模块的需要来封装
 * BaseAppActivity : BaseDbActivity : BaseActivity : AppCompatActivity
 */
abstract class BaseAppActivity<DB : ViewDataBinding>(layoutId: Int)
    : BaseDbActivity<DB>(layoutId){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getUserData()

    }

    private fun getUserData() {
    }


    protected fun isLogin(): Boolean {
        val userName = SPUtils.getString(Constant.Sp.USER_NAME)
        if (userName == null || userName.isEmpty()) {
            return false
        }
        return true
    }

}