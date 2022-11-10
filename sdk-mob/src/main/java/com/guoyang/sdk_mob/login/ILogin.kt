package com.guoyang.sdk_mob.login

import android.content.Context

/**
 * @author yang.guo on 2022/11/2
 * @describe 登录接口
 */
interface ILogin {
    /**
     * 初始化
     * @param context 上下文
     */
    fun init(context: Context)

    /**
     * 登录
     * @param loginPlatform 登录平台
     * @param loginListener 登录回调
     */
    fun login(loginPlatform: LoginPlatform, loginListener: LoginListener)
}