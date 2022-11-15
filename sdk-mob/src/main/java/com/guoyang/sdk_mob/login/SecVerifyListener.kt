package com.guoyang.sdk_mob.login

/**
 * 运营商一键登录回调接口
 * @author yang.guo on 2022/11/15
 */
interface SecVerifyListener {
    /**
     * 登录成功
     * @param token 运营商一键登录token
     * @param opToken 运营商一键登录opToken
     * @param operator 运营商类型，[CMCC:中国移动，CUCC：中国联通，CTCC：中国电信]
     */
    fun onComplete(token: String, opToken: String, operator: String)

    /**
     * 登录失败
     * @param throwable 异常
     */
    fun onError(throwable: Throwable?)

    /**
     * 点击其他登陆回调
     */
    fun onOtherLogin() {}

    /**
     * 用户点击“关闭按钮”或“物理返回键”取消登录
     */
    fun onUserCanceled() {}
}