package com.zhongya.havefun.ui.fragment.personal

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.zhongya.havefun.R
import com.zhongya.havefun.bean.personal.ScoreHistoryResponse
import com.zhongya.havefun.databinding.PersonalFragmentScoreBinding
import com.zhongya.havefun.ui.adapter.personal.ScoreHistoryAdapter
import com.zhongya.havefun.viewmodel.personal.ScoreHistoryVM
import com.zhongya666.library.base.bean.BasePagingResponse
import com.zhongya.havefun.app.BaseAppFragment
import com.zhongya666.library.ext.initClose
import com.zhongya666.library.network.IStateObserver
import com.zhongya666.library.view.nav
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * GitHub/Gitee：zhongya666 2021/7/13 11:09
 *
 */
class ScoreHistoryFragment :
    BaseAppFragment<PersonalFragmentScoreBinding>(R.layout.personal_fragment_score) {

    private val viewModel: ScoreHistoryVM by viewModel()
    private var pageNo = 1

    private lateinit var adapter: ScoreHistoryAdapter

    override fun initView() {

        mDatabind.include.toolbar.run {
            initClose("积分记录") {
                nav().navigateUp()
            }
        }

        mDatabind.includeRv.refreshLayout.setOnRefreshLoadMoreListener(object :
            OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                pageNo = 1
                viewModel.getScoreHistory(pageNo)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                viewModel.getScoreHistory(pageNo)
            }
        })

        adapter = ScoreHistoryAdapter(arrayListOf())
        mDatabind.includeRv.recyclerView.layoutManager = LinearLayoutManager(context)
        mDatabind.includeRv.recyclerView.adapter = adapter
    }

    override fun createObserver() {
        viewModel.scoreHistoryLiveData.observe(
            this,
            object :
                IStateObserver<BasePagingResponse<ArrayList<ScoreHistoryResponse>>>(mDatabind.includeRv.refreshLayout) {
                override fun onDataChange(data: BasePagingResponse<ArrayList<ScoreHistoryResponse>>?) {
                    super.onDataChange(data)

                    if (pageNo == 1) {
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
        viewModel.getScoreHistory(pageNo, true)
    }
}