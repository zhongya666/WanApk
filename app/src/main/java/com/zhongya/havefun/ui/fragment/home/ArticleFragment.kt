package com.zhongya.havefun.ui.fragment.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.zhongya.havefun.R
import com.zhongya.havefun.app.MyApplication.Companion.appViewModel
import com.zhongya.havefun.bean.home.BannerData
import com.zhongya.havefun.databinding.HomeFragmentArticleBinding
import com.zhongya.havefun.ui.adapter.home.ArticleMultiAdapter
import com.zhongya.havefun.viewmodel.home.ArticleViewModel
import com.zhongya.havefun.app.BaseAppFragment
import com.zhongya666.library.constant.Constant
import com.zhongya666.library.network.IStateObserver
import com.zhongya666.library.view.nav
import com.zhongya666.library.view.navigateAction
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * GitHub/Gitee：zhongya666 2021/7/6 19:55
 *
 */
class ArticleFragment : BaseAppFragment<HomeFragmentArticleBinding>(
    R.layout.home_fragment_article
) {

    private val viewModel : ArticleViewModel by viewModel()
    private var position = -1

    companion object {
        @JvmStatic
        fun newInstance(): ArticleFragment {
            return ArticleFragment()
        }
    }

    private var adapter = ArticleMultiAdapter()


    override fun initView() {

        mDatabind.recyclerView.adapter = adapter


        mDatabind.refreshLayout.setOnRefreshListener {
            viewModel.loadBanner()
            adapter.refresh()
        }



        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest {
                //根据Paging的请求状态收缩刷新view
                if (it.refresh != LoadState.Loading) {
                    mDatabind.refreshLayout.finishRefresh()
                }
            }
        }

        adapter.run {
            setHeadClickListener {
                nav().navigateAction(
                    R.id.action_global_webViewFragment,
                    Bundle().apply {
                        putParcelable(Constant.Bundle.BannerData,it)
                    }
                )
            }

            setOnItemClickListener {
                nav().navigateAction(
                    R.id.action_global_webViewFragment,
                    Bundle().apply {
                        putParcelable(Constant.Bundle.ArticleData,it)
                    }
                )
            }

            setCollectClick { position, bean ->
                this@ArticleFragment.position = position
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
        /*
        * IStateObserver() 切记不能传入RecycleView
        * */
        viewModel.bannerLiveData.observe(this, object : IStateObserver<List<BannerData>>(null) {
            override fun onDataChange(data: List<BannerData>?) {
                super.onDataChange(data)
                //绑定banner
                data?.let {
                    adapter.addBannerList(it)
                }
            }

            override fun onReload(v: View?) {
            }
        })

        appViewModel.appColor.observe(this,{
            adapter.notifyDataSetChanged()
        })

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
        viewModel.loadBanner()
        //请求首页文章列表
        lifecycleScope.launchWhenCreated {
            viewModel.articleList().collectLatest {
                adapter.submitData(it)
            }
        }
    }


}