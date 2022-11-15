package com.guoyang.sdk_mob.login

import android.content.Context

/**
 * 登录接口
 * @author yang.guo on 2022/11/2
 */
interface ILogin {
    /**
     * 初始化(请务必在用户授权《隐私政策》后再初始化)
     * @param context 上下文 [Context]
     */
    fun init(context: Context)

    /**
     * 登录
     * @param loginPlatform 登录平台 [LoginPlatform]
     * @param loginListener 登录回调 [LoginListener]
     */
    fun login(loginPlatform: LoginPlatform, loginListener: LoginListener)

    /**
     * 运营商一键登录
     * @param secVerifyListener 运营商一键登录回调 [SecVerifyListener]
     */
    fun secVerify(secVerifyListener: SecVerifyListener)
}