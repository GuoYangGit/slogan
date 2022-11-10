package com.guoyang.sdk_mob.share

/**
 * @author yang.guo on 2022/11/2
 * 分享接口
 */
interface ShareListener {
    /**
     * 分享成功
     */
    fun onComplete()

    /**
     * 分享失败
     * @param throwable 异常
     */
    fun onError(throwable: Throwable?)

    /**
     * 分享取消
     */
    fun onCancel()
}