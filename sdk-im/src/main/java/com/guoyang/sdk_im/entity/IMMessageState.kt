package com.guoyang.sdk_im.entity

/**
 * @author yang.guo on 2022/11/8
 * @describe 消息状态
 */
enum class IMMessageState {
    SENDING, // 发送中
    SUCCESS, // 发送成功
    FAIL, // 发送失败
    DELETE, // 删除
    RECALL,// 撤回
}