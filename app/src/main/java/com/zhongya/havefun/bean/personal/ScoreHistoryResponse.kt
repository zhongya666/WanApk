package com.zhongya.havefun.bean.personal

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 积分记录
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class ScoreHistoryResponse(
        var coinCount: Int,
        var date: Long,
        var desc: String,
        var id: Int,
        var type: Int,
        var reason: String,
        var userId: Int,
        var userName: String) : Parcelable


