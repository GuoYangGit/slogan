package com.huafang.module_home.entity

import android.os.Parcelable
import com.huafang.mvvm.entity.UserEntity
import kotlinx.parcelize.Parcelize

/**
 * 用户发布内容实体类
 * @author yang.guo on 2022/10/14
 */
@Parcelize
data class ContentEntity(
    val id: Long = 0,
    val currentUser: UserEntity = UserEntity(),
    val locationName: String = "北京",
    val data: Long = 1665734285,
    val urls: List<String> = listOf(
        "https://img.zcool.cn/community/01b72057a7e0790000018c1bf4fce0.png",
        "https://img.zcool.cn/community/016a2256fb63006ac7257948f83349.jpg",
        "https://img.zcool.cn/community/01233056fb62fe32f875a9447400e1.jpg",
        "https://img.zcool.cn/community/01700557a7f42f0000018c1bd6eb23.jpg"
    ),
    val likeUser: List<UserEntity> = listOf(
        UserEntity(),
        UserEntity(),
        UserEntity()
    ),
    val content: String = "#日出 #粉色的海 不必形色匆匆，不必光芒四射，@小宝 不必成为别人，只需做自己，我并不讨厌等待，因为等..."
) : Parcelable