package com.huafang.mvvm.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * 用户信息实体类
 * @author yang.guo on 2022/10/14
 */
@Parcelize
@Entity(tableName = "user_entity")
data class UserEntity(
    @PrimaryKey
    val userID: Long = 0,
    val userName: String = "羊羊",
    val avatar: String = "https://img.zcool.cn/community/01b72057a7e0790000018c1bf4fce0.png",
    val city: String = "",
    val age: Int = 0,
//    @Ignore
    val sex: Int = SEX_FEMALE,
) : Parcelable {
    companion object {
        const val SEX_MALE = 1
        const val SEX_FEMALE = 2
    }
}
