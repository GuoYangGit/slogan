package com.guoyang.sdk_im.init

import android.content.Context

/**
 * @author yang.guo on 2022/11/8
 * IM初始化接口
 */
interface IIMInit {
    /**
     * 初始化IM
     * @param appContext 应用上下文
     * @param sdkAppID IM应用ID
     */
    fun init(appContext: Context, sdkAppID: Int)

    /**
     * 添加IM初始化监听
     * @param onConnecting 正在连接到腾讯云服务器,适合在 UI 上展示 “正在连接” 状态。
     * @param onConnectSuccess 已经成功连接到腾讯云服务器
     * @param onConnectFailed 连接腾讯云服务器失败
     * @param onLoginFail 用户登陆过期(isKickedOffline 是否被踢下线)
     */
    fun addSdkListener(
        onConnecting: () -> Unit = {},
        onConnectSuccess: () -> Unit = {},
        onConnectFailed: (code: Int, error: String?) -> Unit = { _, _ -> },
        onLoginFail: (isKickedOffline: Boolean) -> Unit = {},
    )

    /**
     * 登录IM
     * @param userID 用户ID
     * @param userSig 用户签名
     * @param onSuccess 登录回调
     * @param onError 登录失败回调
     */
    fun loginIM(
        userID: String,
        userSig: String,
        onSuccess: () -> Unit,
        onError: (code: Int, msg: String) -> Unit
    )

    /**
     * 登出IM
     * @param onSuccess 登出回调
     * @param onError 登出失败回调
     */
    fun logoutIM(
        onSuccess: () -> Unit,
        onError: (code: Int, msg: String) -> Unit
    )
}