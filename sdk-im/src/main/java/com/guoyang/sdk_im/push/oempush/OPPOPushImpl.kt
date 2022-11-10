package com.guoyang.sdk_im.push.oempush

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.guoyang.sdk_im.push.OEMPushSetting
import com.guoyang.sdk_im.push.PrivateConstants
import com.heytap.msp.push.callback.ICallBackResultService

class OPPOPushImpl : ICallBackResultService {
    override fun onRegister(responseCode: Int, registerID: String) {
        if (responseCode == 0) {
            OEMPushSetting.pushToken(PrivateConstants.BRAND_OPPO, registerID)
        }
    }

    override fun onUnRegister(responseCode: Int) {}
    override fun onSetPushTime(responseCode: Int, s: String) {}
    override fun onGetPushStatus(responseCode: Int, status: Int) {}
    override fun onGetNotificationStatus(responseCode: Int, status: Int) {}
    override fun onError(i: Int, s: String) {}
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val name: CharSequence = "oppotest"
        val description = "this is opptest"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("tuikit", name, importance)
        channel.description = description
        val notificationManager = context.getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(channel)
    }
}