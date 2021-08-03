package com.zhongya.havefun.ui.adapter.personal

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import coil.load
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhongya.havefun.R
import com.zhongya.havefun.app.widget.CollectView
import com.zhongya.havefun.bean.personal.CollectResponse
import com.zhongya.havefun.app.MyApplication
import com.zhongya666.library.ext.setAdapterAnimation
import com.zhongya666.library.ext.shapeBackgroundColor
import com.zhongya666.library.util.SettingUtil
import com.zhongya666.library.util.toHtml

/**
 * GitHub/Gitee：zhongya666 2021/7/23 09:54
 *
 */
class CollectAdapter(data: ArrayList<CollectResponse>) : BaseDelegateMultiAdapter<CollectResponse, BaseViewHolder>(data){

    //文章类型
    private val Ariticle = 1
    private val Project = 2

    private var collectClickListener : ((Int, CollectResponse) -> Unit)? = null

    fun setCollectClick( listener :(Int, CollectResponse) -> Unit){
        collectClickListener=listener
    }

    init {

        setAdapterAnimation(SettingUtil.getListMode())

        // 第一步，设置代理
        setMultiTypeDelegate(object : BaseMultiTypeDelegate<CollectResponse>() {
            override fun getItemType(data: List<CollectResponse>, position: Int): Int {
                //根据是否有图片 判断为文章还是项目
                return if (TextUtils.isEmpty(data[position].envelopePic)) Ariticle else Project
            }
        })
        // 第二步，绑定 item 类型
        getMultiTypeDelegate()?.let {
            it.addItemType(Ariticle, R.layout.home_item_article)
            it.addItemType(Project, R.layout.project_item_project_list)
        }
    }

    override fun convert(holder: BaseViewHolder, item: CollectResponse) {
        when (holder.itemViewType) {
            Ariticle -> {
                //文章布局的赋值
                item.run {
                    holder.setText(R.id.tv_author, if (author.isEmpty()) "匿名用户" else author)
                    holder.setText(R.id.tv_title, title)
                    holder.setText(R.id.tv_type, chapterName.toHtml())
                    holder.setText(R.id.tv_time, niceDate)
                    holder.getView<View>(R.id.tv_type).shapeBackgroundColor(MyApplication.appViewModel.appColor.value!!)
                }
            }
            Project -> {
                //项目布局的赋值
                item.run {
                    holder.setText(R.id.tv_title,title)
                    holder.setText(R.id.tv_content,desc)
                    holder.setText(R.id.tv_time,niceDate)
                    holder.setText(R.id.tv_type,chapterName)
                    holder.getView<ImageView>(R.id.iv_pic).load(envelopePic){
                        placeholder(R.drawable.default_project_img)
                    }

                    if (author.isEmpty()) {
                        holder.setText(R.id.tv_name, "匿名用户")
                    } else {
                        holder.setText(R.id.tv_name, author)
                    }
                }
            }
        }

        holder.getView<CollectView>(R.id.iv_collect).setCollect(item.collect)

        holder.getView<CollectView>(R.id.iv_collect).setCollectAction {
            collectClickListener?.invoke(holder.absoluteAdapterPosition,item)
        }
    }
}