package com.zhongya666.library.base.fragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle

/**
 * GitHub/Gitee：zhongya666 2021/6/3 12:04
 *
 * ViewModelFragment基类，自动把ViewModel注入Fragment（现在被koin取代了）
 *
 * BaseFragment : Fragment
 */

/*
* 函数可以有泛型参数，通过在函数名前使用尖括号指定
* run函数是以闭包形式返回最后一行代码的值，
* apply函数的返回的是传入对象的本身
*
*
* */
abstract class BaseFragment(@LayoutRes val layoutId: Int) : Fragment(layoutId) {

    private val handler = Handler()

    //是否第一次加载
    private var isFirst: Boolean = true

    lateinit var mActivity: AppCompatActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isFirst = true
        initView()
        createObserver()
        initData()
    }

    override fun onResume() {
        super.onResume()
        onVisible()
    }


    /**
     * Fragment执行onViewCreated后触发
     */

    open fun initData() {}
    abstract fun initView()


    /**
     * 创建LiveData观察者 Fragment执行onViewCreated后触发
     */
    abstract fun createObserver()

    /**
     * 供子类初始化Databinding操作
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
     * 是否需要懒加载
     */
    private fun onVisible() {
//        Log.e("wzy","${this.javaClass.name} --  ${lifecycle.currentState}")
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {

            // 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿
            handler.postDelayed({
                lazyLoadData()
                isFirst = false
            }, lazyLoadTime())
        }
    }

    /**
     * 懒加载
     */
    abstract fun lazyLoadData()

    /**
     * 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿  bug
     * 这里传入你想要延迟的时间，延迟时间可以设置比转场动画时间长一点 单位： 毫秒
     * 不传默认 300毫秒
     * @return Long
     */
    open fun lazyLoadTime(): Long {
        return 300
    }
}