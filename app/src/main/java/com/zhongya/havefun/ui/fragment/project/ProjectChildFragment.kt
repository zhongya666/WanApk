package com.zhongya.havefun.ui.fragment.project

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.zhongya.havefun.R
import com.zhongya.havefun.databinding.ProjectFragmentProjectChildBinding
import com.zhongya.havefun.ui.adapter.project.ProjectListAdapter
import com.zhongya.havefun.viewmodel.project.ProjectChildViewModel
import com.zhongya666.library.base.fragment.BaseDbFragment
import com.zhongya666.library.constant.Constant
import com.zhongya666.library.view.nav
import com.zhongya666.library.view.navigateAction
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * GitHub/Gitee：zhongya666 2021/6/30 16:59
 *
 */
class ProjectChildFragment :
    BaseDbFragment<ProjectFragmentProjectChildBinding>(
        R.layout.project_fragment_project_child
    ) {

    companion object {
        fun newInstance(cid: Int, isNew: Boolean): ProjectChildFragment {
            val args = Bundle()
            args.putInt("cid", cid)
            args.putBoolean("isNew", isNew)
            val fragment = ProjectChildFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: ProjectChildViewModel by viewModel()

    private var adapter = ProjectListAdapter()

    //改项目对应的id
    private var cid = 0

    private var position = -1

    override fun initView() {

        arguments?.let {
            cid = it.getInt("cid")
        }
        mDatabind.recyclerView.adapter = adapter

        mDatabind.refreshLayout.setOnRefreshListener {
            adapter.refresh()
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest {
                if (it.refresh != LoadState.Loading) {
                    mDatabind.refreshLayout.finishRefresh()
                }
            }
        }

        adapter.run {

            setOnItemClickListener { _, bean ->
                nav().navigateAction(
                    R.id.action_global_webViewFragment,
                    Bundle().apply {
                        putParcelable(Constant.Bundle.ProjectContent, bean)
                    }
                )
            }

            setCollectClick { position, bean ->
                this@ProjectChildFragment.position = position
                if (bean.collect) {
                    viewModel.unCollect(bean.id)
                    bean.collect = false //修改数据源,不要重新访问列表了
                } else {
                    viewModel.collect(bean.id)
                    bean.collect = true //修改数据源,不要重新访问列表了
                }
            }
        }

    }

    override fun createObserver() {
        viewModel.collectLiveData.observe(this, {
            if (it.errorCode == 0) {
                adapter.notifyItemChanged(position)
            }
        })

        viewModel.unCollectLiveData.observe(this, {
            if (it.errorCode == 0) {
                adapter.notifyItemChanged(position)
            }
        })
    }

    override fun lazyLoadData() {
        query()
    }

    private fun query() {
        lifecycleScope.launch {
            viewModel.loadProjectList(cid).collectLatest {
                adapter.submitData(it)
            }
        }
    }
}