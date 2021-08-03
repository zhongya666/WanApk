package com.zhongya.havefun.ui.adapter.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.zhongya.havefun.R
import com.zhongya.havefun.app.widget.CollectView
import com.zhongya666.library.base.adapter.BasePagingAdapter
import com.zhongya666.project.bean.ProjectContent

/**
 * GitHub/Gitee：zhongya666 2021/6/30 14:34
 *
 */
class ProjectListAdapter : BasePagingAdapter<ProjectContent>(differCallback) {

    companion object {
        val differCallback = object :
            DiffUtil.ItemCallback<ProjectContent>() {

            override fun areContentsTheSame(oldItem: ProjectContent, newItem: ProjectContent): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: ProjectContent, newItem: ProjectContent): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    private var collectClickListener : ((Int,ProjectContent) -> Unit)? = null

    fun setCollectClick( listener :(Int,ProjectContent) -> Unit){
        collectClickListener=listener
    }

    override fun getItemView(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.project_item_project_list, parent, false)
    }

    override fun bindData(helper: ItemHelper, position: Int, data: ProjectContent) {
        helper.setText(R.id.tv_title, data.title)
        helper.setText(R.id.tv_content, data.desc)
        helper.setText(R.id.tv_time, data.niceDate)
        helper.setText(R.id.tv_type, data.chapterName)
        helper.bindImgGlide(R.id.iv_pic, data.envelopePic)

        if (data.author.isEmpty()) {
            helper.setText(R.id.tv_name, data.shareUser)
        } else {
            helper.setText(R.id.tv_name, data.author)
        }

        (helper.findViewById(R.id.iv_collect) as CollectView).setCollect(data.collect)

        (helper.findViewById(R.id.iv_collect) as CollectView).setCollectAction {
            collectClickListener?.invoke(position, data)
        }
    }
}