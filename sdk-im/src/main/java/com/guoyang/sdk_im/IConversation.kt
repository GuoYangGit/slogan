package com.guoyang.sdk_im

import com.tencent.imsdk.v2.V2TIMConversationResult

/**
 * @author yang.guo on 2022/11/8
 * @describe 会话列表相关的接口类
 */
interface IConversation {
    /**
     * 获取会话列表
     * @param nextSeq 下一次拉取的seq,第一次拉取时填0,可以通过v2TIMConversationResult.getNextSeq()获取
     * @param count 拉取的数量,每次分页拉取的数量建议不超过100个
     */
    fun getConversationList(
        nextSeq: Int = 0,
        count: Int = 50,
        callback: (success: Boolean, v2TIMConversationResult: V2TIMConversationResult?) -> Unit
    )
}