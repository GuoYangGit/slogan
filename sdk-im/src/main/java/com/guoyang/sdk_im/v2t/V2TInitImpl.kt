package com.guoyang.sdk_im.v2t

import android.content.Context
import com.guoyang.sdk_im.init.IIMInit
import com.tencent.imsdk.v2.*

/**
 * @author yang.guo on 2022/11/8
 * V2TIM初始化实现类
 */
object V2TInitImpl : IIMInit {
    /**
     * 初始化IM
     * @param appContext 应用上下文
     * @param sdkAppID IM应用ID
     */
    override fun init(appContext: Context, sdkAppID: Int) {
        // 初始化 config 对象
        val config = V2TIMSDKConfig()
        // 指定 log 输出级别
        config.logLevel = V2TIMSDKConfig.V2TIM_LOG_INFO
        // 制定 log 输出监听器
        config.logListener = object : V2TIMLogListener() {
            override fun onLog(logLevel: Int, logContent: String?) {
                super.onLog(logLevel, logContent)
                // log 输出回调
            }
        }
        V2TIMManager.getInstance().initSDK(appContext, sdkAppID, config)
    }

    /**
     * 添加IM初始化监听
     * @param onConnecting 正在连接到腾讯云服务器,适合在 UI 上展示 “正在连接” 状态。
     * @param onConnectSuccess 已经成功连接到腾讯云服务器
     * @param onConnectFailed 连接腾讯云服务器失败
     * @param onLoginFail 用户登陆过期(isKickedOffline 是否被踢下线)
     */
    override fun addSdkListener(
        onConnecting: () -> Unit,
        onConnectSuccess: () -> Unit,
        onConnectFailed: (code: Int, error: String?) -> Unit,
        onLoginFail: (isKickedOffline: Boolean) -> Unit
    ) {
        val sdkListener = object : V2TIMSDKListener() {
            /**
             * 正在连接到腾讯云服务器,适合在 UI 上展示 “正在连接” 状态。
             */
            override fun onConnecting() {
                super.onConnecting()
                onConnecting()
            }

            /**
             * 已经成功连接到腾讯云服务器
             */
            override fun onConnectSuccess() {
                super.onConnectSuccess()
                onConnectSuccess()
            }

            /**
             * 连接腾讯云服务器失败
             */
            override fun onConnectFailed(code: Int, error: String?) {
                super.onConnectFailed(code, error)
                onConnectFailed(code, error)
            }

            /**
             * 当前用户被踢下线,此时可以 UI 提示用户 “您已经在其他端登录了当前帐号，是否重新登录？”
             */
            override fun onKickedOffline() {
                super.onKickedOffline()
                onLoginFail(true)
            }

            /**
             * 登录票据已经过期,请使用新签发的 UserSig 进行登录。
             */
            override fun onUserSigExpired() {
                super.onUserSigExpired()
                onLoginFail(false)
            }

            /**
             * 登陆用户信息更新
             */
            override fun onSelfInfoUpdated(info: V2TIMUserFullInfo?) {
                super.onSelfInfoUpdated(info)
            }

            /**
             * 用户状态改变(包括自己、好友状态),ONLINE-在线,OFFLINE-离线,UNLOGINED-未登录
             */
            override fun onUserStatusChanged(userStatusList: MutableList<V2TIMUserStatus>?) {
                super.onUserStatusChanged(userStatusList)
            }
        }
        V2TIMManager.getInstance().addIMSDKListener(sdkListener)
    }

    /**
     * 登录IM
     * @param userID 用户ID
     * @param userSig 用户签名
     * @param onSuccess 登录回调
     * @param onError 登录失败回调
     */
    override fun loginIM(
        userID: String,
        userSig: String,
        onSuccess: () -> Unit,
        onError: (code: Int, msg: String) -> Unit
    ) {
        // 获取登陆的UserID
        val loginUserID = V2TIMManager.getInstance().loginUser
        // 获取当前登陆状态
        val loginStatus = V2TIMManager.getInstance().loginStatus
        // 判断如果当前登陆的UserID和传入的UserID一致，且当前登陆状态不是未登陆，则直接回调成功
        if (loginUserID == userID && loginStatus != V2TIMManager.V2TIM_STATUS_LOGOUT) {
            onSuccess()
            return
        }
        V2TIMManager.getInstance().login(userID, userSig, object : V2TIMCallback {
            override fun onSuccess() {
                // 登录成功
                onSuccess()
            }

            override fun onError(code: Int, desc: String?) {
                // 登录失败
                onError(code, desc ?: "")
            }
        })
    }

    /**
     * 登出IM
     * @param onSuccess 登出回调
     * @param onError 登出失败回调
     */
    override fun logoutIM(onSuccess: () -> Unit, onError: (code: Int, msg: String) -> Unit) {
        V2TIMManager.getInstance().logout(object : V2TIMCallback {
            override fun onSuccess() {
                // 登出成功
                onSuccess()
            }

            override fun onError(code: Int, desc: String?) {
                // 登出失败
                onError(code, desc ?: "")
            }
        })
    }
}