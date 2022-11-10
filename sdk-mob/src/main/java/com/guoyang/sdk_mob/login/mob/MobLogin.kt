package com.guoyang.sdk_mob.login.mob

import android.content.Context
import android.util.Log
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.tencent.qq.QQ
import cn.sharesdk.wechat.friends.Wechat
import com.guoyang.sdk_mob.login.ILogin
import com.guoyang.sdk_mob.login.LoginListener
import com.guoyang.sdk_mob.login.LoginPlatform
import com.mob.MobSDK
import com.mob.secverify.PreVerifyCallback
import com.mob.secverify.SecVerify
import com.mob.secverify.VerifyCallback
import com.mob.secverify.common.exception.VerifyException
import com.mob.secverify.datatype.VerifyResult

/**
 * @author yang.guo on 2022/11/2
 * @describe
 */
object MobLogin : ILogin {
    const val TAG = "MobLogin"

    /**
     * 初始化登陆SDK
     * @param context 上下文
     */
    override fun init(context: Context) {
        // 合规指南，用户同意隐私协议后调用
        MobSDK.submitPolicyGrantResult(true)
    }

    /**
     * 登录
     * @param loginPlatform 登录平台
     * @param loginListener 登录回调
     */
    override fun login(loginPlatform: LoginPlatform, loginListener: LoginListener) {
        if (loginPlatform == LoginPlatform.AUTH_PHONE) {
            authPhoneLogin(loginListener)
            return
        }
        val platform = ShareSDK.getPlatform(getPlatform(loginPlatform))
        // 判断客户端是否可用
        if (!platform.isClientValid) {
            loginListener.onError(Throwable("客户端不可用"))
            return
        }
        // 判断是否已经授权
        if (platform.isAuthValid) {
            // 移除授权状态和本地缓存，下次授权会重新授权
            platform.removeAccount(true)
        }
        platform.platformActionListener = MobLoginListener(loginListener)
        // 设置false表示使用SSO授权方式
        platform.SSOSetting(false)
        // 执行登录，登录后在回调里面获取用户资料
        platform.showUser(null)
    }

    /**
     * 手机号自动登录
     * [说明文档](https://www.mob.com/wiki/detailed?wiki=297&id=78)
     * @param loginListener 登录回调
     */
    private fun authPhoneLogin(loginListener: LoginListener) {
        // 建议提前调用预登录接口，可以加快免密登录过程，提高用户的体验。
        SecVerify.preVerify(object : PreVerifyCallback() {
            override fun onComplete(data: Void) {
                // 处理成功的结果
                SecVerify.verify(object : VerifyCallback() {
                    override fun onComplete(data: VerifyResult) {
                        // 获取授权码成功，将token信息传给应用服务端，再由应用服务端进行登录验证，此功能需由开发者自行实现
                        val opToken: String = data.opToken
                        val token: String = data.token
                        // 运营商类型，[CMCC:中国移动，CUCC：中国联通，CTCC：中国电信]
                        val operator: String = data.operator
                    }

                    override fun onFailure(exception: VerifyException) {
                        //TODO处理失败的结果
                        loginListener.onError(Throwable(exception.message))
                    }

                    override fun onOtherLogin() {
                        // 用户点击“其他登录方式”，处理自己的逻辑
                    }

                    override fun onUserCanceled() {
                        // 用户点击“关闭按钮”或“物理返回键”取消登录，处理自己的逻辑
                    }
                })
            }

            override fun onFailure(exception: VerifyException) {
                // 获取错误码
                val errCode: Int = exception.code
                Log.d(TAG, "authPhoneLogin onFailure: $errCode")
                // 更详细的网络错误信息可以通过t查看，注：t有可能为null,也可用于获取运营商返回的错误信息
                val errDetail: String? = exception.cause?.run {
                    message
                } ?: exception.message
                loginListener.onError(Throwable(errDetail))
            }

        })
    }

    /**
     * 获取登陆平台
     * @param platform 登陆平台枚举
     */
    private fun getPlatform(platform: LoginPlatform): String {
        return when (platform) {
            LoginPlatform.QQ -> QQ.NAME
            LoginPlatform.WECHAT -> Wechat.NAME
            LoginPlatform.AUTH_PHONE -> ""
        }
    }
}