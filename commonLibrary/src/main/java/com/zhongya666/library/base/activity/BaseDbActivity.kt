package com.zhongya666.library.base.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * GitHub/Gitee：zhongya666 2021/6/2 16:19
 *
 * 你项目中的Activity基类，在这里实现显示弹窗，吐司，还有加入自己的需求操作
 * 如果不想用Databind，请继承BaseActivity例如
 *
 * BaseDbActivity : BaseActivity : AppCompatActivity
 */
abstract class BaseDbActivity<DB : ViewDataBinding>(
    layoutId: Int
) : BaseActivity(layoutId) {

    lateinit var mDatabind: DB


    override fun onCreate(savedInstanceState: Bundle?) {
        useDataBinding(true)
        super.onCreate(savedInstanceState)
    }

    override fun initDataBind() {
        mDatabind = DataBindingUtil.setContentView(this, layoutId)
        mDatabind.lifecycleOwner = this
    }

}