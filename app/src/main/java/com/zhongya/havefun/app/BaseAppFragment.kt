package com.zhongya.havefun.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.zhongya666.library.base.fragment.BaseDbFragment
import com.zhongya666.library.bus.LiveDataBus
import com.zhongya666.library.bus.bean.BaseBusBean
import com.zhongya666.library.constant.Constant
import com.zhongya666.library.util.SPUtils

/**
 * GitHub/Gitee：zhongya666 2021/6/3 13:30
 *
 * 根据该模块的需要来封装
 * 如果不想用Databind 请继承BaseFragment
 *
 * BaseAppFragment : BaseDbFragment : BaseFragment : Fragment
 */
abstract class BaseAppFragment<DB : ViewDataBinding>(
    layoutId: Int
) : BaseDbFragment<DB>(layoutId) {

    var userName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isLogin()
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    protected fun isLogin(): Boolean {
        SPUtils.getString(Constant.Sp.USER_NAME).let {
            userName = it
        }
        if (userName == null || userName!!.isEmpty()) {
            return false
        }
        return true
    }

    override fun createObserver() {

        LiveDataBus.get().with(Constant.Bus.app_event, BaseBusBean::class.java)
            .observe(this, Observer {
                when (it) {
                    BaseBusBean.LOGIN_OUT -> {
                        isLogin()
                    }
                    else -> {
                    }
                }
            })
    }

}