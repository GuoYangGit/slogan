package com.huafang.mvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.huafang.mvvm.entity.UserEntity

/**
 * App 存储 [Room] 数据库
 * @author yang.guo on 2022/10/25
 */
@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        /**
         * 创建数据库方法
         */
        fun create(appContext: Context): AppDatabase {
            val dbName = "user_" // 数据库名称
            return Room.databaseBuilder(
                appContext.applicationContext,
                AppDatabase::class.java,
                dbName
            ).build()
        }
    }

    /**
     * 用户Dao
     */
    abstract fun userDao(): UserDao
}