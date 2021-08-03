package com.zhongya666.library.widget.dialog

import android.content.Context
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.zhongya666.library.R

/**
 * 作者：zhongya666 2019/8/9 9:32
 * 邮箱：997934916@qq.com
 */
class DialogPicture(context: Context) : BaseDialog(context, R.style.dialog_picture) {
    private val photoView: PhotoView
    fun showPicture(url: String?) {
        Glide.with(mContext).load(url).into(photoView)
    }

    init {
        mContext = context
        val v = LayoutInflater.from(context).inflate(R.layout.dialog_picture, null)
        photoView = v.findViewById(R.id.photoView)
        photoView.setOnClickListener { dismiss() }
        setFullScreen()
        setContentView(v)
        show()
    }
}