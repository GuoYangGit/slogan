package com.guoyang.sdk_mob.login

/**
 * @author yang.guo on 2022/11/2
 * @describe 登录接口
 */
interface LoginListener {
    /**
     * 登录成功
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