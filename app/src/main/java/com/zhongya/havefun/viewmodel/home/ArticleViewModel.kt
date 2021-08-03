package com.zhongya.havefun.viewmodel.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zhongya.havefun.bean.home.ArticleData
import com.zhongya.havefun.bean.home.BannerData
import com.zhongya.havefun.app.net.repo.HomeRepo
import com.zhongya.havefun.app.net.repo.PersonalRepo
import com.zhongya666.library.base.viewmodel.BaseViewModel
import com.zhongya666.library.network.StateLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class ArticleViewModel(
    val repo: HomeRepo,
    val personalRepo: PersonalRepo
) : BaseViewModel() {

    val bannerLiveData = StateLiveData<List<BannerData>>()
    val collectLiveData = StateLiveData<Any?>()
    val unCollectLiveData = StateLiveData<Any?>()

    fun loadBanner() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getBanner(bannerLiveData)
        }
    }

    /**
     * 请求首页文章数据
     */
    fun articleList(): Flow<PagingData<ArticleData>> =
        repo.articleList().cachedIn(viewModelScope)

    fun collect(
        id: Int,
    ) {
        viewModelScope.launch {
            personalRepo.collect(
                id,
                collectLiveData
            )
        }
    }

    fun unCollect(
        id: Int,
    ) {
        viewModelScope.launch {
            personalRepo.unCollect(
                id,
                unCollectLiveData
            )
        }
    }
}