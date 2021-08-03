package com.zhongya.havefun.ui.adapter.personal

import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhongya.havefun.R
import com.zhongya.havefun.bean.personal.ScoreResponse
import com.zhongya666.library.ext.setAdapterAnimation
import com.zhongya666.library.util.SettingUtil

/**
 * GitHub/Gitee　: zhongya666
 * 时间　: 2021/7/20 17:12
 * 描述　: 积分排行
 */
class ScoreAdapter(data: ArrayList<ScoreResponse>) : BaseQuickAdapter<ScoreResponse, BaseViewHolder>(
    R.layout.personal_item_score, data) {

    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }
    override fun convert(holder: BaseViewHolder, item: ScoreResponse) {
        //赋值
        item.run {
            if(0==holder.bindingAdapterPosition){
                holder.setTextColor(R.id.item_integral_rank, SettingUtil.getColor(context))
                holder.setTextColor(R.id.item_integral_name,SettingUtil.getColor(context))
                holder.setTextColor(R.id.item_integral_count,SettingUtil.getColor(context))
            }else{
                holder.setTextColor(R.id.item_integral_rank,ContextCompat.getColor(context,R.color.color_333))
                holder.setTextColor(R.id.item_integral_name,ContextCompat.getColor(context,R.color.color_333))
                holder.setTextColor(R.id.item_integral_count,ContextCompat.getColor(context,R.color.color_333))
            }
            holder.setText(R.id.item_integral_rank, "$rank")
            holder.setText(R.id.item_integral_name, username)
            holder.setText(R.id.item_integral_count, coinCount.toString())
        }
    }
}


