package com.guoyang.sdk_im.push.oempush

import android.text.TextUtils
import com.guoyang.sdk_im.push.OEMPushSetting
import com.guoyang.sdk_im.push.PrivateConstants
import com.hihonor.push.sdk.HonorMessageService
import com.hihonor.push.sdk.bean.DataMessage

/**
 * @author yang.guo on 2022/11/9
 * @describe
 */
class MyHonorMessageService : HonorMessageService() {
    override fun onNewToken(token: String) {
        if (TextUtils.isEmpty(token)) return
        OEMPushSetting.pushToken(PrivateConstants.BRAND_HONOR, token)
    }

    override fun onMessageReceived(dataMessage: DataMessage) {
    }
}