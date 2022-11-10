package com.guoyang.sdk_im.push

import android.content.Context
import com.guoyang.sdk_im.push.oempush.OPPOPushImpl
import com.guoyang.sdk_im.push.utils.BrandUtil
import com.guoyang.sdk_im.push.utils.PushLog
import com.heytap.msp.push.HeytapPushManager
import com.hihonor.push.sdk.HonorInstanceId
import com.hihonor.push.sdk.HonorMessaging
import com.hihonor.push.sdk.common.data.ApiException
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.push.HmsMessaging
import com.meizu.cloud.pushsdk.PushManager
import com.tencent.imsdk.v2.V2TIMCallback
import com.tencent.imsdk.v2.V2TIMManager
import com.tencent.imsdk.v2.V2TIMOfflinePushConfig
import com.vivo.push.PushClient
import com.xiaomi.mipush.sdk.MiPushClient

/**
 * @author yang.guo on 2022/11/9
 * @describe 腾讯推送设置类
 */
object OEMPushSetting {

    /**
     * 初始化推送
     * 应合规要求，在用户同意隐私协议登录成功后，分别初始化注册各个厂商推送服务
     * @param context 上下文
     */
    fun initPush(context: Context) {
        val appContext = context.applicationContext
        when (val brandID = BrandUtil.brandID) {
            PrivateConstants.BRAND_XIAOMI ->// 小米离线推送
                MiPushClient.registerPush(
                    appContext,
                    PrivateConstants.xiaomiPushAppId,
                    PrivateConstants.xiaomiPushAppKey
                )
            PrivateConstants.BRAND_MEIZU -> // 魅族离线推送
                PushManager.register(
                    appContext,
                    PrivateConstants.meizuPushAppId,
                    PrivateConstants.meizuPushAppKey
                )
            PrivateConstants.BRAND_OPPO -> { // oppo离线推送
                HeytapPushManager.init(appContext, false)
                if (!HeytapPushManager.isSupportPush(appContext)) return
                val oppo = OPPOPushImpl()
                oppo.createNotificationChannel(appContext)
                HeytapPushManager.register(
                    appContext,
                    PrivateConstants.oppoPushAppKey,
                    PrivateConstants.oppoPushAppSecret,
                    oppo
                )
                // OPPO 手机默认关闭通知，需要申请
                HeytapPushManager.requestNotificationPermission()
            }
            PrivateConstants.BRAND_VIVO -> { // vivo离线推送
                PushClient.getInstance(appContext).initialize()
                PushClient.getInstance(appContext).turnOnPush { state: Int ->
                    if (state != 0) return@turnOnPush
                    val regId = PushClient.getInstance(appContext).regId
                    pushToken(brandID, regId)
                }
            }
            PrivateConstants.BRAND_HUAWEI -> { // 华为离线推送
                // 华为离线推送，设置是否接收Push通知栏消息调用示例
                HmsMessaging.getInstance(appContext).turnOnPush().addOnCompleteListener { }
                // 华为离线推送
                object : Thread() {
                    override fun run() {
                        try {
                            // read from agconnect-services.json
                            val appId = AGConnectServicesConfig.fromContext(appContext)
                                .getString("client/app_id")
                            val token = HmsInstanceId.getInstance(appContext).getToken(appId, "HCM")
                            if (token.isNullOrBlank()) return
                            pushToken(brandID, token)
                        } catch (e: com.huawei.hms.common.ApiException) {
                            e.printStackTrace()
                        }
                    }
                }.start()
            }
            PrivateConstants.BRAND_HONOR -> { // 荣耀离线推送
                HonorMessaging.getInstance(appContext).turnOnPush().addOnCompleteListener { }
                Thread {
                    try {
                        val pushToken = HonorInstanceId.getInstance(appContext).pushToken
                        //判断pushToken是否为空
                        if (pushToken.isNullOrBlank()) return@Thread
                        //PushToken保存到您的服务器上
                        pushToken(brandID, pushToken)
                    } catch (e: ApiException) {
                        e.printStackTrace()
                    }
                }.start()
            }
            else -> {}
        }
    }

    /**
     * 上报推送token至腾讯云后台
     * @param brandID 腾讯云配置厂商证书ID
     * @param token 推送token
     */
    fun pushToken(brandID: Long, token: String) {
        val v2TIMOfflinePushConfig = V2TIMOfflinePushConfig(brandID, token)
        // 需要设置 businessID 为对应厂商的证书 ID，上报注册厂商推送服务获取的 token。
        V2TIMManager.getOfflinePushManager()
            .setOfflinePushConfig(v2TIMOfflinePushConfig, object : V2TIMCallback {
                override fun onError(code: Int, desc: String) {
                    PushLog.d("setOfflinePushToken err code = $code")
                }

                override fun onSuccess() {
                    PushLog.d("setOfflinePushToken success")
                }
            })
    }
}