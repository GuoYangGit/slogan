package com.huafang.module_home.model

import com.huafang.module_home.entity.ContentEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author yang.guo on 2022/10/14
 * @describe 关注列表的数据处理类
 */
@Singleton
class FollowRepository @Inject constructor() {
    fun getFollowList(): Flow<List<ContentEntity>> {
        return flow {
            val result = listOf(
                ContentEntity(),
                ContentEntity(),
                ContentEntity()
            )
            emit(result)
        }
    }
}