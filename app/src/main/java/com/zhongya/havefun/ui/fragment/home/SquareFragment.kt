package com.zhongya.havefun.ui.fragment.home

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.zhongya.havefun.R
import com.zhongya.havefun.app.MyApplication.Companion.appViewModel
import com.zhongya.havefun.databinding.HomeFragmentSquareBinding
import com.zhongya.havefun.ui.adapter.home.SquarePagingAdapter
import com.zhongya.havefun.viewmodel.home.SquareViewModel
import com.zhongya.havefun.app.BaseAppFragment
import com.zhongya666.library.constant.Constant
import com.zhongya666.library.view.nav
import com.zhongya666.library.view.navigateAction
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * GitHub/Gitee：zhongya666 2021/7/6 19:55
 *
 */
class SquareFragment : BaseAppFragment<HomeFragmentSquareBinding>(
    R.layout.home_fragment_square
) {

    private var position = -1

    companion object {
        @JvmStatic
        fun newInstance(): SquareFragment {
            return SquareFragment()
        }
    }

    private val viewModel: SquareViewModel by viewModel()

    private val pagingAdapter = SquarePagingAdapter()

    override fun initView() {
        mDatabind.recyclerView.adapter = pagingAdapter


        //下拉刷新
        mDatabind.refreshLayout.setOnRefreshListener {
            pagingAdapter.refresh()
        }
        lifecycleScope.launchWhenCreated {
            pagingAdapter.loadStateFlow.collectLatest {
                //根据Paging的请求状态收缩刷新view
                if (it.refresh != LoadState.Loading) {
                    mDatabind.refreshLayout.finishRefresh()
                }
            }
        }

        pagingAdapter.run {
            setOnItemClickListener { _, bean ->
                nav().navigateAction(
                    R.id.action_global_webViewFragment,
                    Bundle().apply {
                        putParcelable(Constant.Bundle.ArticleData, bean)
                    }
                )
            }

            setCollectClick { position, bean ->
                this@SquareFragment.position = position
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
        appViewModel.appColor.observe(this, {
            pagingAdapter.notifyDataSetChanged()
        })

        viewModel.collectLiveData.observe(this, {
            if (it.errorCode == 0) {
                pagingAdapter.notifyItemChanged(position)
            }
        })

        viewModel.unCollectLiveData.observe(this, {
            if (it.errorCode == 0) {
                pagingAdapter.notifyItemChanged(position)
            }
        })
    }

    override fun lazyLoadData() {
        lifecycleScope.launchWhenCreated {
            viewModel.squarePagingFlow().collectLatest {
                pagingAdapter.submitData(it)
            }
        }
    }
}