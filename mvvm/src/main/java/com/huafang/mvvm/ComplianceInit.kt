package com.huafang.mvvm

import com.guoyang.sdk_bugly.CrashHelper
import com.guoyang.utils_helper.*

/**
 * 关于适配合规的第三方SDK的初始化
 * @author yang.guo on 2022/11/4
 */
object ComplianceInit {
    fun init() {
        val deviceModel = deviceModel
        // 初始化Bugly
        CrashHelper.init(application) {
            this.isDebug = isAppDebug
            this.appId = "8254dc84c7"
            this.appVersion = appVersionName
            this.deviceModel = deviceModel
            // appChannel = "test"
            // deviceId = ""
        }
    }
}

