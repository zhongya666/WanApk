package com.zhongya.havefun.ui.fragment.project

import android.view.View
import androidx.fragment.app.Fragment
import com.zhongya.havefun.R
import com.zhongya.havefun.app.MyApplication.Companion.appViewModel
import com.zhongya.havefun.databinding.ProjectFragmentBinding
import com.zhongya.havefun.viewmodel.project.ProjectViewModel
import com.zhongya.havefun.app.BaseAppFragment
import com.zhongya666.library.ext.bindViewPager2
import com.zhongya666.library.ext.init
import com.zhongya666.library.ext.setUiTheme
import com.zhongya666.library.network.IStateObserver
import com.zhongya666.project.bean.ProjectTree
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * GitHub/Gitee：zhongya666 2021/6/25 13:07
 *
 */
class ProjectFragment :
    BaseAppFragment<ProjectFragmentBinding>(R.layout.project_fragment) {

    companion object {
        @JvmStatic
        fun newInstance(): ProjectFragment {
            return ProjectFragment()
        }
    }

    private val viewModel: ProjectViewModel by viewModel()

    //标题集合
    val mTitleDataList: ArrayList<String> = arrayListOf()

    //fragment集合
    var fragments: ArrayList<Fragment> = arrayListOf()

    override fun initView() {

        appViewModel.appColor.value?.let { setUiTheme(it, mDatabind.include.fl) }

        viewModel.mProjectTreeLiveData.observe(
            viewLifecycleOwner,
            object : IStateObserver<List<ProjectTree>>(mDatabind.include.magicIndicator) {

                override fun onReload(v: View?) {
                    viewModel.loadProjectTree()
                }

                override fun onDataChange(data: List<ProjectTree>?) {
                    super.onDataChange(data)

                    mTitleDataList.clear()
                    fragments.clear()

                    data?.forEach {
                        mTitleDataList.add(it.name)
                        fragments.add(ProjectChildFragment.newInstance(it.id, false))
                    }

                    mDatabind.include.magicIndicator.navigator.notifyDataSetChanged()
                    mDatabind.include.viewPager2.adapter?.notifyDataSetChanged()
//                            viewPager2.offscreenPageLimit = fragments.size
                    mDatabind.include.viewPager2.offscreenPageLimit = 1


                }

                override fun onError(e: Throwable?) {
                    super.onError(e)
//                        Log.e("wzy", "onError: ${e?.printStackTrace()}")
                }
            }
        )
        //初始化viewpager2
        mDatabind.include.viewPager2.init(this, fragments)

        //初始化 magicIndicator
        mDatabind.include.magicIndicator.bindViewPager2(
            mDatabind.include.viewPager2,
            mTitleDataList
        )
    }

    override fun createObserver() {
        appViewModel.appColor.observe(this, {
            setUiTheme(it, mDatabind.include.fl)
        })
    }

    override fun lazyLoadData() {
        viewModel.loadProjectTree()
    }

}