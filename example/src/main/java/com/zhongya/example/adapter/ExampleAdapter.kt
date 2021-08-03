package com.zhongya.example.adapter

import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhongya.example.R
import com.zhongya.example.bean.ExampleBean
import com.zhongya666.library.ext.setAdapterAnimation
import com.zhongya666.library.util.SettingUtil

/**
 * GitHub/Gitee：zhongya666 2021/7/9 15:08
 *
 */
class ExampleAdapter(
    data: ArrayList<ExampleBean>
) : BaseQuickAdapter<ExampleBean, BaseViewHolder>(R.layout.example_item_example, data) {

    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }

    override fun convert(helper: BaseViewHolder, bean: ExampleBean) {
        helper.getView<TextView>(R.id.tv_name).text = bean.name
        helper.getView<TextView>(R.id.tv_content).text = bean.content
        helper.getView<ImageView>(R.id.iv_icon).setImageResource(bean.iconId)

    }
}