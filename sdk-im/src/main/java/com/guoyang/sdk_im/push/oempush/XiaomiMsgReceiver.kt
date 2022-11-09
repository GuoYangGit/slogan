package com.guoyang.sdk_im.push.oempush

import android.content.Context
import android.text.TextUtils
import com.guoyang.sdk_im.push.OEMPushSetting
import com.guoyang.sdk_im.push.PrivateConstants
import com.guoyang.sdk_im.push.utils.PushLog
import com.xiaomi.mipush.sdk.*

class XiaomiMsgReceiver : PushMessageReceiver() {
    private val TAG = "XiaomiMsgReceiver"
    override fun onReceivePassThroughMessage(context: Context, miPushMessage: MiPushMessage) {}
    override fun onNotificationMessageClicked(context: Context, miPushMessage: MiPushMessage) {
        val extra = miPushMessage.extra
        val ext = extra["ext"]
        if (TextUtils.isEmpty(ext)) return
    }

    override fun onNotificationMessageArrived(context: Context, miPushMessage: MiPushMessage) {}
    override fun onReceiveRegisterResult(
        context: Context,
        miPushCommandMessage: MiPushCommandMessage
    ) {
        val command = miPushCommandMessage.command
        if (MiPushClient.COMMAND_REGISTER != command) return
        val arguments = miPushCommandMessage.commandArguments
        if (arguments.isNullOrEmpty()) return
        val token = arguments.first()
        if (miPushCommandMessage.resultCode == ErrorCode.SUCCESS.toLong()) {
            OEMPushSetting.pushToken(PrivateConstants.BRAND_XIAOMI, token)
        } else {
            PushLog.d(
                "onReceiveRegisterResult: register fail, errorCode = " + miPushCommandMessage.resultCode
            )
        }
    }

    override fun onCommandResult(context: Context, miPushCommandMessage: MiPushCommandMessage) {
        super.onCommandResult(context, miPushCommandMessage)
    }
}