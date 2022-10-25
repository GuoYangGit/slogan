package com.huafang.mvvm.repository

import com.dylanc.mmkv.MMKVOwner
import com.dylanc.mmkv.mmkvBool
import com.dylanc.mmkv.mmkvParcelable
import com.huafang.mvvm.entity.UserEntity

/**
 * @author yang.guo on 2022/10/17
 * @describe 用户相关[MMKVOwner]存储类
 */
object UserRepository : MMKVOwner {
    // 是否显示用户协议
    var isShowAgreement by mmkvBool(default = false)

    // 用户是否已同意协议
    var isAgreement by mmkvBool(default = false)

    // 登陆用户信息
    var user by mmkvParcelable<UserEntity>()
}