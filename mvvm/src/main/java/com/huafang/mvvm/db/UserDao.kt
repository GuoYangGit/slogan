package com.huafang.mvvm.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.huafang.mvvm.entity.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author yang.guo on 2022/10/25
 * @describe 用户Dao
 */
@Dao
interface UserDao {
    @Query("select * from user_entity")
    fun getAll(): Flow<List<UserEntity>>

    @Query("select * from user_entity where userID in (:userIds)")
    fun loadAllByIds(userIds: IntArray): Flow<List<UserEntity>>

    @Query("select * from user_entity where userName like :userName and age like :age limit 1")
    fun findByName(userName: String, age: Int): Flow<UserEntity>

    @Insert
    fun insertAll(vararg userEntity: UserEntity)

    @Delete
    fun delete(userEntity: UserEntity)
}