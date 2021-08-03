package com.zhongya.havefun.ui.fragment.personal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import cn.sharesdk.onekeyshare.OnekeyShare
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import com.zhongya.havefun.R
import com.zhongya.havefun.app.MyApplication.Companion.appViewModel
import com.zhongya.havefun.app.widget.dialog.DialogTheme
import com.zhongya.havefun.bean.home.BannerData
import com.zhongya.havefun.bean.personal.LoginResp
import com.zhongya.havefun.bean.personal.MyFunctionBean
import com.zhongya.havefun.bean.personal.ScoreResponse
import com.zhongya.havefun.databinding.PersonalFragmentBinding
import com.zhongya.havefun.ui.adapter.personal.PersonalitySettingAdapter
import com.zhongya.havefun.ui.adapter.personal.UserFunctionAdapter
import com.zhongya.havefun.viewmodel.personal.MyFragmentViewModel
import com.zhongya666.library.arouter.ARouterUtil
import com.zhongya.havefun.app.BaseAppFragment
import com.zhongya666.library.bean.ToExampleBean
import com.zhongya666.library.bus.LiveDataBus
import com.zhongya666.library.bus.bean.BaseBusBean
import com.zhongya666.library.constant.Constant
import com.zhongya666.library.constant.NaviTo
import com.zhongya666.library.ext.setUiTheme
import com.zhongya666.library.network.IStateObserver
import com.zhongya666.library.network.RetrofitManager
import com.zhongya666.library.ui.dialog.BasicDialog
import com.zhongya666.library.util.SPUtils
import com.zhongya666.library.util.ToastUtil
import com.zhongya666.library.view.nav
import com.zhongya666.library.view.navigateAction
import com.zhongya666.library.widget.dialog.DialogPicture
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyFragment :
    BaseAppFragment<PersonalFragmentBinding>(R.layout.personal_fragment) {

    companion object {
        @JvmStatic
        fun newInstance(): MyFragment {
            return MyFragment()
        }
    }

    private val viewModel: MyFragmentViewModel by viewModel()

    private var touxiangUrl: String? = null
    private var erweima: String? = null
    private var scoreResponse: ScoreResponse? = null

    private val userFunctionAdapter = UserFunctionAdapter(
        arrayListOf(
            MyFunctionBean(
                R.drawable.personal_my_collection_s,
                "我的收藏"
            ),
            MyFunctionBean(R.drawable.personal_my_jifen, "我的积分"),
            MyFunctionBean(
                R.drawable.personal_my_login_out,
                "退出登录"
            ),
        )
    )

    private lateinit var personalitySettingAdapter: PersonalitySettingAdapter

    private val otherAdapter = UserFunctionAdapter(
        arrayListOf(

            MyFunctionBean(R.drawable.personal_my_library, "库"),
            MyFunctionBean(R.drawable.personal_my_theme, "主题色"),
            MyFunctionBean(R.drawable.personal_my_weather, "天气"),
            MyFunctionBean(R.drawable.personal_my_share, "分享"),
            MyFunctionBean(R.drawable.personal_my_about_us, "关于"),

            )
    )

    override fun initView() {

        appViewModel.appColor.value?.let { setUiTheme(it, mDatabind.llHeadBg) }

        initSwich()
        mDatabind.click = ProxyClick()
        mDatabind.rvUser.layoutManager =
            StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
        mDatabind.rvUser.adapter = userFunctionAdapter

        mDatabind.rvOther.layoutManager =
            StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
        mDatabind.rvOther.adapter = otherAdapter

        userFunctionAdapter.setOnItemClickListener { _, _, position ->

            if (!isLogin()) {
                LiveDataBus.get().with(Constant.NAVIGATION_TO).postValue(NaviTo.Login)
                return@setOnItemClickListener
            }

            when (position) {
                0 -> {
                    LiveDataBus.get().with(Constant.NAVIGATION_TO).postValue(NaviTo.Collect)
                }
                1 -> {
                    nav().navigateAction(R.id.action_wanFragment_to_scoreFragment, Bundle().apply {
                        if (scoreResponse == null) {
                            ToastUtil.show("还没获取到个人信息,请稍等几秒")
                            return@setOnItemClickListener
                        }
                        putParcelable(Constant.Bundle.ScoreResponse, scoreResponse)
                    })
                }
                2 -> {
                    loginOut()
                }
            }
        }

        otherAdapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> {
                    ARouterUtil.navigate(Constant.Router.E_MainActivity, Bundle().apply {
                        putParcelable(Constant.Bundle.To_Example, ToExampleBean(1))
                    })
                }
                1 -> {
                    DialogTheme(requireContext()).show()
                }
                2 -> {
                    nav().navigateAction(R.id.action_wanFragment_to_weatherFragment)
                }
                3 -> {
                    showShare()
                }
                4 -> {
                    nav().navigateAction(R.id.action_global_webViewFragment, Bundle().apply {
                        putParcelable(
                            Constant.Bundle.BannerData,
                            BannerData(
                                "",
                                0,
                                "",
                                0,
                                0,
                                "项目仓库",
                                0,
                                "https://gitee.com/zhongya666/WanApk.git"
                            )
                        )
                    })
                }
            }
        }

        mDatabind.refreshLayout.setOnRefreshListener {
            viewModel.getMyScore()
        }
    }

    private fun loginOut() {
        var dialog = BasicDialog(requireContext())
        dialog.setContent("确定要退出登录吗?")
        dialog.show()
        dialog.setAction {
            mDatabind.name.text = "未登录"
            mDatabind.tvRank.text = "排名:0"
            mDatabind.tvScore.text = "积分:0"
            SPUtils.remove(Constant.Sp.USER_NAME)
            LiveDataBus.get().with(Constant.Bus.app_event).postValue(BaseBusBean.LOGIN_OUT)
            RetrofitManager.cookieJar.clear()
        }
    }

    override fun initData() {
        userName.let {
            if (it != null) {
                mDatabind.name.text = it
            } else {
                mDatabind.name.text = "请先登录"
            }
        }
    }

    inner class ProxyClick {

        fun touxiang() {
            DialogPicture(context!!).showPicture(touxiangUrl)
        }
    }

    override fun createObserver() {

        //登录成功后触发的事件
        LiveDataBus.get().with(
            Constant.Bus.login_response,
            LoginResp::class.java
        )
            .observe(this, Observer {
                mDatabind.run {
                    viewModel.getMyScore()
                }
            })

        appViewModel.appColor.observe(this, {
            setUiTheme(it, mDatabind.llHeadBg)
            userFunctionAdapter.notifyDataSetChanged()
            personalitySettingAdapter.notifyDataSetChanged()
            otherAdapter.notifyDataSetChanged()
        })

        viewModel.myScoreLiveData.observe(this, object : IStateObserver<ScoreResponse>(null) {
            override fun onReload(v: View?) {

            }

            override fun onDataChange(data: ScoreResponse?) {
                super.onDataChange(data)
                data.run {
                    scoreResponse = this
                    mDatabind.refreshLayout.finishRefresh()
                    mDatabind.name.text = this?.username ?: "未登录"
                    mDatabind.tvRank.text = "排名:${this?.rank ?: '0'}"
                    mDatabind.tvScore.text = "积分:${this?.coinCount ?: '0'}"
                }
            }
        })
    }

    override fun lazyLoadData() {
        viewModel.getMyScore()
    }

    private fun initSwich() {

        val is_xianhua = SPUtils.getBoolean(Constant.Sp.XIAN_HUA, false)
        val is_video_wall = SPUtils.getBoolean(Constant.Sp.VIDEO, false)

        personalitySettingAdapter = PersonalitySettingAdapter(
            arrayListOf(
                MyFunctionBean(R.drawable.personal_my_sonw, "下雪", is_xianhua),
                MyFunctionBean(R.drawable.personal_my_video, "视频墙", is_video_wall),
            )
        )

        mDatabind.rvSettingPersonality.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mDatabind.rvSettingPersonality.adapter = personalitySettingAdapter

        personalitySettingAdapter.setCheckedListener { position, isChecked ->
            when (position) {
                0 -> {
                    SPUtils.put(Constant.Sp.XIAN_HUA, isChecked)
                    LiveDataBus.get().with(Constant.Bus.screen_snow).postValue(isChecked)
                }
                1 -> {
                    SPUtils.put(Constant.Sp.VIDEO, isChecked)
                    initVideo(isChecked)
                }
            }
        }
    }

    private fun initVideo(is_video_wall: Boolean) {

        if (is_video_wall) {
            GSYVideoManager.instance().isNeedMute = true
            GSYVideoType.setShowType(4) //全屏裁减显示，为了显示正常 CoverImageView 建议使用FrameLayout作为父布局
            mDatabind.video.run {
                isLooping = true
                isSoundEffectsEnabled = false
                setUp("http://118.89.194.28:8080/video/1.mp4", true, "")
                startPlayLogic()
            }

            mDatabind.run {
                llHeadBg.alpha = 0.5f
                rvUser.alpha = 0.5f
                rvOther.alpha = 0.5f
                rvSettingPersonality.alpha = 0.5f
                clSettingPersonal.alpha = 0.5f
                clOther.alpha = 0.5f
            }
        } else {
            mDatabind.run {
                video.release()
                llHeadBg.alpha = 1f
                rvUser.alpha = 1f
                rvOther.alpha = 1f
                rvSettingPersonality.alpha = 1f
                clSettingPersonal.alpha = 1f
                clOther.alpha = 1f
            }
        }
    }


    private fun startQQ() {
        val qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=997934916&version=2"
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)))
    }

    fun dialPhoneNumber() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.setData(Uri.parse("tel:" + "16602196601"))
        if (intent.resolveActivity(context?.packageManager!!) != null) {
            startActivity(intent)
        }
    }

    private fun showShare() {
        val oks = OnekeyShare()
        //关闭sso授权
        oks.disableSSOWhenAuthorize()

        // title标题，微信、QQ和QQ空间等平台使用
//        oks.setTitle(getString(R.string.share));
        oks.setTitle("这是我的个人app")
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn")
        // text是分享文本，所有平台都需要这个字段
        oks.setText("里面有我的照片哦")
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl("")
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn")
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("欢迎光临")
        // 启动分享GUI
        oks.show(activity)
    }
}