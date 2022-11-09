package com.guoyang.sdk_im.push.oempush

import android.content.Context
import com.vivo.push.model.UPSNotificationMessage
import com.vivo.push.sdk.OpenClientPushMessageReceiver

class VIVOPushMessageReceiverImpl : OpenClientPushMessageReceiver() {
    override fun onNotificationMessageClicked(
        context: Context,
        upsNotificationMessage: UPSNotificationMessage
    ) {
    }

    override fun onReceiveRegId(context: Context, regId: String) {}
}