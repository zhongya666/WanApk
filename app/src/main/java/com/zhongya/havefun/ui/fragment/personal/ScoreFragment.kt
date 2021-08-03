package com.zhongya.havefun.ui.fragment.personal

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.zhongya.havefun.R
import com.zhongya.havefun.bean.home.BannerData
import com.zhongya.havefun.bean.personal.ScoreResponse
import com.zhongya.havefun.databinding.PersonalFragmentScoreBinding
import com.zhongya.havefun.ui.adapter.personal.ScoreAdapter
import com.zhongya.havefun.viewmodel.personal.ScoreVM
import com.zhongya666.library.base.bean.BasePagingResponse
import com.zhongya.havefun.app.BaseAppFragment
import com.zhongya666.library.constant.Constant
import com.zhongya666.library.ext.initClose
import com.zhongya666.library.network.IStateObserver
import com.zhongya666.library.view.nav
import com.zhongya666.library.view.navigateAction
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * GitHub/Gitee：zhongya666 2021/7/13 11:09
 *
 */
class ScoreFragment :
    BaseAppFragment<PersonalFragmentScoreBinding>(R.layout.personal_fragment_score) {

    private val viewModel: ScoreVM by viewModel()
    private var pageNo = 1

    private var adapter = ScoreAdapter(arrayListOf())
    private lateinit var scoreResponse: ScoreResponse

    override fun initView() {

        mDatabind.include.toolbar.run {
            inflateMenu(R.menu.menu_score)

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.integral_guize -> {
                        nav().navigateAction(R.id.action_global_webViewFragment,
                            Bundle().apply {
                                putParcelable(
                                    Constant.Bundle.BannerData,
                                    BannerData(
                                        title = "积分规则",
                                        url = "https://www.wanandroid.com/blog/show/2653"
                                    )
                                )
                            }
                        )
                    }
                    R.id.integral_history -> {
                        nav().navigateAction(R.id.action_scoreFragment_to_scoreHistoryFragment)
                    }
                }
                true
            }

            initClose("积分排行") {
                nav().navigateUp()
            }
        }


        arguments?.run {
            scoreResponse = getParcelable<ScoreResponse>(Constant.Bundle.ScoreResponse)!!
        }

        mDatabind.includeRv.refreshLayout.setOnRefreshLoadMoreListener(object :
            OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                pageNo = 1
                viewModel.getScoreRank(pageNo)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                viewModel.getScoreRank(pageNo)
            }
        })

        mDatabind.includeRv.recyclerView.layoutManager = LinearLayoutManager(context)
        mDatabind.includeRv.recyclerView.adapter = adapter
    }

    override fun createObserver() {
        viewModel.scoreLiveData.observe(
            this,
            object :
                IStateObserver<BasePagingResponse<ArrayList<ScoreResponse>>>(mDatabind.includeRv.refreshLayout) {
                override fun onDataChange(data: BasePagingResponse<ArrayList<ScoreResponse>>?) {
                    super.onDataChange(data)

                    if (pageNo == 1) {
                        data?.datas?.add(0, scoreResponse)
                        adapter.setList(data?.datas)
                        mDatabind.includeRv.refreshLayout.finishRefresh()
                    } else {
                        adapter.addData(data?.datas!!)
                        mDatabind.includeRv.refreshLayout.finishLoadMore()
                    }

                    pageNo++
                }

                override fun onReload(v: View?) {
                }
            }
        )
    }

    override fun lazyLoadData() {
        viewModel.getScoreRank(pageNo, true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}