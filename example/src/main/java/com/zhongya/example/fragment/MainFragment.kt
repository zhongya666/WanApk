package com.zhongya.example.fragment

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zhongya.example.R
import com.zhongya.example.activity.MainActivity
import com.zhongya.example.adapter.ExampleAdapter
import com.zhongya.example.bean.ExampleBean
import com.zhongya.example.databinding.ExampleFragmentMainBinding
import com.zhongya666.library.base.fragment.BaseDbFragment
import com.zhongya666.library.bean.ToExampleBean
import com.zhongya666.library.ext.initClose
import com.zhongya666.library.view.nav
import com.zhongya666.library.view.navigateAction

/**
 * GitHub/Gitee：zhongya666 2021/7/27 10:59
 *
 */
class MainFragment : BaseDbFragment<ExampleFragmentMainBinding>(R.layout.example_fragment_main) {

    private var toExampleBean : ToExampleBean? = null

    override fun initView() {

        toExampleBean = (activity as MainActivity).toExampleBean

        mDatabind.include.toolbar.initClose("案例演示") {
            activity?.finish() //路由过来的才显示返回键,直接结束
        }

        if (toExampleBean == null){
            mDatabind.include.toolbar.navigationIcon = null //如果为null 说明是单模块运行的,自然不需要返回键
        }


        mDatabind.includeRv.refreshLayout.setEnableRefresh(false)
        mDatabind.includeRv.refreshLayout.setEnableLoadMore(false)

        var adapter = ExampleAdapter(
            arrayListOf(
                ExampleBean(R.drawable.emoji1, "Dialog","一个简单方便的弹框,几行代码轻松搞定,可轻松修改字体属性、窗口尺寸。"),
                ExampleBean(R.drawable.emoji2, "倒计时按钮","一个简单方便的倒计时按钮，xml几行代码轻松配置属性，可修改按钮的字体属性和按钮属性,一个按钮可使用整个项目！"),
            )
        )

        mDatabind.includeRv.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mDatabind.includeRv.recyclerView.adapter = adapter

        adapter.setOnItemClickListener { _, _, position ->
            when(position){
                0 -> {
                    nav().navigateAction(R.id.action_mainFragment_to_dialogFragment)
                }
                1 -> {
                    nav().navigateAction(R.id.action_mainFragment_to_timeButtonFragment)
                }
            }
        }

    }

    override fun createObserver() {

    }

    override fun lazyLoadData() {

    }
}