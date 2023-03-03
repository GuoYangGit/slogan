package com.huafang.mvvm.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.huafang.mvvm.entity.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * 用户 Dao
 * @author yang.guo on 2022/10/25
 */
@Dao
interface UserDao {
    @Query("select * from user_entity")
    fun getAll(): Flow<List<UserEntity>>

    @Query("select * from user_entity where userID in (:userIds)")
    fun loadAllByIds(userIds: IntArray): Flow<List<UserEntity>>

    @Query("select * from user_entity where userName like :userName limit 1")
    fun findByName(userName: String): Flow<UserEntity>

    @Insert
    fun insertAll(vararg userEntity: UserEntity)

    @Delete
    fun delete(userEntity: UserEntity)
}