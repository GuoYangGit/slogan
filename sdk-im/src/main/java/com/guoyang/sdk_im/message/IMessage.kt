package com.guoyang.sdk_im.message

import com.tencent.imsdk.v2.V2TIMMessage

/**
 * @author yang.guo on 2022/11/8
 * @describe 消息相关的接口类
 */

interface IMessage {
    /**
     * 获取历史消息
     * @param userID 对方的userID,userID｜groupID二选一
     * @param groupID 群聊的groupID,userID｜groupID二选一
     * @param count 获取消息的数量,建议设置为20，否则可能影响拉取速度
     * @param lastMsg 最后一条消息,如果设置为空,则使用会话的最新一条消息作为拉取起点
     * @param callback 获取消息的回调
     */
    fun getHistoryMessage(
        userID: String = "",
        groupID: String = "",
        count: Int = 20,
        lastMsg: V2TIMMessage? = null,
        callback: (success: Boolean, messageList: List<V2TIMMessage>?) -> Unit
    )

    /**
     * 插入消息(只会将消息插入本地数据库，不会发送到服务端)
     * 该接口主要用于满足向聊天会话中插入一些提示性消息的需求，例如 “您已经退出该群”、“请注意信息安全，不要在群聊中发送任何账号、密码和验证码等私密信息“ 等。
     * 这类消息有展示在聊天消息区的需求，但并没有发送给其他人的必要。
     * @param userID 对方的userID,userID｜groupID二选一
     * @param groupID 群聊的groupID,userID｜groupID二选一
     * @param message 要插入的消息
     * @param callback 插入消息的回调
     */
    fun insertMessage(
        userID: String = "",
        groupID: String = "",
        message: V2TIMMessage,
        callback: (success: Boolean) -> Unit
    )

    /**
     * 删除消息
     * @param messageList 消息集合
     * @param callback 删除消息的回调
     */
    fun deleteMessage(messageList: List<V2TIMMessage>, callback: (success: Boolean) -> Unit)

    /**
     * 清空消息
     * @param userID 对方的userID,userID｜groupID二选一
     * @param groupID 群聊的groupID,userID｜groupID二选一
     * @param callback 清空消息的回调
     */
    fun clearMessage(
        userID: String = "",
        groupID: String = "",
        callback: (success: Boolean) -> Unit
    )

    /**
     * 撤回消息(发送者只能撤回2分钟以内的消息)
     * @param message 要撤回的消息
     * @param callback 删除消息的回调
     */
    fun revokeMessage(message: V2TIMMessage, callback: (success: Boolean) -> Unit)

    /**
     * 发送消息已读回执
     * @param messageList 消息集合
     * @param callback 发送消息已读回执的回调
     */
    fun readReceipts(messageList: List<V2TIMMessage>, callback: (success: Boolean) -> Unit)
}