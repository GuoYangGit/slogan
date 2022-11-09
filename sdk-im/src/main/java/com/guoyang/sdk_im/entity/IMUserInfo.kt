package com.guoyang.sdk_im.entity

import com.tencent.imsdk.relationship.UserInfo

/**
 * @author yang.guo on 2022/11/9
 * @describe
 */
data class IMUserInfo(
    val userID: String, // 用户ID
    val nickName: String, // 昵称
    val faceUrl: String, // 头像
    val gender: Int, // 性别
    val birthday: Long, // 生日
    val level: Int = 0, // 等级
    val selfSignature: String = "", // 个性签名
) {
    companion object {
        const val GENDER_UNKNOWN = 0 // 未知
        const val GENDER_MALE = 1 // 男
        const val GENDER_FEMALE = 2 // 女
    }
}

data class IMFriendInfo(
    val userID: String, // 用户ID
    val friendRemark: String, // 好友备注
    val friendAddTime: Long, // 好友添加时间
    val userInfo: UserInfo, // 用户信息
    val relationType: Int, // 关系类型
) {
    companion object {
        const val RELATION_TYPE_SINGLE = 1 // 单向好友
        const val RELATION_TYPE_BOTH = 2 // 双向好友
    }
}

data class IMUserState(
    val userID: String, // 用户ID
    val state: Int, // 用户状态
    val stateDesc: String // 用户状态描述
) {
    companion object {
        const val STATUS_UNKNOWN = 0 // 未知
        const val STATUS_ONLINE = 1 // 在线
        const val STATUS_OFFLINE = 2 // 离线
        const val STATUS_UNLOGINED = 3 // 离开
    }
}