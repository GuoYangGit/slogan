package com.huafang.module_home.model

import androidx.fragment.app.Fragment
import com.huafang.module_home.entity.RecommendEntity
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author yang.guo on 2022/10/25
 * @describe 推荐请求类
 */
@Singleton
class RecommendRepository @Inject constructor() {

    fun getRecommendList(fragment: Fragment): Flow<List<RecommendEntity>> {
        return callbackFlow {
            PictureSelector.create(fragment)
                .dataSource(SelectMimeType.ofImage())
                .obtainMediaData {
                    val list: List<RecommendEntity> = it.map { RecommendEntity(url = it.path) }
                    trySendBlocking(list)
                }
            awaitClose { }
        }.onEach {
            delay(1000)
        }.flowOn(Dispatchers.IO)
    }
}