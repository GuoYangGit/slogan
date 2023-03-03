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
    var userID: Long = 0,
    var token: String = "",
    var avatar: String = "",
    var userName: String = "",
    var birthday: String = "",
    var phone: String = "",
//    @Ignore
    var sex: Int = SEX_MALE,
) : Parcelable {
    companion object {
        const val SEX_MALE = 1
        const val SEX_FEMALE = 2
    }
}
