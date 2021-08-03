package com.zhongya.havefun.ui.fragment.home

import androidx.fragment.app.Fragment
import com.zhongya.havefun.R
import com.zhongya.havefun.app.MyApplication.Companion.appViewModel
import com.zhongya.havefun.databinding.HomeFragmentHomeBinding
import com.zhongya.havefun.app.BaseAppFragment
import com.zhongya666.library.ext.bindViewPager2
import com.zhongya666.library.ext.init
import com.zhongya666.library.ext.setUiTheme

/**
 * GitHub/Gitee：zhongya666 2021/6/25 13:07
 *
 */
class HomeFragment : BaseAppFragment<HomeFragmentHomeBinding>(
    R.layout.home_fragment_home) {

    companion object {
        @JvmStatic
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    //标题集合
    private val mTitleDataList: ArrayList<String> = arrayListOf(
        "每日一问",
        "首页",
        "广场"
    )

    //fragment集合
    var fragments: ArrayList<Fragment> = arrayListOf(
        DailyQuestionFragment.newInstance(),
        ArticleFragment.newInstance(),
        SquareFragment.newInstance()
    )

    override fun initView() {

        appViewModel.appColor.value?.let { setUiTheme(it, mDatabind.include.fl) }

        mDatabind.include.viewPager2.init(this, fragments)
        mDatabind.include.magicIndicator.bindViewPager2(
            mDatabind.include.viewPager2,
            mTitleDataList,
            350
        )
    }

    override fun createObserver() {
        appViewModel.appColor.observe(this,{
            setUiTheme(it, mDatabind.include.fl)
        })
    }

    override fun lazyLoadData() {
    }
}