package com.huafang.module_login.entity

data class LoginEntity(
    val token: String = "",
    val type: String = "",
    val userInfo: UserInfo = UserInfo()
)

data class UserInfo(
    val avatar: String = "",
    val birthday: String = "",
    val inviteCode: String = "",
    val invitedCode: String = "",
    val nickname: String = "",
    val sex: String = "",
    val signature: String = "",
    val userId: Int = 0,
    val userPhone: String = ""
)