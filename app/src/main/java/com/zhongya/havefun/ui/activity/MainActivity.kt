package com.zhongya.havefun.ui.activity

import android.view.KeyEvent
import android.view.View
import androidx.navigation.Navigation
import com.alibaba.android.arouter.facade.annotation.Route
import com.baidu.mobstat.StatService
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.zhongya.havefun.R
import com.zhongya.havefun.app.BaseAppActivity
import com.zhongya.havefun.app.MyApplication.Companion.appViewModel
import com.zhongya.havefun.app.util.StatusBarUtil
import com.zhongya.havefun.app.widget.xiaxue.FallObject
import com.zhongya.havefun.databinding.ActivityMainBinding
import com.zhongya.havefun.viewmodel.MainViewModel
import com.zhongya666.library.bus.LiveDataBus
import com.zhongya666.library.constant.Constant
import com.zhongya666.library.ext.setUiTheme
import com.zhongya666.library.util.ToastUtil
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


@Route(path = Constant.Router.A_MAIN)
class MainActivity : BaseAppActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModel()
    private var firstTime: Long = 0

    override fun initView() {
        StatService.start(this) //统计
        initStatusBar()

        appViewModel.appColor.value?.let { setUiTheme(it, mDatabind.statusBar) }

//        var mainFragment = ARouterUtil.navigate(AppConstant.Router.Main.F_MAIN) as BaseFragment<*, *>

    }

    override fun createObserver() {
        appViewModel.appColor.observe(this, {
            setUiTheme(it, mDatabind.statusBar)
        })

        LiveDataBus.get()
            .with(Constant.Bus.screen_snow, Boolean::class.java)
            .observe(this, {

                when (it) {
                    true -> {
                        xianHuaPlay(true)
                    }
                    false -> {
                        xianHuaPlay(false)
                    }
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        StatService.onEventEnd(this, "en", "en")
        GSYVideoManager.releaseAllVideos();//慎用，在跳转的时候可能会导致下个界面的视频不能播放
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {

            val nav = Navigation.findNavController(this@MainActivity, R.id.main_fragment)
            if (nav.currentDestination != null && nav.currentDestination!!.id != R.id.wanFragment) {
                //如果当前界面不是主页，那么直接调用返回即可
                nav.navigateUp()
                return true
            } else {
                if (System.currentTimeMillis() - firstTime > 2000) {
                    ToastUtil.show("再按一次退出程序")
                    firstTime = System.currentTimeMillis()
                    return true
                } else {
                    finish()
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }


    private fun xianHuaPlay(b: Boolean) {
        if (b) {
            mDatabind.fallingView.visibility = View.VISIBLE
            //初始化一个雪花样式的fallObject
            val builder = FallObject.Builder(resources.getDrawable(huaBan))
            val fallObject = builder
                .setSpeed(5, false)
                .setSize(60, 60, false)
                .setWind(5, true, true)
                .build()
            mDatabind.fallingView.addFallObject(fallObject, 50) //添加50个下落物体对象
            mDatabind.fallingView.alpha = 0.4f
        } else {
            mDatabind.fallingView.visibility = View.GONE
        }
    }

    //随机选一个
    private val huaBan: Int
        get() {
            val list: MutableList<Int> = ArrayList()
            list.add(R.drawable.huaban_a)
            list.add(R.drawable.huaban_b)
            list.add(R.drawable.huaban_c)
            list.add(R.drawable.huaban_d)
            list.add(R.drawable.huaban_e)
            val random = Random()
            val i = random.nextInt(list.size)
            return list[i]
        }

    private fun initStatusBar() {
        var statusBarHeight = StatusBarUtil.getStatusBarHeight(this)
        mDatabind.statusBar.layoutParams.height = statusBarHeight
    }

}