package com.guoyang.sdk_im.conversation

import com.tencent.imsdk.v2.V2TIMConversation

/**
 * @author yang.guo on 2022/11/9
 * @describe 会话监听接口
 */
interface IIMConversationCallback {

    /**
     * 同步服务器会话开始
     */
    fun onSyncServerStart() {}

    /**
     * 同步服务器会话完成
     */
    fun onSyncServerFinish() {}

    /**
     * 同步服务器会话失败
     */
    fun onSyncServerFailed() {}

    /**
     * 有会话新增
     */
    fun onNewConversation(conversationList: List<V2TIMConversation>) {}

    /**
     * 有会话更新
     */
    fun onConversationChanged(conversationList: List<V2TIMConversation>) {}

    /**
     * 会话未读总数变更通知
     */
    fun onTotalUnreadMessageCountChanged(count: Int) {}
}