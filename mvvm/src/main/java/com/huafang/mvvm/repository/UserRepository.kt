package com.huafang.mvvm.repository

import com.dylanc.mmkv.MMKVOwner
import com.dylanc.mmkv.mmkvBool
import com.dylanc.mmkv.mmkvParcelable
import com.huafang.mvvm.entity.UserEntity

/**
 * 用户相关[MMKVOwner]存储类
 * @author yang.guo on 2022/10/17
 */
object UserRepository : MMKVOwner {
    /**
     * 是否显示用户协议
     */
    var isShowAgreement by mmkvBool(default = false)

    /**
     * 用户是否已同意协议
     */
    var isAgreement by mmkvBool(default = false)

    /**
     * 登陆用户信息
     */
    var user by mmkvParcelable<UserEntity>()

//    fun clear() {
//        // 对象进行删除值操作
//        kv.removeValueForKey(::isShowAgreement.name)
//        // 清理缓存
//        kv.clearAll()
//    }
}