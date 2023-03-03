package com.huafang.module_home.model

import com.huafang.module_home.entity.ArticleEntity
import com.huafang.module_home.entity.BannerEntity
import com.huafang.module_home.entity.PageEntity
import com.huafang.mvvm.db.AppDatabase
import kotlinx.coroutines.flow.*
import rxhttp.RxWanAndroidHttp
import rxhttp.toFlowWanAndroidResponse
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 推荐请求类
 * @author yang.guo on 2022/10/25
 */
@Singleton
class RecommendRepository @Inject constructor(appDatabase: AppDatabase) {

    /**
     * 首页文章列表
     */
    fun getArticleList(page: Int = 0): Flow<List<ArticleEntity>> {
        return RxWanAndroidHttp.get("article/list/$page/json")
            .add("page_size", 10)
            .toFlowWanAndroidResponse<PageEntity<ArticleEntity>>()
            .map { it.datas }
    }

    /**
     * 首页banner
     */
    fun getBannerList(): Flow<List<BannerEntity>> {
        return RxWanAndroidHttp.get("banner/json")
            .toFlowWanAndroidResponse()
    }

    /**
     * 置顶文章
     */
    fun getTopArticleList(): Flow<List<ArticleEntity>> {
        return RxWanAndroidHttp.get("article/top/json")
            .toFlowWanAndroidResponse()
    }
}