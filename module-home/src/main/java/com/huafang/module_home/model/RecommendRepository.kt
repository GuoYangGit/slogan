package com.huafang.module_home.model

import androidx.fragment.app.Fragment
import com.huafang.module_home.entity.RecommendEntity
import com.huafang.mvvm.db.AppDatabase
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
 * 推荐请求类
 * @author yang.guo on 2022/10/25
 */
@Singleton
class RecommendRepository @Inject constructor(appDatabase: AppDatabase) {

    fun getRecommendList(fragment: Fragment, index: Int): Flow<List<RecommendEntity>> {
        return callbackFlow {
            PictureSelector.create(fragment)
                .dataSource(SelectMimeType.ofImage())
                .obtainMediaData { result ->
                    val list: List<RecommendEntity> = if (index == 0) {
                        List(10) { RecommendEntity(url = result.first().path) }
                    } else {
                        List(5) { RecommendEntity(url = result.first().path) }
                    }
                    trySendBlocking(list)
                }
            awaitClose { }
        }.onEach {
            delay(1000)
        }.flowOn(Dispatchers.IO)
    }
}