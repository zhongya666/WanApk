package com.zhongya.example.activity

import android.view.KeyEvent
import androidx.navigation.Navigation
import com.alibaba.android.arouter.facade.annotation.Route
import com.zhongya.example.R
import com.zhongya.example.databinding.ExampleActivityMainBinding
import com.zhongya666.library.base.activity.BaseDbActivity
import com.zhongya666.library.bean.ToExampleBean
import com.zhongya666.library.constant.Constant
import com.zhongya666.library.util.ToastUtil

@Route(path = Constant.Router.E_MainActivity)
class MainActivity : BaseDbActivity<ExampleActivityMainBinding>(R.layout.example_activity_main) {

    private var firstTime: Long = 0

    var toExampleBean: ToExampleBean? = null

    override fun initView() {
        toExampleBean = intent.extras?.get(Constant.Bundle.To_Example) as ToExampleBean?
    }

    override fun createObserver() {
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {

            if (toExampleBean == null) { //如果为null 说明是单模块运行的,正常处理返回事件
                val nav = Navigation.findNavController(this@MainActivity, R.id.main_fragment)
                if (nav.currentDestination != null && nav.currentDestination!!.id != R.id.mainFragment) {
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
            } else {
                finish()
            }


        }
        return super.onKeyDown(keyCode, event)
    }
}