package com.zhongya.havefun.ui.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.zhongya.havefun.R
import com.zhongya.havefun.app.widget.CollectView
import com.zhongya.havefun.bean.home.ArticleData
import com.zhongya.havefun.app.MyApplication.Companion.appViewModel
import com.zhongya666.library.base.adapter.BasePagingAdapter
import com.zhongya666.library.ext.shapeBackgroundColor


class SquarePagingAdapter : BasePagingAdapter<ArticleData>(
    differCallback
) {

    private var collectClickListener : ((Int,ArticleData) -> Unit)? = null

    fun setCollectClick( listener :(Int,ArticleData) -> Unit){
        collectClickListener=listener
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<ArticleData>() {
            override fun areItemsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun getItemView(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(parent.context).inflate(R.layout.home_item_article,parent,false)
    }

    override fun bindData(helper: ItemHelper,position : Int, data: ArticleData) {
        helper.setText(R.id.tv_title,data.title)
        helper.setText(R.id.tv_type,data.superChapterName)
        helper.setText(R.id.tv_time,data.niceDate)

        if (data.author.isEmpty()) {
            helper.setText(R.id.tv_author, data.shareUser)
        } else {
            helper.setText(R.id.tv_author, data.author)
        }

        helper.findViewById(R.id.tv_type).shapeBackgroundColor(appViewModel.appColor.value!!)

        (helper.findViewById(R.id.iv_collect) as CollectView).setCollect(data.collect)

        (helper.findViewById(R.id.iv_collect) as CollectView).setCollectAction {
            collectClickListener?.invoke(position,data)
        }
    }
}

