package com.guoyang.sdk_im.conversation

import com.tencent.imsdk.v2.V2TIMConversation

/**
 * @author yang.guo on 2022/11/8
 * @describe 会话列表相关的接口类
 */
interface IIMConversation {
    /**
     * 获取会话列表
     * @param nextSeq 下一次拉取的seq,第一次拉取时填0,可以通过v2TIMConversationResult.getNextSeq()获取
     * @param count 拉取的数量,每次分页拉取的数量建议不超过100个
     */
    fun getConversationList(
        nextSeq: Long = 0,
        count: Int = 50,
        callback: (success: Boolean, nextSeq: Long, v2TIMConversationList: List<V2TIMConversation>?) -> Unit
    )

    /**
     * 添加会话监听回调
     * @param callback 会话监听回调
     */
    fun addConversationListener(callback: IIMConversationCallback)

    /**
     * 移除会话监听回调
     * @param callback 会话监听回调
     */
    fun removeConversationListener(callback: IIMConversationCallback)

    /**
     * 获取未读总数
     * @param callback 获取未读总数的回调
     */
    fun getTotalUnreadMessageCount(callback: (count: Long) -> Unit)

    /**
     * 清空会话未读消息数
     * @param userID 用户ID,如果userID不为空,则清空与userID的会话未读消息数
     * @param groupID 群组ID,如果groupID不为空,则清空与groupID的会话未读消息数
     * 如果userID&groupID为空,则清空所有会话的未读消息数
     * @param callback 清空会话未读消息数的回调
     */
    fun markMessageAsRead(
        userID: String = "",
        groupID: String = "",
        callback: (success: Boolean) -> Unit
    )

    /**
     * 删除会话
     * @param conversationID 会话ID
     * @param callback 删除会话的回调
     */
    fun deleteConversation(conversationID: String, callback: (success: Boolean) -> Unit)

    /**
     * 置顶会话,可以通过isPinned字段，检查会话有没有置顶
     * @param conversationID 会话ID
     * @param pin 是否置顶,true:置顶,false:取消置顶
     * @param callback 置顶会话的回调
     */
    fun pinConversation(
        conversationID: String,
        pin: Boolean = true,
        callback: (success: Boolean) -> Unit
    )
}

