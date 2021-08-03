package com.zhongya.havefun.ui.adapter.personal

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhongya.havefun.R
import com.zhongya.havefun.bean.personal.ScoreHistoryResponse
import com.zhongya.havefun.app.MyApplication.Companion.appViewModel
import com.zhongya666.library.ext.setAdapterAnimation
import com.zhongya666.library.util.SettingUtil
import com.zhongya666.library.util.TimeUtils

/**
 * GitHub/Gitee：zhongya666 2021/7/22 14:58
 * 积分获取历史
 */
class ScoreHistoryAdapter(data: ArrayList<ScoreHistoryResponse>) :
    BaseQuickAdapter<ScoreHistoryResponse, BaseViewHolder>(
        R.layout.personal_item_score_history, data
    ) {
    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }

    override fun convert(holder: BaseViewHolder, item: ScoreHistoryResponse) {
        //赋值
        item.run {
            val descStr =
                if (desc.contains("积分")) desc.subSequence(desc.indexOf("积分"), desc.length) else ""
            holder.setText(R.id.tv_title, reason + descStr)
            holder.setText(
                R.id.tv_time,
                TimeUtils.getTimeString(date)
            )
            holder.setText(R.id.tv_count, "+$coinCount")
            holder.getView<TextView>(R.id.tv_count).setTextColor(appViewModel.appColor.value!!)
        }
    }
}


