package com.guoyang.sdk_mob.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 登录平台枚举
 */
enum class LoginPlatform {
    WECHAT, // 微信
    QQ, // QQ
}

/**
 * 登陆数据实体类
 */
@Parcelize
data class LoginData(
    val userId: String, // 用户id
    val userName: String, // 用户名
    val userIcon: String, // 用户头像
    val userGender: String, // 用户性别
    val exportData: String, // 导出数据
) : Parcelable