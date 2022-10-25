package com.guoyang.sdk_mob

import android.content.Context
import com.mob.MobSDK
import com.mob.secverify.PreVerifyCallback
import com.mob.secverify.SecVerify
import com.mob.secverify.VerifyCallback
import com.mob.secverify.common.exception.VerifyException
import com.mob.secverify.datatype.VerifyResult

/**
 * @author yang.guo on 2022/10/25
 * @describe
 */
class LoginSdk {
    /**
     * 初始化登陆SDK
     * @param appContext [Application]
     * 强烈建议开发者在终端用户点击应用隐私协议弹窗同意按钮后调用
     */
    fun init(appContext: Context) {
        MobSDK.submitPolicyGrantResult(true)
    }

    fun login() {
        // 建议提前调用预登录接口，可以加快免密登录过程，提高用户的体验。
        SecVerify.preVerify(object : PreVerifyCallback() {
            override fun onComplete(data: Void) {
                // TODO处理成功的结果
                autoLogin()
            }

            override fun onFailure(e: VerifyException) {
                // TODO处理失败的结果
                // 获取错误码
                // TODO处理失败的结果
                // 获取错误码
                val errCode: Int = e.code
                //获取SDK返回的错误信息
                //获取SDK返回的错误信息
                val errMsg: String? = e.message
                // 更详细的网络错误信息可以通过t查看，注：t有可能为null,也可用于获取运营商返回的错误信息
                // 更详细的网络错误信息可以通过t查看，注：t有可能为null,也可用于获取运营商返回的错误信息
                val t: Throwable? = e.cause
                var errDetail: String? = null
                if (t != null) {
                    errDetail = t.message
                }
            }

        })
    }

    private fun autoLogin() {
        SecVerify.verify(object : VerifyCallback() {
            override fun onComplete(data: VerifyResult) {
                // 获取授权码成功，将token信息传给应用服务端，再由应用服务端进行登录验证，此功能需由开发者自行实现
                // opToken
                val opToken: String = data.opToken
                // token
                val token: String = data.token
                // 运营商类型，[CMCC:中国移动，CUCC：中国联通，CTCC：中国电信]
                val operator: String = data.operator
            }

            override fun onFailure(p0: VerifyException) {
                //TODO处理失败的结果
            }

            override fun onOtherLogin() {
                // 用户点击“其他登录方式”，处理自己的逻辑
            }

            override fun onUserCanceled() {
                // 用户点击“关闭按钮”或“物理返回键”取消登录，处理自己的逻辑
            }
        })
    }
}