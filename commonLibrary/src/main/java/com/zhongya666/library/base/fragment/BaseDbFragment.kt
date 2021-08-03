package com.zhongya666.library.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * GitHub/Gitee：zhongya666 2021/6/3 11:47
 *
 * BaseDbFragment : BaseFragment : Fragment
 */
abstract class BaseDbFragment<DB : ViewDataBinding>(
    layoutId: Int
) : BaseFragment(layoutId) {

    lateinit var mDatabind: DB


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDatabind = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mDatabind.lifecycleOwner = this
        return mDatabind.root
    }
}