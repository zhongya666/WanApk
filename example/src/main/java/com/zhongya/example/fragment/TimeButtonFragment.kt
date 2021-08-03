package com.zhongya.example.fragment

import androidx.core.content.ContextCompat
import com.zhongya.example.R
import com.zhongya.example.databinding.ExampleFragmentTimebuttonBinding
import com.zhongya666.library.base.fragment.BaseDbFragment

class TimeButtonFragment : BaseDbFragment<ExampleFragmentTimebuttonBinding>(R.layout.example_fragment_timebutton) {

    override fun initView() {

        mDatabind.timeButton.run {

            /**
             * 这种方式可以不用在xml中写shape代码，更加方便
             * normalBackgroundColor 正常时的背景色
             * runBackgroundColor    执行时的背景色
             * radius 圆角大小
             */
            setEasyAttr(
                ContextCompat.getColor(context,R.color.yellow_f2),
                ContextCompat.getColor(context,R.color.color_999),
                10f
            )

            /**
             * 务必使用这个点击事件
             */
            setTimeButtonClickListener {

            }

        }
    }

    override fun createObserver() {

    }

    override fun lazyLoadData() {

    }
}