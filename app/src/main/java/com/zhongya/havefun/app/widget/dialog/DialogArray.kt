package com.zhongya.havefun.app.widget.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhongya.havefun.R
import com.zhongya666.library.util.ScreenUtils.getScreenH
import com.zhongya666.library.util.ScreenUtils.getScreenW
import com.zhongya666.library.widget.dialog.BaseDialog


/**
 * 作者：zhongya666 2019/8/9 9:32
 * 邮箱：997934916@qq.com
 *
 * 封装了展示数组的dilog
 */
class DialogArray(
        context: Context, data: ArrayList<String>
) : BaseDialog(context, R.style.dialog_picture) {

    private var listener: (Int) -> Unit = {}

    init {
        mContext = context
        val v = LayoutInflater.from(context).inflate(R.layout.dialog_array, null)
        setContentView(v)
        var rv = v.findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = LinearLayoutManager(mContext)
        var myAdapter = MyAdapter(R.layout.item_dialog_array, data)
        rv.adapter = myAdapter
        myAdapter.setOnItemClickListener { _, _, position ->
            dismiss()
            listener(position)
        }
        layoutParams.width = getScreenW(mContext) * 6 / 10
        layoutParams.height = getScreenH(mContext) * 6 / 10

    }

    fun setItemClick(listener: (Int) -> Unit) {
        this.listener = listener
    }

    private inner class MyAdapter(
            layoutResId: Int,
            data: ArrayList<String>
    ) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {

        override fun convert(helper: BaseViewHolder, item: String) {
            helper.getView<TextView>(R.id.tv).text = item
        }
    }


}