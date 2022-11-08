package com.guoyang.sdk_im.message

import com.guoyang.sdk_im.entity.IMMessage
import com.guoyang.sdk_im.entity.IMSendConfig
import com.tencent.imsdk.v2.V2TIMMessage

/**
 * @author yang.guo on 2022/11/8
 * @describe 发送消息接口类
 */
interface ISendMessage {
    /**
     * 发送消息
     * @param imMessage 要发送的消息类型
     * @param configBlock 发送消息的配置
     * @param onSuccess 发送成功的回调
     * @param onError 发送失败的回调
     * @param onProgress 发送进度的回调
     */
    fun sendMessage(
        imMessage: IMMessage,
        configBlock: IMSendConfig.() -> Unit = {},
        onSuccess: (message: V2TIMMessage?) -> Unit,
        onError: (code: Int, desc: String) -> Unit = { _, _ -> },
        onProgress: (progress: Int) -> Unit = { _ -> }
    )
}