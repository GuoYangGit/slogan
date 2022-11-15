package com.huafang.mvvm

import com.guoyang.sdk_bugly.CrashHelper
import com.guoyang.utils_helper.*

/**
 * 关于适配合规的第三方SDK的初始化
 * @author yang.guo on 2022/11/4
 */
object ComplianceInit {
    fun init() {
        // 获取设备型号
        val deviceModel = deviceModel
        // 初始化崩溃信息统计
        CrashHelper.init(application) {
            this.isDebug = isAppDebug // 是否是Debug模式
            this.appId = "8254dc84c7" // 三方统计平台AppID
            this.appVersion = appVersionName // App版本号
            this.deviceModel = deviceModel // 设备型号
            // appChannel = "test" // 渠道号,不需要可以不填
            // deviceId = "" // 设备唯一ID,不需要可以不填
        }
    }
}

