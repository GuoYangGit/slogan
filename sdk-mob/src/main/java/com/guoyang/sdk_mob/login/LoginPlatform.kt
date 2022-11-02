package com.guoyang.sdk_mob.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author yang.guo on 2022/11/2
 * @describe 登录平台相关信息
 */

/**
 * 登录平台枚举
 */
enum class LoginPlatform {
    WECHAT, QQ, AUTH_PHONE
}

@Parcelize
data class LoginData(
    val userId: String, // 用户id
    val userName: String, // 用户名
    val userIcon: String, // 用户头像
    val userGender: String, // 用户性别
    val exportData: String, // 导出数据
) : Parcelable