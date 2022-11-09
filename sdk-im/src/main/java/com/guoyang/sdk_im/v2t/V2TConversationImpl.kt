package com.guoyang.sdk_im.v2t

import com.guoyang.sdk_im.conversation.IIMConversationCallback
import com.guoyang.sdk_im.conversation.IIMConversation
import com.tencent.imsdk.v2.*

/**
 * @author yang.guo on 2022/11/9
 * @describe V2TIM会话实现类
 */
object V2TConversationImpl : IIMConversation {
    private val listenerMap = mutableMapOf<IIMConversationCallback, T2VConversationCallback>()

    override fun getConversationList(
        nextSeq: Long,
        count: Int,
        callback: (success: Boolean, nextSeq: Long, v2TIMConversationList: List<V2TIMConversation>?) -> Unit
    ) {
        V2TIMManager.getConversationManager().getConversationList(
            nextSeq,
            count,
            object : V2TIMValueCallback<V2TIMConversationResult> {
                override fun onSuccess(p0: V2TIMConversationResult?) {
                    callback(true, p0?.nextSeq ?: 0, p0?.conversationList)
                }

                override fun onError(p0: Int, p1: String?) {
                    callback(false, 0, null)
                }
            })
    }

    override fun addConversationListener(callback: IIMConversationCallback) {
        val t2VConversationCallback = T2VConversationCallback(callback)
        listenerMap[callback] = t2VConversationCallback
        V2TIMManager.getConversationManager().addConversationListener(t2VConversationCallback)
    }

    override fun removeConversationListener(callback: IIMConversationCallback) {
        listenerMap[callback]?.let {
            V2TIMManager.getConversationManager().removeConversationListener(it)
            listenerMap.remove(callback)
        }
    }

    override fun getTotalUnreadMessageCount(callback: (count: Long) -> Unit) {
        V2TIMManager.getConversationManager()
            .getTotalUnreadMessageCount(object : V2TIMValueCallback<Long> {
                override fun onSuccess(p0: Long?) {
                    callback(p0 ?: 0)
                }

                override fun onError(p0: Int, p1: String?) {
                    callback(0)
                }
            })
    }

    override fun markMessageAsRead(
        userID: String,
        groupID: String,
        callback: (success: Boolean) -> Unit
    ) {
        when {
            userID.isNotBlank() -> { // 单聊
                V2TIMManager.getMessageManager()
                    .markC2CMessageAsRead(userID, object : V2TIMCallback {
                        override fun onSuccess() {
                            callback(true)
                        }

                        override fun onError(p0: Int, p1: String?) {
                            callback(false)
                        }
                    })
            }
            groupID.isNotBlank() -> { // 群聊
                V2TIMManager.getMessageManager()
                    .markGroupMessageAsRead(groupID, object : V2TIMCallback {
                        override fun onSuccess() {
                            callback(true)
                        }

                        override fun onError(p0: Int, p1: String?) {
                            callback(false)
                        }
                    })
            }
            else -> { // 全部
                V2TIMManager.getMessageManager().markAllMessageAsRead(object : V2TIMCallback {
                    override fun onSuccess() {
                        callback(true)
                    }

                    override fun onError(p0: Int, p1: String?) {
                        callback(false)
                    }
                })
            }
        }
    }

    override fun deleteConversation(conversationID: String, callback: (success: Boolean) -> Unit) {
        V2TIMManager.getConversationManager()
            .deleteConversation(conversationID, object : V2TIMCallback {
                override fun onSuccess() {
                    callback(true)
                }

                override fun onError(p0: Int, p1: String?) {
                    callback(false)
                }
            })
    }

    override fun pinConversation(
        conversationID: String,
        pin: Boolean,
        callback: (success: Boolean) -> Unit
    ) {
        V2TIMManager.getConversationManager()
            .pinConversation(conversationID, pin, object : V2TIMCallback {
                override fun onSuccess() {
                    callback(true)
                }

                override fun onError(p0: Int, p1: String?) {
                    callback(false)
                }
            })
    }
}

class T2VConversationCallback(private val callback: IIMConversationCallback) :
    V2TIMConversationListener() {
    override fun onSyncServerFailed() {
        super.onSyncServerFailed()
        callback.onSyncServerFailed()
    }

    override fun onSyncServerFinish() {
        super.onSyncServerFinish()
        callback.onSyncServerFinish()
    }

    override fun onSyncServerStart() {
        super.onSyncServerStart()
        callback.onSyncServerStart()
    }

    override fun onTotalUnreadMessageCountChanged(totalUnreadCount: Long) {
        super.onTotalUnreadMessageCountChanged(totalUnreadCount)
        callback.onTotalUnreadMessageCountChanged(totalUnreadCount.toInt())
    }

    override fun onNewConversation(p0: MutableList<V2TIMConversation>?) {
        super.onNewConversation(p0)
        if (p0.isNullOrEmpty()) return
        callback.onNewConversation(p0)
    }

    override fun onConversationChanged(p0: MutableList<V2TIMConversation>?) {
        super.onConversationChanged(p0)
        if (p0.isNullOrEmpty()) return
        callback.onConversationChanged(p0)
    }
}