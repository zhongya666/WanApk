package com.zhongya.havefun.app.widget.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhongya.havefun.R
import com.zhongya.havefun.app.MyApplication.Companion.appViewModel
import com.zhongya666.library.util.SettingUtil
import com.zhongya666.library.widget.dialog.BaseDialog


/**
 * 作者：zhongya666 2019/8/9 9:32
 * 邮箱：997934916@qq.com
 */
class DialogTheme(context: Context) : BaseDialog(context, R.style.dialog_picture) {
    private val rv: RecyclerView

    init {
        setScreenWidth(8)
        mContext = context
        val v = LayoutInflater.from(context).inflate(R.layout.personal_dialog_theme, null)
        rv = v.findViewById(R.id.rv)


        setContentView(v)

        val adapter = Adapter()
        rv.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        rv.adapter = adapter
        val list = arrayListOf(
            Bean("黄瓜绿", Color.parseColor("#008000")),
            Bean("帽子绿", Color.parseColor("#00AA00")),
            Bean("葡萄紫", Color.parseColor("#9F009F")),
            Bean("骚红", Color.parseColor("#FF4646")),
            Bean("香蕉黄", Color.parseColor("#D2D200")),
        )
        adapter.setNewData(list)

        show()

        adapter.setOnItemClickListener { _, _, position ->
            SettingUtil.setColor(list[position].color)
            //通知其他界面立马修改配置
            appViewModel.appColor.value = list[position].color
            dismiss()
        }

    }

    internal class Adapter :
        BaseQuickAdapter<Bean, BaseViewHolder>(R.layout.personal_item_dialog_theme) {

        override fun convert(helper: BaseViewHolder, bean: Bean) {
            val drawable = GradientDrawable()
//            drawable.cornerRadius = 5f
            drawable.shape = GradientDrawable.OVAL
//            drawable.setStroke(1, Color.parseColor("#cccccc"))
            drawable.setColor(bean.color)
            helper.getView<TextView>(R.id.tv_color).background = drawable

            helper.getView<TextView>(R.id.tv_name).text = bean.name
        }
    }

    internal data class Bean(var name: String, var color: Int)

}