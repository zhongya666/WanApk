package com.zhongya.example.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhongya.example.R
import com.zhongya666.library.ext.setAdapterAnimation
import com.zhongya666.library.util.SettingUtil

/**
 * GitHub/Gitee：zhongya666 2021/7/9 15:08
 *
 */
class BaseTextAdapter(
    data: ArrayList<String>
) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.example_item_text, data) {

    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }

    override fun convert(helper: BaseViewHolder, s: String) {
        helper.getView<TextView>(R.id.tv_content).text = s
    }
}