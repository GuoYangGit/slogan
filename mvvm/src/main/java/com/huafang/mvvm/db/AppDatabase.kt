package com.huafang.mvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.huafang.mvvm.entity.UserEntity

/**
 * App存储Room数据库
 * @author yang.guo on 2022/10/25
 */
@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        fun create(appContext: Context): AppDatabase {
            val dbName = ""
            return Room.databaseBuilder(
                appContext.applicationContext,
                AppDatabase::class.java,
                dbName
            ).build()
        }
    }

    abstract fun userDao(): UserDao
}