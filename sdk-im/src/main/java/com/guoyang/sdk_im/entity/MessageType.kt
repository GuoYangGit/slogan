package com.guoyang.sdk_im.entity

/**
 * @author yang.guo on 2022/11/8
 * @describe 消息类型
 */
sealed class MessageType {
    object TXT : MessageType() // 文本消息
    object IMAGE : MessageType() // 图片消息
    object AUDIO : MessageType() // 语音消息
    object VIDEO : MessageType() // 视频消息
    object FILE : MessageType() // 文件消息
    object CUSTOM : MessageType() // 自定义消息
    object TIP : MessageType() // 提示消息
    object SYSTEM : MessageType() // 系统消息
    object LOCATION : MessageType() // 地理位置消息
}