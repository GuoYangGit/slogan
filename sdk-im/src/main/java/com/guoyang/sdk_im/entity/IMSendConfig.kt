package com.guoyang.sdk_im.entity

import com.tencent.imsdk.v2.V2TIMMessage

/**
 * @author yang.guo on 2022/11/8
 * @describe 发送消息配置类
 */
class IMSendConfig {
    // 接受者的ID,可以是群ID或者用户ID
    var receiverID: String = ""

    // 是否是群消息,默认为false
    var isGroup: Boolean = false

    // 消息优先级	默认值：V2TIMMessage.V2TIM_PRIORITY_NORMAL
    var priority: Int = V2TIMMessage.V2TIM_PRIORITY_NORMAL

    // 是否只发送在线用户,默认值:false,常被用于实现 ”对方正在输入” 或群组里的非重要提示等弱提示功能
    var onlineUserOnly: Boolean = false

    // 设置消息是否需要已读回执,默认值:false
    var isNeedReadReceipt: Boolean = false

    // 是否更新lastMessage,如果您不希望一些消息（例如系统提示等）显示为会话的最新消息，可以将此参数设置为true
    var isExcludedFromLastMessage: Boolean = false
}