package com.zhongya666.library.base.adapter

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.zhongya666.library.R

/**
 * GitHub/Gitee　: zhongya666
 * 时间　: 2021/7/19 16:54
 * 描述　:Paging3Adapter的公共类，主要减少adapter的冗余代码。
 */
abstract class BasePagingAdapter<T : Any>(var diffCallback: DiffUtil.ItemCallback<T>) :
    PagingDataAdapter<T, RecyclerView.ViewHolder>(diffCallback)  {

    var listener: ((Int, T) -> Unit)? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) ?: return

        holder.itemView.setOnClickListener {
            listener?.invoke(holder.layoutPosition,item)
        }

        (holder as BasePagingAdapter<*>.BaseViewHolder).bindNormalData(item,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BaseViewHolder(getItemView(parent, viewType))
    }

    inner class BaseViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){

        private val helper: ItemHelper = ItemHelper(this)

        fun bindNormalData(item: Any?, position: Int){
            bindData(helper,position,item as T)
        }

    }

    /**
     * 获取itemView
     */

    protected abstract fun getItemView(parent: ViewGroup, viewType: Int): View

    /**
     * item返回点击事件,并返回数据
     */
    fun setOnItemClickListener(listener : (position : Int,data : T) -> Unit) {
        this.listener = listener
    }

    /**
     * 子类绑定数据
     */
    protected abstract fun bindData(helper: ItemHelper, position: Int,data: T)

    /**
     * ItemView的辅助类
     */
    class ItemHelper(holder: BasePagingAdapter<*>.BaseViewHolder) {
        private val itemView = holder.itemView
        private val viewCache = SparseArray<View>()

        fun findViewById(viewId: Int): View {
            var view = viewCache.get(viewId)
            if (view == null) {
                view = itemView.findViewById(viewId)
                if (view == null) {
                    throw NullPointerException("$viewId can not find this item！")
                }
                viewCache.put(viewId, view)
            }
            return view
        }

        /**
         * TextView设置内容
         */
        fun setText(viewId: Int, text: CharSequence?): ItemHelper {
            (findViewById(viewId) as TextView).text = text
            return this
        }

        /**
         * Coil加载图片
         */
        fun bindImgGlide(viewId: Int, url: String) {
            val imageView: ImageView = findViewById(viewId) as ImageView
            imageView.load(url) {
                placeholder(R.drawable.default_project_img)
            }

        }
    }

}