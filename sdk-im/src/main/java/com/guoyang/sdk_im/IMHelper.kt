package com.guoyang.sdk_im

import com.guoyang.sdk_im.conversation.IIMConversation
import com.guoyang.sdk_im.init.IIMInit
import com.guoyang.sdk_im.message.IIMMessage
import com.guoyang.sdk_im.v2t.V2TConversationImpl
import com.guoyang.sdk_im.v2t.V2TInitImpl
import com.guoyang.sdk_im.v2t.V2TMessageImpl


/**
 * @author yang.guo on 2022/11/8
 * @describe
 */
object IMInitHelper : IIMInit by V2TInitImpl

object IMMessageHelper : IIMMessage by V2TMessageImpl

object IMConversationHelper : IIMConversation by V2TConversationImpl