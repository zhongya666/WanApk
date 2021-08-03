package com.zhongya.havefun.ui.fragment.other

import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.zhongya.havefun.R
import com.zhongya.havefun.bean.home.ArticleData
import com.zhongya.havefun.bean.home.BannerData
import com.zhongya.havefun.bean.personal.CollectResponse
import com.zhongya.havefun.bean.personal.CollectType
import com.zhongya.havefun.databinding.WebFragmentWebviewBinding
import com.zhongya.havefun.viewmodel.other.WebViewModel
import com.zhongya.havefun.app.BaseAppFragment
import com.zhongya666.library.constant.Constant
import com.zhongya666.library.ext.initClose
import com.zhongya666.library.view.nav
import com.zhongya666.project.bean.ProjectContent
import org.koin.androidx.viewmodel.ext.android.viewModel

class WebViewFragment :
    BaseAppFragment<WebFragmentWebviewBinding>(R.layout.web_fragment_webview) {


    private val viewModel: WebViewModel by viewModel()
    private lateinit var mWebView: WebView

    override fun initView() {

        arguments?.run {
            //从首页点进来的
            getParcelable<ArticleData>(Constant.Bundle.ArticleData)?.let {
                viewModel.ariticleId = it.id
                viewModel.showTitle = it.title
                viewModel.url = it.link
                viewModel.collectType = CollectType.Ariticle.type
            }
            //从首页banner点进来的
            getParcelable<BannerData>(Constant.Bundle.BannerData)?.let {
                viewModel.ariticleId = it.id
                viewModel.showTitle = it.title
                viewModel.url = it.url
                viewModel.collectType = CollectType.Ariticle.type
            }
            //从项目点进来的
            getParcelable<ProjectContent>(Constant.Bundle.ProjectContent)?.let {
                viewModel.ariticleId = it.id
                viewModel.showTitle = it.title
                viewModel.url = it.link
                viewModel.collectType = CollectType.Ariticle.type
            }

            //从收藏点进来的
            getParcelable<CollectResponse>(Constant.Bundle.CollectResponse)?.let {
                viewModel.ariticleId = it.id
                viewModel.showTitle = it.title
                viewModel.url = it.link
                viewModel.collectType = CollectType.Ariticle.type
            }


        }

        mDatabind.include.toolbar.initClose(viewModel.showTitle) {
            nav().navigateUp()
        }
    }

    override fun lazyLoadData() {
        initWebView(viewModel.url)
    }

    private fun initWebView(path: String?) {
        mWebView = WebView(requireContext())
        val layoutParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        mWebView.layoutParams = layoutParams
        mDatabind.fl.addView(mWebView)

        val webSetting = mWebView.settings
        webSetting.domStorageEnabled = true
        webSetting.useWideViewPort = true
        webSetting.loadWithOverviewMode = true
        webSetting.javaScriptEnabled = true

        mWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url == null) {
                    return false
                }
                try {
                    if (url.startsWith("weixin://") || url.startsWith("jianshu://")) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        return true
                    }
                } catch (e: Exception) {
                    return true
                }
                view?.loadUrl(url)
                return true
            }
        }

        mWebView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                mDatabind.progressBar.isVisible = newProgress != 100
                mDatabind.progressBar.progress = newProgress
            }
        }

        mWebView.loadUrl(path ?: "")
    }
}