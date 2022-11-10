package com.guoyang.sdk_im.push.oempush

import com.guoyang.sdk_im.push.OEMPushSetting
import com.guoyang.sdk_im.push.PrivateConstants
import com.guoyang.sdk_im.push.utils.PushLog
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage


/**
 * @author yang.guo on 2022/11/9
 * 华为推送服务
 */
class HUAWEIHmsMessageService : HmsMessageService() {
    /**
     * 接收消息
     * @param message 推送消息
     */
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }

    override fun onMessageSent(msgId: String) {
        super.onMessageSent(msgId)
    }

    override fun onSendError(msgId: String, exception: Exception?) {
        super.onSendError(msgId, exception)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        OEMPushSetting.pushToken(PrivateConstants.BRAND_HUAWEI, token)
    }

    override fun onTokenError(exception: Exception) {
        super.onTokenError(exception)
    }

    override fun onMessageDelivered(msgId: String, exception: Exception?) {
        super.onMessageDelivered(msgId, exception)
    }
}