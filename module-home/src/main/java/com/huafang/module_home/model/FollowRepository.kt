package com.huafang.module_home.model

import com.huafang.module_home.entity.ContentEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 关注列表的数据处理类
 * @author yang.guo on 2022/10/14
 */
@Singleton
class FollowRepository @Inject constructor() {
    /**
     * 获取关注列表页面数据
     */
    fun getFollowList(index: Int): Flow<List<ContentEntity>> {
        return flow {
            val result = if (index == 0) {
                List(10) { ContentEntity() }
            } else {
                List(5) { ContentEntity() }
            }
            emit(result)
        }.onEach {
            delay(1000)
        }
    }
}