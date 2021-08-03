package com.zhongya.havefun.bean.personal

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 积分
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class ScoreResponse(
        var coinCount: Int,//当前积分
        var rank: Int,
        var userId: Int,
        var username: String) : Parcelable


