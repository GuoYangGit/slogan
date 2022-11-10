package com.guoyang.sdk_im.message

import com.tencent.imsdk.v2.V2TIMMessage

/**
 * @author yang.guo on 2022/11/9
 * @describe
 */
interface IIMReceiveMessageCallback {
    /**
     * 接收消息
     * @param v2TIMMessage 消息
     */
    fun onReceiveMessage(v2TIMMessage: V2TIMMessage)
}