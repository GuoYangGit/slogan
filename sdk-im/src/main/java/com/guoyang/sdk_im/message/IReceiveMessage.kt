package com.guoyang.sdk_im.message

import com.tencent.imsdk.v2.V2TIMMessage

/**
 * @author yang.guo on 2022/11/8
 * @describe 接受消息接口类
 */
interface IReceiveMessage<T : IReceiveMessageCallback> {
    /**
     * 添加消息监听
     * @param listener 消息监听
     */
    fun addReceiveMessageListener(listener: T)

    /**
     * 移除消息监听
     * @param listener 消息监听
     */
    fun removeReceiveMessageListener(listener: T)
}

interface IReceiveMessageCallback {
    /**
     * 接收消息
     * @param v2TIMMessage 消息
     */
    fun onReceiveMessage(v2TIMMessage: V2TIMMessage)
}