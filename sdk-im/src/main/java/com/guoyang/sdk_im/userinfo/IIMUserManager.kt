package com.guoyang.sdk_im.userinfo

import com.guoyang.sdk_im.entity.IMUserInfo
import com.guoyang.sdk_im.entity.IMUserState

/**
 * @author yang.guo on 2022/11/9
 * @describe 用户信息管理接口
 */
interface IIMUserManager {
    /**
     * 获取登陆用户ID
     * @return 登陆用户ID
     */
    fun getLoginUserID(): String

    /**
     * 设置个人资料
     * @param userInfo 个人资料
     * @param onSuccess 设置成功回调
     * @param onError 设置失败回调
     */
    fun setSelfInfo(
        userInfo: IMUserInfo,
        onSuccess: () -> Unit,
        onError: (code: Int, msg: String) -> Unit
    )

    /**
     * 获取用户信息
     * @param userIDList 用户ID列表
     * @param onSuccess 获取成功回调
     * @param onError 获取失败回调
     */
    fun getUserInfo(
        userIDList: List<String>,
        onSuccess: (userInfoList: List<IMUserInfo>?) -> Unit,
        onError: (code: Int, msg: String) -> Unit
    )

    /**
     * 获取用户状态
     * @param userIDList 用户ID列表
     * @param onSuccess 获取成功回调
     * @param onError 获取失败回调
     */
    fun getUserStatus(
        userIDList: List<String>,
        onSuccess: (statusList: List<IMUserState>?) -> Unit,
        onError: (code: Int, msg: String) -> Unit
    )

    /**
     * 加入黑名单
     * @param userIDList 用户ID列表
     * @param onSuccess 拉黑成功回调
     * @param onError 拉黑失败回调
     */
    fun addToBlackList(
        userIDList: List<String>,
        onSuccess: () -> Unit,
        onError: (code: Int, msg: String) -> Unit
    )

    /**
     * 移除黑名单
     * @param userIDList 用户ID列表
     * @param onSuccess 移除成功回调
     * @param onError 移除失败回调
     */
    fun removeFromBlackList(
        userIDList: List<String>,
        onSuccess: () -> Unit,
        onError: (code: Int, msg: String) -> Unit
    )

    /**
     * 获取黑名单
     * @param onSuccess 获取成功回调
     * @param onError 获取失败回调
     */
    fun getBlackList(
        onSuccess: (userIDList: List<String>?) -> Unit,
        onError: (code: Int, msg: String) -> Unit
    )
}