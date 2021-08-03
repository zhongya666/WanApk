package com.zhongya.havefun.ui.fragment.personal

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.zhongya.havefun.R
import com.zhongya.havefun.bean.personal.CollectResponse
import com.zhongya.havefun.databinding.PersonalFragmentCollectBinding
import com.zhongya.havefun.ui.adapter.personal.CollectAdapter
import com.zhongya.havefun.viewmodel.personal.CollectFragmentVM
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
class CollectFragment :
    BaseAppFragment<PersonalFragmentCollectBinding>(R.layout.personal_fragment_collect) {

    private val viewModel: CollectFragmentVM by viewModel()
    private var pageNo = 0
    private var adapter = CollectAdapter(arrayListOf())

    private var position = -1

    override fun initView() {
        mDatabind.include.toolbar.initClose("收藏") {
            nav().navigateUp()
        }

        mDatabind.includeRv.refreshLayout.setOnRefreshLoadMoreListener(object :
            OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                pageNo = 0
                viewModel.getCollectData(pageNo)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                viewModel.getCollectData(pageNo)
            }
        })

        mDatabind.includeRv.recyclerView.layoutManager = LinearLayoutManager(context)
        mDatabind.includeRv.recyclerView.adapter = adapter

        adapter.run {

            setOnItemClickListener { adapter, view, position ->
                nav().navigateAction(
                    R.id.action_global_webViewFragment,
                    Bundle().apply {
                        putParcelable(
                            Constant.Bundle.CollectResponse,
                            this@CollectFragment.adapter.data[position]
                        )
                    }
                )
            }

            setCollectClick { position, bean ->
                this@CollectFragment.position = position
                if (bean.collect) {
                    viewModel.unCollect(bean.originId)
                    bean.collect = false //修改数据源,不要重新访问列表了
                } else {
                    viewModel.collect(bean.originId)
                    bean.collect = true //修改数据源,不要重新访问列表了
                }
            }
        }
    }

    override fun createObserver() {

        viewModel.collectListLiveData.observe(
            this,
            object :
                IStateObserver<BasePagingResponse<ArrayList<CollectResponse>>>(mDatabind.includeRv.refreshLayout) {
                override fun onDataChange(data: BasePagingResponse<ArrayList<CollectResponse>>?) {
                    super.onDataChange(data)

                    data?.datas?.forEach { it.collect = true }//先赋值为收藏

                    if (pageNo == 0) {
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
        viewModel.getCollectData(pageNo, true)
    }
}