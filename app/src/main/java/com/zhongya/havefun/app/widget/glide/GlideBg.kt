package com.zhongya.havefun.app.widget.glide

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

/**
*作者GitHub:zhongya666
*创建日期：2021/2/26 16:40
**/
object GlideBg {
    fun setBg(context: Context?, view: View?, url: String?) {
        val simpleTarget: SimpleTarget<Drawable> = object : SimpleTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                if (view != null) view.background = resource
            }
        }
        Glide.with(context!!).load(url).into(simpleTarget)
    }
}