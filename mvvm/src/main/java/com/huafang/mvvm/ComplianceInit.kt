package com.huafang.mvvm

import com.dylanc.longan.appVersionName
import com.dylanc.longan.application
import com.dylanc.longan.deviceModel
import com.dylanc.longan.isAppDebug
import com.guoyang.sdk_bugly.CrashHelper

/**
 * @author yang.guo on 2022/11/4
 * 关于适配合规的第三方SDK的初始化
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

