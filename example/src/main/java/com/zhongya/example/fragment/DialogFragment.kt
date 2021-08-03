package com.zhongya.example.fragment

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zhongya.example.R
import com.zhongya.example.adapter.BaseTextAdapter
import com.zhongya.example.databinding.ExampleFragmentMainBinding
import com.zhongya666.library.base.fragment.BaseDbFragment
import com.zhongya666.library.ext.initClose
import com.zhongya666.library.ui.dialog.BasicDialog
import com.zhongya666.library.util.ToastUtil
import com.zhongya666.library.view.nav


/**
 * GitHub/Gitee：zhongya666 2021/7/28 13:49
 *
 */
class DialogFragment : BaseDbFragment<ExampleFragmentMainBinding>(R.layout.example_fragment_main) {

    override fun initView() {

        mDatabind.include.toolbar.initClose("弹框演示") {
            nav().navigateUp()
        }

        mDatabind.includeRv.refreshLayout.setEnableRefresh(false)
        mDatabind.includeRv.refreshLayout.setEnableLoadMore(false)

        var adapter = BaseTextAdapter(
            arrayListOf(
                "最简单的使用",
                "修改弹框尺寸",
                "修改确定按钮属性",
                "隐藏取消按钮",
                "修改弹框背景和圆角",
                "可传入SpannableString",
            )
        )


        mDatabind.includeRv.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mDatabind.includeRv.recyclerView.adapter = adapter

        adapter.setOnItemClickListener { _, _, position ->

            when(position){
                0 -> {
                    var dialog = BasicDialog(requireContext())
                    dialog.setContent("我真的很帅")
                    dialog.show()
                    dialog.setAction {
                        ToastUtil.show("是的,你做出了正确的选择")
                    }
                }
                1 -> {
                    /**
                     * 修改弹框宽度 1 - 10 (int)
                     * 高度是自适应的不支持修改
                     */
                    var dialog = BasicDialog(requireContext(),6)
                    dialog.setContent("我真的很帅")
                    dialog.show()
                    dialog.setActionWhich {
                        if (it ==0){
                            ToastUtil.show("嗯,我知道你手误了")
                        }else{
                            ToastUtil.show("是的,你做出了正确的选择")
                        }
                    }
                }
                2 -> {
                    /**
                     * 一般修改确定按钮背景色比较多,特写此方法
                     */
                    var dialog = BasicDialog(requireContext())
                    dialog.setContent("我真的很帅")
                    dialog.setAgreeBackgroundColor(ContextCompat.getColor(requireContext(),R.color.blue))
                    dialog.setAgreeTextColor(ContextCompat.getColor(requireContext(),R.color.white))
                    dialog.show()
                }
                3 -> {
                    var dialog = BasicDialog(requireContext())
                    dialog.setContent("我真的很帅")
                    dialog.goneCancel() //如果你修改了确定按钮的背景色,这句话你需要在它之前调用
                    dialog.setAgreeBackgroundColor(ContextCompat.getColor(requireContext(),R.color.blue))
                    dialog.setAgreeTextColor(ContextCompat.getColor(requireContext(),R.color.white))
                    dialog.show()
                }
                4 -> {
                    var dialog = BasicDialog(requireContext())
                    dialog.setContent("我真的很帅")
                    dialog.setBackgroundColorAndRadius(ContextCompat.getColor(requireContext(),R.color.blue),30f)
                    dialog.show()
                }
                5 -> {
                    val spannableString = SpannableString("我真的很帅我真的很帅我真的很帅我真的很帅我真的很帅我真的很帅我真的很帅\n《我已阅读以上内容》")
//        val backgroundColorSpan = BackgroundColorSpan(Color.GREEN)
                    val foregroundColorSpan = ForegroundColorSpan(ContextCompat.getColor(requireContext(),R.color.blue))
                    spannableString.setSpan(foregroundColorSpan, spannableString.length-10, spannableString.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    val clickableSpan: ClickableSpan = object : ClickableSpan() {

                        override fun onClick(widget: View) {
                            ToastUtil.show("我已阅读以上内容")
                        }

                        override fun updateDrawState(ds: TextPaint) {
                            ds.isUnderlineText = false
                        }
                    }
                    spannableString.setSpan(clickableSpan, spannableString.length-10, spannableString.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

                    var dialog = BasicDialog(requireContext())
                    dialog.setSpanContent(spannableString)
                    dialog.show()
                }
            }

        }
    }

    override fun createObserver() {

    }

    override fun lazyLoadData() {

    }
}