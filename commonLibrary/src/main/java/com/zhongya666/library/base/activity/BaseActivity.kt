package com.zhongya666.library.base.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar

/**
 * GitHub/Gitee：zhongya666 2021/6/2 10:29
 * 基本上不用动
 * 当包含fragment时不要这么写AppCompatActivity(layoutId)，
 * 会跟这两个方法冲突（setContentView(layoutId)、initDataBind()
 *
 * BaseActivity : AppCompatActivity
 */
abstract class BaseActivity(@LayoutRes val layoutId: Int) : AppCompatActivity() {

    /**
     * 是否需要使用DataBinding 供子类BaseActivity修改，用户请慎动
     */
    private var isUserDb = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (!isUserDb) {
            setContentView(layoutId)
        } else {
            initDataBind()
        }

        statusBar()
        createObserver()
        initView()
    }

    private fun statusBar() {
        ImmersionBar.with(this)
            .statusBarAlpha(0.0f) //状态栏透明度，不写默认0.0f
            .statusBarDarkFont(false)
            //               .fullScreen(true)//有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
            //                .hideBar(BarHide.FLAG_HIDE_BAR)
            .init()

    }

    abstract fun initView()

    /**
     * 供子类BaseVmDbActivity 初始化Databinding操作
     */
    open fun initDataBind() {}

    /**
     * 创建viewModel
     * 换成了koin 这个就不用了
     */
    /*private fun createViewModel(): VM {

        return ViewModelProvider(this).get(getVmClazz(this))
    }*/

    /**
     * 创建LiveData数据观察者
     */
    abstract fun createObserver()

    fun useDataBinding(isUserDb: Boolean) {
        this.isUserDb = isUserDb
    }


}