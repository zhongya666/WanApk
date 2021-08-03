package com.zhongya.havefun.app.widget

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageView
import com.zhongya.havefun.R
import com.zhongya.havefun.app.MyApplication.Companion.appViewModel
import com.zhongya666.library.util.ToastUtil

/**
 * GitHub/Gitee：zhongya666 2021/7/23 09:58
 *
 */
@SuppressLint("AppCompatCustomView")
class CollectView(context: Context, attrs: AttributeSet?) : ImageView(context, attrs) {

    private var isCollected = false
    private var canVisitInternet = true
    private var collectAction : (() -> Unit)? = null

    init {

        setImageResource(R.drawable.personal_my_collection)
        drawable.mutate()

        setOnClickListener {
            if (canVisitInternet) {
                canVisitInternet = false
                collectAction?.invoke()
            } else {
                ToastUtil.show("正在收藏,请稍等片刻")
            }
        }
    }

    fun setCollectAction(action : (() -> Unit)?){
        collectAction = action
    }

    fun setCollect(boolean: Boolean){
        isCollected = boolean
        canVisitInternet = true
        if (boolean){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable?.setTint(appViewModel.appColor.value!!)
            }else {
                setImageResource(R.drawable.personal_my_collection_s)
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable?.setTint(context.resources.getColor(R.color.huise_eee))
            }else {
                setImageResource(R.drawable.personal_my_collection)
            }
        }
    }
}