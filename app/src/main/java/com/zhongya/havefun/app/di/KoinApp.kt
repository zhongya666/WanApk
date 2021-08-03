package com.zhongya.havefun.app.di

import com.zhongya.havefun.app.net.api.HomeApi
import com.zhongya.havefun.app.net.api.PersonalApi
import com.zhongya.havefun.app.net.api.ProjectApi
import com.zhongya.havefun.app.net.repo.HomeRepo
import com.zhongya.havefun.app.net.repo.PersonalRepo
import com.zhongya.havefun.app.net.repo.ProjectRepository
import com.zhongya.havefun.viewmodel.MainViewModel
import com.zhongya.havefun.viewmodel.home.ArticleViewModel
import com.zhongya.havefun.viewmodel.home.DailyQuestionViewModel
import com.zhongya.havefun.viewmodel.home.HomeViewModel
import com.zhongya.havefun.viewmodel.home.SquareViewModel
import com.zhongya.havefun.viewmodel.other.WebViewModel
import com.zhongya.havefun.viewmodel.personal.*
import com.zhongya.havefun.viewmodel.project.ProjectChildViewModel
import com.zhongya.havefun.viewmodel.project.ProjectViewModel
import com.zhongya666.library.network.RetrofitManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { RetrofitManager.initRetrofit().getService(HomeApi::class.java) }
    single { RetrofitManager.initRetrofit().getService(ProjectApi::class.java) }
    single { RetrofitManager.initRetrofit().getService(PersonalApi::class.java) }

    single { HomeRepo() }
    single { ProjectRepository(get()) }
    single { PersonalRepo(get()) }

    viewModel { MainViewModel() }

    viewModel { HomeViewModel() }
    viewModel { DailyQuestionViewModel(get(), get()) }
    viewModel { ArticleViewModel(get(), get()) }
    viewModel { SquareViewModel(get(), get()) }

    viewModel { ProjectViewModel(get()) }
    viewModel { ProjectChildViewModel(get(), get()) }

    viewModel { CollectFragmentVM(get()) }
    viewModel { LoginFragmentVM(get()) }
    viewModel { MyFragmentViewModel(get()) }
    viewModel { ScoreHistoryVM(get()) }
    viewModel { ScoreVM(get()) }
    viewModel { WeatherVM(get()) }
    viewModel { MyQRFragmentVM() }

    viewModel { WebViewModel() }


}