package com.guoyang.sdk_bugly

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author yang.guo on 2022/11/2
 * 崩溃上报配置类
 */

@Parcelize
data class CrashConfig(
    var isDebug: Boolean = false, // 是否开启调试模式
    var appId: String = "", // 三方平台 AppId
    var appVersion: String = "", // App版本号
    var appChannel: String = "", // App渠道号
    var deviceId: String = "", // 设备ID
    var deviceModel: String = "", // 设备型号
) : Parcelable