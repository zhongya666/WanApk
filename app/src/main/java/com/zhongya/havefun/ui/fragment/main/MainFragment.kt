package com.zhongya.havefun.ui.fragment.main

import androidx.fragment.app.Fragment
import com.zhongya.havefun.R
import com.zhongya.havefun.app.MyApplication.Companion.appViewModel
import com.zhongya.havefun.databinding.FragmentMainBinding
import com.zhongya.havefun.ui.fragment.home.HomeFragment
import com.zhongya.havefun.ui.fragment.personal.MyFragment
import com.zhongya.havefun.ui.fragment.project.ProjectFragment
import com.zhongya.havefun.app.BaseAppFragment
import com.zhongya666.library.bus.LiveDataBus
import com.zhongya666.library.constant.Constant
import com.zhongya666.library.constant.NaviTo
import com.zhongya666.library.ext.bindBottomNavigationViewPager2
import com.zhongya666.library.ext.setUiTheme
import com.zhongya666.library.view.nav
import com.zhongya666.library.view.navigateAction

/**
 * GitHub/Gitee：zhongya666 2021/7/5 20:00
 *
 */
class MainFragment : BaseAppFragment<FragmentMainBinding>(R.layout.fragment_main) {

    companion object {
        @JvmStatic
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    private val fragments: ArrayList<Fragment> = arrayListOf(
        HomeFragment.newInstance(),
        ProjectFragment.newInstance(),
        MyFragment.newInstance()

    )

    override fun initView() {

        appViewModel.appColor.value?.let { setUiTheme(it, mDatabind.navView, mDatabind.viewLine) }

        bindBottomNavigationViewPager2(
            this,
            fragments,
            mDatabind.viewPager2,
            mDatabind.navView
        )


    }

    override fun createObserver() {

        LiveDataBus.get().with(Constant.NAVIGATION_TO, NaviTo::class.java)
            .observe(this, {
                when (it) {
                    NaviTo.Login -> {
                        nav().navigateAction(R.id.action_global_loginFragment)
                    }
                    NaviTo.Collect -> {
                        nav().navigateAction(R.id.action_wanFragment_to_collectFragment)
                    }
                    NaviTo.Web -> {
                        nav().navigateAction(R.id.action_global_webViewFragment)
                    }
                    else -> {
                    }
                }
            })

        appViewModel.appColor.observe(this, {
            setUiTheme(it, mDatabind.navView, mDatabind.viewLine)
        })

    }

    override fun lazyLoadData() {

    }
}