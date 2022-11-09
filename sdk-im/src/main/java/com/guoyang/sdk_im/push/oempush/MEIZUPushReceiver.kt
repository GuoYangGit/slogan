package com.guoyang.sdk_im.push.oempush

import android.content.Context
import android.content.Intent
import com.guoyang.sdk_im.push.OEMPushSetting
import com.guoyang.sdk_im.push.PrivateConstants
import com.meizu.cloud.pushsdk.MzPushMessageReceiver
import com.meizu.cloud.pushsdk.handler.MzPushMessage
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder
import com.meizu.cloud.pushsdk.platform.message.*


/**
 * @author yang.guo on 2022/11/9
 * @describe
 */
class MEIZUPushReceiver : MzPushMessageReceiver() {
    override fun onMessage(context: Context?, s: String) {
    }

    override fun onMessage(context: Context?, message: String, platformExtra: String) {
    }

    override fun onMessage(context: Context?, intent: Intent) {
    }

    override fun onUpdateNotificationBuilder(pushNotificationBuilder: PushNotificationBuilder?) {
        super.onUpdateNotificationBuilder(pushNotificationBuilder)
    }

    override fun onNotificationClicked(context: Context?, mzPushMessage: MzPushMessage) {
    }

    override fun onNotificationArrived(context: Context?, mzPushMessage: MzPushMessage?) {
        super.onNotificationArrived(context, mzPushMessage)
    }

    override fun onNotificationDeleted(context: Context?, mzPushMessage: MzPushMessage?) {
        super.onNotificationDeleted(context, mzPushMessage)
    }

    override fun onNotifyMessageArrived(context: Context?, s: String?) {
        super.onNotifyMessageArrived(context, s)
    }

    override fun onPushStatus(context: Context?, pushSwitchStatus: PushSwitchStatus?) {}
    override fun onRegisterStatus(context: Context?, registerStatus: RegisterStatus) {
        val token = registerStatus.pushId
        OEMPushSetting.pushToken(PrivateConstants.BRAND_MEIZU, token)
    }

    override fun onUnRegisterStatus(context: Context?, unRegisterStatus: UnRegisterStatus?) {}
    override fun onSubTagsStatus(context: Context?, subTagsStatus: SubTagsStatus?) {}
    override fun onSubAliasStatus(context: Context?, subAliasStatus: SubAliasStatus?) {}
    override fun onRegister(context: Context?, s: String?) {}
    override fun onUnRegister(context: Context?, b: Boolean) {}
}