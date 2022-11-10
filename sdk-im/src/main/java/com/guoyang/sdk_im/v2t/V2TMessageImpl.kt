package com.guoyang.sdk_im.v2t

import com.guoyang.sdk_im.entity.IMSendConfig
import com.guoyang.sdk_im.message.IIMMessage
import com.guoyang.sdk_im.message.IIMReceiveMessageCallback
import com.tencent.imsdk.v2.*


/**
 * @author yang.guo on 2022/11/8
 * V2TIM消息相关的接口类
 */
object V2TMessageImpl : IIMMessage {

    private val listenerMap = mutableMapOf<IIMReceiveMessageCallback, V2TReceiveMessageCallback>()

    /**
     * 发送消息
     * @param imMessage 要发送的消息类型
     * @param configBlock 发送消息的配置
     * @param onSuccess 发送成功的回调
     * @param onError 发送失败的回调
     * @param onProgress 发送进度的回调
     */
    override fun sendMessage(
        imMessage: V2TIMMessage,
        configBlock: IMSendConfig.() -> Unit,
        onSuccess: (message: V2TIMMessage?) -> Unit,
        onError: (code: Int, desc: String) -> Unit,
        onProgress: (progress: Int) -> Unit
    ) {
        // 创建发送消息的配置类
        val config = IMSendConfig().apply(configBlock)
        // 创建发送消息的实体类
        val v2tMessage = imMessage.apply {
            isExcludedFromLastMessage = config.isExcludedFromLastMessage
            isExcludedFromUnreadCount = config.isExcludedFromLastMessage
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

    /**
     * 添加消息监听
     * @param listener 消息监听
     */
    override fun addReceiveMessageListener(listener: IIMReceiveMessageCallback) {
        val advanceMsgListener = V2TReceiveMessageCallback(listener)
        listenerMap[listener] = advanceMsgListener
        V2TIMManager.getMessageManager().addAdvancedMsgListener(advanceMsgListener)
    }

    /**
     * 移除消息监听
     * @param listener 消息监听
     */
    override fun removeReceiveMessageListener(listener: IIMReceiveMessageCallback) {
        listenerMap[listener]?.let {
            V2TIMManager.getMessageManager().removeAdvancedMsgListener(it)
            listenerMap.remove(listener)
        }
    }

    /**
     * 获取历史消息
     * @param userID 对方的userID,userID｜groupID二选一
     * @param groupID 群聊的groupID,userID｜groupID二选一
     * @param count 获取消息的数量,建议设置为20，否则可能影响拉取速度
     * @param lastMsg 最后一条消息,如果设置为空,则使用会话的最新一条消息作为拉取起点
     * @param callback 获取消息的回调
     */
    override fun getHistoryMessage(
        userID: String,
        groupID: String,
        count: Int,
        lastMsg: V2TIMMessage?,
        callback: (success: Boolean, messageList: List<V2TIMMessage>?) -> Unit
    ) {
        val option = V2TIMMessageListGetOption()
        // 拉取消息的位置及方向，可以设置拉取 本地/云端 的 更老/更新 的消息
        // V2TIMMessageListGetOption.V2TIM_GET_CLOUD_OLDER_MSG 表示拉取云端更老的消息
        // V2TIMMessageListGetOption.V2TIM_GET_CLOUD_NEWER_MSG 表示拉取云端更新的消息
        // V2TIMMessageListGetOption.V2TIM_GET_LOCAL_OLDER_MSG 表示拉取本地更老的消息
        // V2TIMMessageListGetOption.V2TIM_GET_LOCAL_NEWER_MSG 表示拉取本地更新的消息
        option.getType = V2TIMMessageListGetOption.V2TIM_GET_CLOUD_OLDER_MSG
        option.userID = userID
        option.groupID = groupID
        option.count = count
        // 最后一条消息，用于分页拉取
        option.lastMsg = lastMsg
        // 拉取消息的回调
        V2TIMManager.getMessageManager()
            .getHistoryMessageList(option, object : V2TIMValueCallback<List<V2TIMMessage>> {
                override fun onError(code: Int, desc: String) {
                    callback(false, null)
                }

                override fun onSuccess(msgList: List<V2TIMMessage>?) {
                    callback(true, msgList)
                }
            })
    }

    /**
     * 插入消息(只会将消息插入本地数据库，不会发送到服务端)
     * 该接口主要用于满足向聊天会话中插入一些提示性消息的需求，例如 “您已经退出该群”、“请注意信息安全，不要在群聊中发送任何账号、密码和验证码等私密信息“ 等。
     * 这类消息有展示在聊天消息区的需求，但并没有发送给其他人的必要。
     * @param userID 对方的userID,userID｜groupID二选一
     * @param groupID 群聊的groupID,userID｜groupID二选一
     * @param message 要插入的消息
     * @param callback 插入消息的回调
     */
    override fun insertMessage(
        userID: String, groupID: String, message: V2TIMMessage, callback: (success: Boolean) -> Unit
    ) {
        if (userID.isNotBlank()) {
            V2TIMManager.getMessageManager().insertC2CMessageToLocalStorage(message,
                userID,
                "",
                object : V2TIMValueCallback<V2TIMMessage> {
                    override fun onError(code: Int, desc: String) {
                        callback(false)
                    }

                    override fun onSuccess(msg: V2TIMMessage?) {
                        callback(true)
                    }
                })
        } else if (groupID.isNotBlank()) {
            V2TIMManager.getMessageManager().insertGroupMessageToLocalStorage(message,
                groupID,
                "",
                object : V2TIMValueCallback<V2TIMMessage> {
                    override fun onError(code: Int, desc: String) {
                        callback(false)
                    }

                    override fun onSuccess(msg: V2TIMMessage?) {
                        callback(true)
                    }
                })
        }

    }

    /**
     * 删除消息
     * @param messageList 消息集合
     * @param callback 删除消息的回调
     */
    override fun deleteMessage(
        messageList: List<V2TIMMessage>, callback: (success: Boolean) -> Unit
    ) {
        // 删除云端存储的消息
        // 该接口会在删除本地消息的基础上，同步删除云端存储的消息，且无法恢复。
        V2TIMManager.getMessageManager().deleteMessages(messageList, object : V2TIMCallback {
            override fun onError(code: Int, desc: String) {
                callback(false)
            }

            override fun onSuccess() {
                callback(true)
            }
        })
    }

    /**
     * 清空消息
     * @param userID 对方的userID,userID｜groupID二选一
     * @param groupID 群聊的groupID,userID｜groupID二选一
     * @param callback 清空消息的回调
     */
    override fun clearMessage(
        userID: String,
        groupID: String,
        callback: (success: Boolean) -> Unit
    ) {
        if (userID.isNotBlank()) {
            V2TIMManager.getMessageManager().clearC2CHistoryMessage(
                userID,
                object : V2TIMCallback {
                    override fun onSuccess() {
                        callback(true)
                    }

                    override fun onError(code: Int, desc: String) {
                        callback(false)
                    }
                })
        } else if (groupID.isNotBlank()) {
            V2TIMManager.getMessageManager().clearGroupHistoryMessage(
                groupID,
                object : V2TIMCallback {
                    override fun onSuccess() {
                        callback(true)
                    }

                    override fun onError(code: Int, desc: String) {
                        callback(false)
                    }
                })
        }
    }

    /**
     * 撤回消息(发送者只能撤回2分钟以内的消息)
     * @param message 要撤回的消息
     * @param callback 删除消息的回调
     */
    override fun revokeMessage(message: V2TIMMessage, callback: (success: Boolean) -> Unit) {
        V2TIMManager.getMessageManager().revokeMessage(message, object : V2TIMCallback {
            override fun onError(code: Int, desc: String) {
                callback(false)
            }

            override fun onSuccess() {
                callback(true)
            }
        })
    }

    /**
     * 发送消息已读回执
     * @param messageList 消息集合
     * @param callback 发送消息已读回执的回调
     */
    override fun readReceipts(
        messageList: List<V2TIMMessage>, callback: (success: Boolean) -> Unit
    ) {
        V2TIMManager.getMessageManager()
            .sendMessageReadReceipts(messageList, object : V2TIMCallback {
                override fun onSuccess() {
                    // 发送消息已读回执成功
                    callback(true)
                }

                override fun onError(code: Int, desc: String) {
                    // 发送消息已读回执失败
                    callback(false)
                }
            })
    }
}