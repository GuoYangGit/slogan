package com.guoyang.sdk_im.v2t

import com.guoyang.sdk_im.entity.IMMessage
import com.guoyang.sdk_im.entity.IMSendConfig
import com.guoyang.sdk_im.message.ISendMessage
import com.tencent.imsdk.v2.V2TIMManager
import com.tencent.imsdk.v2.V2TIMMessage
import com.tencent.imsdk.v2.V2TIMSendCallback

/**
 * @author yang.guo on 2022/11/8
 * @describe 腾讯云IM发送消息实现类
 */
object V2TSendImpl : ISendMessage {
    /**
     * 发送消息
     * @param imMessage 要发送的消息类型
     * @param configBlock 发送消息的配置
     * @param onSuccess 发送成功的回调
     * @param onError 发送失败的回调
     * @param onProgress 发送进度的回调
     */
    override fun sendMessage(
        imMessage: IMMessage,
        configBlock: IMSendConfig.() -> Unit,
        onSuccess: (message: V2TIMMessage?) -> Unit,
        onError: (code: Int, desc: String) -> Unit,
        onProgress: (progress: Int) -> Unit
    ) {
        // 创建发送消息的配置类
        val config = IMSendConfig().apply(configBlock)
        // 创建发送消息的实体类
        val v2tMessage = V2TUtils.createMessage(imMessage).apply {
            isExcludedFromLastMessage = config.isExcludedFromLastMessage
            isNeedReadReceipt = config.isNeedReadReceipt
        }
        // 创建消息回调
        val v2TIMValueCallback = object : V2TIMSendCallback<V2TIMMessage> {
            override fun onProgress(progress: Int) {
                onProgress(progress)
            }

            override fun onError(code: Int, desc: String) {
                onError(code, desc)
            }

            override fun onSuccess(msg: V2TIMMessage) {
                onSuccess(msg)
            }
        }
        V2TIMManager.getMessageManager().sendMessage(
            v2tMessage, // 消息实体
            if (!config.isGroup) config.receiverID else "", // 接收者ID
            if (config.isGroup) config.receiverID else "", // 群组ID
            config.priority, // 消息优先级
            config.onlineUserOnly, // 是否只发送在线用户
            null, // 离线推送信息
            v2TIMValueCallback // 消息回调
        )
    }
}