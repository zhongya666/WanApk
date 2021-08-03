package com.zhongya.havefun.ui.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.zhongya.havefun.R
import com.zhongya.havefun.bean.home.ArticleData
import com.zhongya.havefun.bean.home.BannerData
import com.zhongya.havefun.databinding.HomeItemArticleBinding
import com.zhongya.havefun.databinding.HomeItemArticleHeaderBinding
import com.zhongya.havefun.app.MyApplication.Companion.appViewModel
import com.zhongya666.library.ext.shapeBackgroundColor

/**
 * GitHub/Gitee：zhongya666 2021/7/7 13:12
 *
 */
class ArticleMultiAdapter : PagingDataAdapter<ArticleData, RecyclerView.ViewHolder>(
    differCallback
) {

    private var collectClickListener : ((Int,ArticleData) -> Unit)? = null

    fun setCollectClick( listener :(Int,ArticleData) -> Unit){
        collectClickListener=listener
    }

    companion object {
        const val TYPE_BANNER = 0
        const val TYPE_ARTICLE = 1

        val differCallback = object : DiffUtil.ItemCallback<ArticleData>() {
            override fun areItemsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
                return oldItem == newItem
            }
        }
    }

    var listener: ((ArticleData) -> Unit)? = null
    var listenerBanner: ((BannerData) -> Unit)? = null

    //banner数据源
    private var bannerList: List<BannerData> = ArrayList()

    fun addBannerList(list: List<BannerData>) {
        bannerList = list
        notifyItemChanged(0)
    }

    fun setHeadClickListener(listener : (BannerData) -> Unit){
        this.listenerBanner = listener
    }
    fun setOnItemClickListener(listener : (ArticleData) -> Unit){
        this.listener = listener
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_BANNER) {
            (holder as BannerVH).bindData(bannerList)
        } else {
            getItem(position - 1)?.let { item ->
                (holder as ArticleVH).bindData(item)
                holder.binding.tvType.shapeBackgroundColor(appViewModel.appColor.value!!)
                holder.itemView.setOnClickListener { listener?.invoke(item) }
                holder.binding.ivCollect.setCollect(item.collect)
                holder.binding.ivCollect.setCollectAction {
                    collectClickListener?.invoke(position,item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_BANNER){
            return BannerVH(
                HomeItemArticleHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }else{
            val articleVH = ArticleVH(
                HomeItemArticleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            return articleVH
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0){
            return TYPE_BANNER
        }
        return TYPE_ARTICLE
    }

    /*override fun getItemCount(): Int {
        return 1
    }*/

    inner class BannerVH(val binding: HomeItemArticleHeaderBinding)
        : RecyclerView.ViewHolder(binding.root){


            fun bindData(list: List<BannerData>){
                binding.banner.setAdapter(object : BannerImageAdapter<BannerData>(list){
                    override fun onBindView(
                        holder: BannerImageHolder?,
                        data: BannerData?,
                        position: Int,
                        size: Int
                    ) {
                        holder!!.imageView.load(data?.imagePath){
                            placeholder(R.drawable.default_project_img)
                        }
                    }
                })

                binding.banner.setOnBannerListener{data,_ ->
                    listenerBanner?.invoke(data as BannerData)
                }
            }
    }

    internal class ArticleVH(val binding: HomeItemArticleBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bindData(data: ArticleData){
            setText(binding.tvTitle, data.title)
            setText(binding.tvType, data.superChapterName)
            setText(binding.tvTime, data.niceDate)

            if (data.author.isEmpty()) {
                setText(binding.tvAuthor, data.shareUser)
            } else {
                setText(binding.tvAuthor, data.author)
            }
        }

        private fun setText(view: TextView, text: String) {
            view.text = text
        }
    }



}