package com.zhongya.havefun.ui.adapter.personal

import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhongya.havefun.R
import com.zhongya.havefun.app.MyApplication.Companion.appViewModel
import com.zhongya.havefun.bean.personal.MyFunctionBean

/**
 * GitHub/Gitee：zhongya666 2021/7/9 15:08
 *
 */
class UserFunctionAdapter(
    data: ArrayList<MyFunctionBean>,
) : BaseQuickAdapter<MyFunctionBean, BaseViewHolder>(R.layout.personal_item_user_function, data) {


    override fun convert(helper: BaseViewHolder, bean: MyFunctionBean) {
        helper.getView<TextView>(R.id.tv_name).text = bean.name
        helper.getView<ImageView>(R.id.iv_icon).setImageResource(bean.iconId)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            helper.getView<ImageView>(R.id.iv_icon).drawable?.setTint(appViewModel.appColor.value!!)
        }
    }
}