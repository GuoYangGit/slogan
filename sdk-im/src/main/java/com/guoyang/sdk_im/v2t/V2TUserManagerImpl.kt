package com.guoyang.sdk_im.v2t

import com.guoyang.sdk_im.entity.IMUserInfo
import com.guoyang.sdk_im.entity.IMUserState
import com.guoyang.sdk_im.userinfo.IIMUserManager
import com.tencent.imsdk.v2.*

/**
 * @author yang.guo on 2022/11/9
 * @describe 用户信息管理实现类
 */
object V2TUserManagerImpl : IIMUserManager {
    /**
     * 获取登陆用户ID
     */
    override fun getLoginUserID(): String = V2TIMManager.getInstance().loginUser

    /**
     * 设置个人资料
     * @param userInfo 个人资料
     * @param onSuccess 设置成功回调
     * @param onError 设置失败回调
     */
    override fun setSelfInfo(
        userInfo: IMUserInfo,
        onSuccess: () -> Unit,
        onError: (code: Int, msg: String) -> Unit
    ) {
        val v2TIMUserInfo = V2TIMUserFullInfo().parse(userInfo)
        V2TIMManager.getInstance().setSelfInfo(v2TIMUserInfo, object : V2TIMCallback {
            override fun onSuccess() {
                onSuccess()
            }

            override fun onError(code: Int, desc: String?) {
                onError(code, desc ?: "")
            }
        })
    }

    /**
     * 获取用户信息
     * @param userIDList 用户ID列表
     * @param onSuccess 获取成功回调
     * @param onError 获取失败回调
     */
    override fun getUserInfo(
        userIDList: List<String>,
        onSuccess: (userInfoList: List<IMUserInfo>?) -> Unit,
        onError: (code: Int, msg: String) -> Unit
    ) {
        V2TIMManager.getInstance().getUsersInfo(userIDList, object :
            V2TIMValueCallback<List<V2TIMUserFullInfo>> {
            override fun onSuccess(p0: List<V2TIMUserFullInfo>?) {
                val userInfoList = p0?.map { it.parse() }
                onSuccess(userInfoList)
            }

            override fun onError(code: Int, desc: String?) {
                onError(code, desc ?: "")
            }
        })
    }

    /**
     * 获取用户状态
     * @param userIDList 用户ID列表
     * @param onSuccess 获取成功回调
     * @param onError 获取失败回调
     */
    override fun getUserStatus(
        userIDList: List<String>,
        onSuccess: (statusList: List<IMUserState>?) -> Unit,
        onError: (code: Int, msg: String) -> Unit
    ) {
        V2TIMManager.getInstance().getUserStatus(userIDList, object :
            V2TIMValueCallback<List<V2TIMUserStatus>> {
            override fun onSuccess(p0: List<V2TIMUserStatus>?) {
                val statusList = p0?.map { it.parse() }
                onSuccess(statusList)
            }

            override fun onError(code: Int, desc: String?) {
                onError(code, desc ?: "")
            }
        })
    }

    /**
     * 加入黑名单
     * @param userIDList 用户ID列表
     * @param onSuccess 拉黑成功回调
     * @param onError 拉黑失败回调
     */
    override fun addToBlackList(
        userIDList: List<String>,
        onSuccess: () -> Unit,
        onError: (code: Int, msg: String) -> Unit
    ) {
        V2TIMManager.getFriendshipManager()
            .addToBlackList(
                userIDList,
                object : V2TIMValueCallback<List<V2TIMFriendOperationResult>> {
                    override fun onSuccess(p0: List<V2TIMFriendOperationResult>?) {
                        onSuccess()
                    }

                    override fun onError(code: Int, desc: String?) {
                        onError(code, desc ?: "")
                    }
                })
    }

    /**
     * 移除黑名单
     * @param userIDList 用户ID列表
     * @param onSuccess 移除成功回调
     * @param onError 移除失败回调
     */
    override fun removeFromBlackList(
        userIDList: List<String>,
        onSuccess: () -> Unit,
        onError: (code: Int, msg: String) -> Unit
    ) {
        V2TIMManager.getFriendshipManager()
            .deleteFromBlackList(
                userIDList,
                object : V2TIMValueCallback<List<V2TIMFriendOperationResult>> {
                    override fun onSuccess(p0: List<V2TIMFriendOperationResult>?) {
                        onSuccess()
                    }

                    override fun onError(code: Int, desc: String?) {
                        onError(code, desc ?: "")
                    }
                })
    }

    /**
     * 获取黑名单
     * @param onSuccess 获取成功回调
     * @param onError 获取失败回调
     */
    override fun getBlackList(
        onSuccess: (userIDList: List<String>?) -> Unit,
        onError: (code: Int, msg: String) -> Unit
    ) {
        V2TIMManager.getFriendshipManager()
            .getBlackList(object : V2TIMValueCallback<List<V2TIMFriendInfo>> {
                override fun onSuccess(p0: List<V2TIMFriendInfo>?) {
                    val userIDList = p0?.map { it.userID }
                    onSuccess(userIDList)
                }

                override fun onError(code: Int, desc: String?) {
                    onError(code, desc ?: "")
                }
            })
    }
}

fun V2TIMUserFullInfo.parse(userInfo: IMUserInfo): V2TIMUserFullInfo {
    setNickname(userInfo.nickName)
    faceUrl = userInfo.faceUrl
    gender = userInfo.gender
    birthday = userInfo.birthday
    level = userInfo.level
    selfSignature = userInfo.selfSignature
    return this
}

fun V2TIMUserFullInfo.parse(): IMUserInfo {
    return IMUserInfo(
        this.userID,
        this.nickName,
        this.faceUrl,
        this.gender,
        this.birthday,
        this.level,
        this.selfSignature
    )
}

fun V2TIMUserStatus.parse(): IMUserState {
    return IMUserState(
        this.userID,
        this.statusType,
        this.customStatus
    )
}