package com.guoyang.sdk_im.v2t

import com.guoyang.sdk_im.entity.*
import com.tencent.imsdk.v2.V2TIMManager
import com.tencent.imsdk.v2.V2TIMMessage
import java.io.File

/**
 * @author yang.guo on 2022/11/8
 * @describe 腾讯云IM转换工具类
 */
object V2TUtils {
    /**
     * 创建IM消息实体类
     * @return [V2TIMMessage]
     */
    fun createMessage(imMessage: IMMessage): V2TIMMessage {
        return when (imMessage) {
            is IMTextMessage -> { // 文本消息
                V2TIMManager.getMessageManager().createTextMessage(imMessage.content)
            }
            is IMImageMessage -> { // 图片消息
                V2TIMManager.getMessageManager().createImageMessage(imMessage.path)
            }
            is IMVideoMessage -> { // 视频消息
                V2TIMManager.getMessageManager().createVideoMessage(
                    imMessage.path,
                    imMessage.coverPath,
                    imMessage.duration,
                    imMessage.coverPath
                )
            }
            is IMFileMessage -> { // 文件消息
                V2TIMManager.getMessageManager()
                    .createFileMessage(imMessage.path, File(imMessage.path).name)
            }
            else -> { // 其他消息
                V2TIMManager.getMessageManager().createCustomMessage("暂不支持的消息类型".toByteArray())
            }
        }
    }
}