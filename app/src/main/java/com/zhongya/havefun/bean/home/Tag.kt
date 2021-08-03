package com.zhongya.havefun.bean.home

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class Tag(
    val name: String,
    val url: String
) : Parcelable