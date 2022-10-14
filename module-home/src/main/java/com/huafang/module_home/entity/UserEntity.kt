package com.huafang.module_home.entity

/**
 * @author yang.guo on 2022/10/14
 * @describe 用户信息实体类
 */
data class UserEntity(
    val userID: Long = 0,
    val userName: String = "羊羊",
    val avatar: String = "https://img.zcool.cn/community/01b72057a7e0790000018c1bf4fce0.png",
    val city: String = "",
)