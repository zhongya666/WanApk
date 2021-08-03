package com.zhongya.havefun.bean.personal

/**
 * GitHub/Gitee：zhongya666 2021/7/9 11:19
 *
 */
data class LoginResp(
    val admin: Boolean,
    val coinCount: Int,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)