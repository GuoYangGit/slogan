package com.guoyang.sdk_mob.login

/**
 * 登录回调接口
 * @author yang.guo on 2022/11/2
 */
interface LoginListener {
    /**
     * 登录成功
     * @param data 登录数据 [LoginData]
     */
    fun onComplete(data: LoginData)

    /**
     * 登录失败
     * @param throwable 异常
     */
    fun onError(throwable: Throwable?)

    /**
     * 登录取消
     */
    fun onCancel()
}