package com.guoyang.sdk_bugly

import android.content.Context

/**
 * 崩溃上报接口
 * @author yang.guo on 2022/11/2
 */
interface ICrash {

    /**
     * 初始化(请务必在用户授权《隐私政策》后再初始化)
     * @param context 上下文 [Context]
     * @param config 崩溃上报配置 [CrashConfig]
     */
    fun init(context: Context, config: CrashConfig.() -> Unit)

    /**
     * 设置用户ID
     * @param userId 用户ID
     */
    fun setUserId(userId: String) {}
}