package com.zhongya666.library.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * GitHub/Gitee：zhongya666 2021/7/27 17:39
 *
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class ToExampleBean(
    var from : Int
): Parcelable
